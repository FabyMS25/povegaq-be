package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.InfraccionDto;
import com.gamq.ambiente.dto.mapper.InspeccionMapper;
import com.gamq.ambiente.dto.mapper.TipoInfraccionMapper;
import com.gamq.ambiente.enumeration.EstadoNotificacion;
import com.gamq.ambiente.enumeration.GradoInfraccion;
import com.gamq.ambiente.enumeration.TipoNotificacion;
import com.gamq.ambiente.model.*;
import com.gamq.ambiente.repository.InfraccionRepository;
import com.gamq.ambiente.repository.TipoInfraccionRepository;
import com.gamq.ambiente.utils.ContribuyenteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GeneradorInfraccionServiceImpl {
    @Autowired
    InfraccionRepository infraccionRepository;
    @Autowired
    TipoInfraccionRepository tipoInfraccionRepository;

    public InfraccionDto generarDesdeInspeccion(Inspeccion inspeccion) {
        // Obtener historial de infracciones del vehículo
        List<Infraccion> historial = infraccionRepository.findByInspeccionVehiculo(inspeccion.getVehiculo());
        TipoContribuyente tipoContribuyente = ContribuyenteUtil.resolverTipoContribuyente(inspeccion);
        // Clasificar grado de infraccion segun reglas de documento
        TipoInfraccion tipoInfraccion = clasificarGrado(inspeccion, tipoContribuyente, historial);

        if (tipoInfraccion == null) return null;// No hay infraccion
        // Crear nueva infraccion
        InfraccionDto infraccionDto = new InfraccionDto();

        //Infraccion infraccion = new Infraccion(UUID.randomUUID().toString());
        infraccionDto.setFechaInfraccion(new Date());
        infraccionDto.setInspeccionDto(InspeccionMapper.toInspeccionDto(inspeccion));
        infraccionDto.setTipoInfraccionDto(TipoInfraccionMapper.toTipoInfraccionDto(tipoInfraccion));
        infraccionDto.setMontoTotal(tipoInfraccion.getValorUFV());
        infraccionDto.setStatusInfraccion("PENDIENTE");
        infraccionDto.setEstadoPago(false);
        infraccionDto.setGeneradoSistema(true);

        // Guardar y devolver
        return  infraccionDto; // infraccionRepository.save(infraccion);
    }

    private TipoInfraccion clasificarGrado(Inspeccion inspeccion, TipoContribuyente tipoContribuyente, List<Infraccion> historial) {
        boolean resultadoFallo = !inspeccion.isResultado();

        // Verificar si accedió a una segunda inspección sin readecuar (Art. 19.1)
        boolean tieneNotificacionInfraccionVencidaSegundoIntento = inspeccion.getNotificacionList().stream()
                .filter(n -> n.getTypeNotificacion() == TipoNotificacion.INFRACCION)
                .anyMatch(n -> n.getNumeroIntento() == 2 &&
                        n.getStatusNotificacion() == EstadoNotificacion.VENCIDA);

        if (tieneNotificacionInfraccionVencidaSegundoIntento) {
            return tipoInfraccionRepository.findByDescripcionAndGradoInfraccionAndTipoContribuyente(
             "Acceder a una segunda inspección sin haber procedido a la readecuación",
                    GradoInfraccion.TERCER_GRADO,tipoContribuyente

            ).get();
            //return GradoInfraccion.TERCER_GRADO;// "TERCER_GRADO"; // Art. 19.1
        }

        // Reincidencia no paga → Segundo grado (Art. 18.2)
        //No haber cancelado la multa impuesta dentro de los plazos establecidos para el pago de la sancion correspondiente
        long infraccionesNoPagadas = historial.stream()
                .filter(i -> !i.isEstadoPago())
                .count();
        if (infraccionesNoPagadas >= 2) return
                tipoInfraccionRepository.findByDescripcionAndGradoInfraccionAndTipoContribuyente(
                "No haber cancelado la multa impuesta dentro de los plazos establecidos para el pago de la sancion correspondiente",
                GradoInfraccion.SEGUNDO_GRADO, tipoContribuyente).get(); // "SEGUNDO_GRADO";

        // Si no pasó inspección y ya falló antes → Tercer grado (Art. 19.2)
        if (resultadoFallo) {
            Optional<Inspeccion> anteriorFallida = historial.stream()
                    .map(Infraccion::getInspeccion)
                    .filter(Objects::nonNull)
                    .filter(i -> !i.isResultado())
                    .max(Comparator.comparing(Inspeccion::getFechaInspeccion));

            if (anteriorFallida.isPresent() &&
                    inspeccion.getFechaInspeccion().after(anteriorFallida.get().getFechaInspeccion())) {
                return tipoInfraccionRepository.findByDescripcionAndGradoInfraccionAndTipoContribuyente(
                        "Acceder a una segunda inspección sin haber procedido a la readecuación",
                        GradoInfraccion.TERCER_GRADO, tipoContribuyente).get() ;// "TERCER_GRADO";
            }
            //return GradoInfraccion.PRIMER_GRADO;// "PRIMER_GRADO"; // Art. 17
        }
        return null; // No hay infracción
    }
}