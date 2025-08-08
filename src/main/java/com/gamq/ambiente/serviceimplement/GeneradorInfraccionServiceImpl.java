package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.InfraccionDto;
import com.gamq.ambiente.dto.mapper.InfraccionMapper;
import com.gamq.ambiente.dto.mapper.InspeccionMapper;
import com.gamq.ambiente.dto.mapper.TipoInfraccionMapper;
import com.gamq.ambiente.dto.mapper.VehiculoMapper;
import com.gamq.ambiente.enumeration.EstadoNotificacion;
import com.gamq.ambiente.enumeration.GradoInfraccion;
import com.gamq.ambiente.enumeration.StatusInfraccion;
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
        infraccionDto.setStatusInfraccion(StatusInfraccion.GENERADA);
        infraccionDto.setEstadoPago(false);
        infraccionDto.setGeneradoSistema(true);
        infraccionDto.setEnPlazo(true);
        infraccionDto.setVehiculoDto(VehiculoMapper.toVehiculoDto(inspeccion.getVehiculo()));
        /*
        // Guardar
        Infraccion infraccion = new Infraccion();
        infraccion.setFechaInfraccion(new Date());
        infraccion.setInspeccion(inspeccion);
        infraccion.setTipoInfraccion(tipoInfraccion);
        infraccion.setMontoTotal(tipoInfraccion.getValorUFV());
        infraccion.setStatusInfraccion(StatusInfraccion.PENDIENTE);
        infraccion.setEstadoPago(false);
        infraccion.setGeneradoSistema(true);
        infraccion.setVehiculo(inspeccion.getVehiculo());
        infraccion.setMotivo("el motivo es por que ...");
        infraccion.setNombreRegistrador("JUAN PABLO RIOS");
        infraccion.setUuidUsuario("eb4b420b-3114-4cfd-8366-9867b4df45b8");
        infraccionRepository.save(infraccion);
        //Guardar  */
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
            return tipoInfraccionRepository.findByArticuloAndGradoInfraccionAndTipoContribuyente(
             "Art. 19.1",
                       GradoInfraccion.TERCER_GRADO,tipoContribuyente).get();
            // "TERCER_GRADO"; // Art. 19.1
            //  Acceder a una segunda inspección sin haber procedido a la readecuación
        }

        // Reincidencia no paga → Segundo grado (Art. 18.2)
        //No haber cancelado la multa impuesta dentro de los plazos establecidos para el pago de la sancion correspondiente
        long infraccionesNoPagadas = historial.stream()
                .filter(i -> !i.isEstadoPago())
                .count();
        if (infraccionesNoPagadas >= 2) return
                tipoInfraccionRepository.findByArticuloAndGradoInfraccionAndTipoContribuyente(
                        "Art. 18.2",
                                  GradoInfraccion.SEGUNDO_GRADO, tipoContribuyente).get();
                                  // "SEGUNDO_GRADO";
                                 //"No haber cancelado la multa impuesta dentro de los plazos establecidos para el pago de la sancion correspondiente"
        // Si no pasó inspección y ya falló antes → Tercer grado (Art. 19.2)
        if (resultadoFallo) {
            Optional<Inspeccion> anteriorFallida = historial.stream()
                    .map(Infraccion::getInspeccion)
                    .filter(Objects::nonNull)
                    .filter(i -> !i.isResultado())
                    .max(Comparator.comparing(Inspeccion::getFechaInspeccion));

            if (anteriorFallida.isPresent() &&
                    inspeccion.getFechaInspeccion().after(anteriorFallida.get().getFechaInspeccion())) {
                return tipoInfraccionRepository.findByArticuloAndGradoInfraccionAndTipoContribuyente(
                        "Art. 19.1",
                                   GradoInfraccion.TERCER_GRADO, tipoContribuyente).get() ;
                                  // "TERCER_GRADO";
                                  // "Acceder a una segunda inspección sin haber procedido a la readecuación"
            }
        }
        return null; // No hay infracción
    }
}