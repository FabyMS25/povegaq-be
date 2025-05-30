package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.InfraccionDto;
import com.gamq.ambiente.dto.mapper.InspeccionMapper;
import com.gamq.ambiente.dto.mapper.TipoInfraccionMapper;
import com.gamq.ambiente.enumeration.EstadoNotificacion;
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

        // Clasificar grado de infraccion segun reglas de documento
        String grado = clasificarGrado(inspeccion, historial);
        if (grado == null) return null;// No hay infraccion

        // Obtener tipo de contribuyente
        //TipoContribuyente tipoContribuyente = inspeccion.getVehiculo()
        //        .getPropietario().getTipoContribuyente();
        TipoContribuyente tipoContribuyente = ContribuyenteUtil.resolverTipoContribuyente(inspeccion);

       /* Vehiculo vehiculo = inspeccion.getVehiculo();
        if (vehiculo != null && vehiculo.getPropietario() != null) {
            tipoContribuyente = vehiculo.getPropietario().getTipoContribuyente();
        } else if (inspeccion.getConductor() != null) {
            tipoContribuyente = inspeccion.getConductor().getTipoContribuyente();
        }
        if (tipoContribuyente == null) {
            throw new IllegalStateException("No se pudo determinar el tipo de contribuyente. Verifica si el vehículo tiene propietario o conductor.");
        }*/

        // Buscar el tipo de infracción correspondiente
        TipoInfraccion tipoInfraccion = tipoInfraccionRepository
                .findByGradoAndTipoContribuyente(grado, tipoContribuyente)
                .orElseThrow(() -> new IllegalStateException("No se encontro tipo de infraccion para grado: "
                        + grado + " y contribuyente" ));

        // Crear nueva infraccion
        InfraccionDto infraccionDto = new InfraccionDto();

        //Infraccion infraccion = new Infraccion(UUID.randomUUID().toString());
        infraccionDto.setFechaInfraccion(new Date());
        infraccionDto.setInspeccionDto(InspeccionMapper.toInspeccionDto(inspeccion));
        infraccionDto.setTipoInfraccionDto(TipoInfraccionMapper.toTipoInfraccionDto(tipoInfraccion));
        //0infraccion.setTipoInfraccion(tipoInfraccion);
        infraccionDto.setMontoTotal(tipoInfraccion.getValorUFV());
        infraccionDto.setStatusInfraccion("PENDIENTE");
        infraccionDto.setEstadoPago(false);
       // infraccion.setEsGeneradaAutomaticamente(true); // campo opcional no existe

        // Guardar y devolver
        return  infraccionDto; // infraccionRepository.save(infraccion);
    }

    private String clasificarGrado(Inspeccion inspeccion, List<Infraccion> historial) {
        boolean resultadoFallo = !inspeccion.isResultado();

        // Verificar si accedió a una segunda inspección sin readecuar (Art. 19.1)
        boolean tieneNotificacionInfraccionVencidaSegundoIntento = inspeccion.getNotificacionList().stream()
                .filter(n -> n.getTypeNotificacion() == TipoNotificacion.INFRACCION)
                .anyMatch(n -> n.getNumeroIntento() == 2 &&
                        n.getStatusNotificacion() == EstadoNotificacion.VENCIDA);

        if (tieneNotificacionInfraccionVencidaSegundoIntento) {
            return "TERCER_GRADO"; // Art. 19.1
        }

        // Reincidencia no paga → Segundo grado (Art. 18.2)
        long infraccionesNoPagadas = historial.stream()
                .filter(i -> !i.isEstadoPago())
                .count();
        if (infraccionesNoPagadas >= 2) return "SEGUNDO_GRADO";

        // Si no pasó inspección y ya falló antes → Tercer grado (Art. 19.2)
        if (resultadoFallo) {
            Optional<Inspeccion> anteriorFallida = historial.stream()
                    .map(Infraccion::getInspeccion)
                    .filter(Objects::nonNull)
                    .filter(i -> !i.isResultado())
                    .max(Comparator.comparing(Inspeccion::getFechaInspeccion));

            if (anteriorFallida.isPresent() &&
                    inspeccion.getFechaInspeccion().after(anteriorFallida.get().getFechaInspeccion())) {
                return "TERCER_GRADO";
            }

            return "PRIMER_GRADO"; // Art. 17
        }

        return null; // No hay infracción
    }




        /*
        // Reincidencia no paga → Segundo grado
        long infraccionesNoPagadas = historial.stream()
                .filter(i -> !i.isEstadoPago())
                .count();
        if (infraccionesNoPagadas >= 2) return "SEGUNDO_GRADO";

        //  Si no paso inspeccion y ya fallado antes → Tercer grado
        if (resultadoFallo) {
            Optional<Inspeccion> anteriorFallida = historial.stream()
                    .map(Infraccion::getInspeccion)
                    .filter(Objects::nonNull)
                    .filter(i -> !i.isResultado())
                    .max(Comparator.comparing(Inspeccion::getFechaInspeccion));

            if (anteriorFallida.isPresent() &&
                    inspeccion.getFechaInspeccion().after(anteriorFallida.get().getFechaInspeccion())) {
                return "TERCER_GRADO";
            }

            return "PRIMER_GRADO";
        }

        return null; // No hay infracción*/
   // }


    private String clasificarGradoCompleto(Inspeccion inspeccion, List<Infraccion> historial) {
        boolean falloActual = !inspeccion.isResultado();

        // 3ER GRADO: Segunda inspección fallida
        if (falloActual) {
            Optional<Inspeccion> anteriorFallida = historial.stream()
                    .map(Infraccion::getInspeccion)
                    .filter(Objects::nonNull)
                    .filter(i -> !i.isResultado())
                    .max(Comparator.comparing(Inspeccion::getFechaInspeccion));

            if (anteriorFallida.isPresent() &&
                    inspeccion.getFechaInspeccion().after(anteriorFallida.get().getFechaInspeccion())) {
                return "TERCER GRADO";//GradoInfraccion.TERCER;
            }
        }

        // 2DO GRADO: Reincidencia en no tener certificación o no pagar multas
        long infraccionesNoPagadas = historial.stream()
                .filter(i -> !i.isEstadoPago())
                .count();

        if (infraccionesNoPagadas >= 2) {
            return "SEGUNDO GRADO"; //GradoInfraccion.SEGUNDO;
        }

        long inspeccionesFallidas = historial.stream()
                .map(Infraccion::getInspeccion)
                .filter(Objects::nonNull)
                .filter(i -> !i.isResultado())
                .count();

        if (inspeccionesFallidas >= 2) {
            return "SEGUNDO GRADO";// GradoInfraccion.SEGUNDO;
        }

        // 1ER GRADO: Primera vez que falla inspección
        if (falloActual) {
            return "PRIMER GRADO"; //GradoInfraccion.PRIMER;
        }

        // No aplica infracción
        return null;
    }

}