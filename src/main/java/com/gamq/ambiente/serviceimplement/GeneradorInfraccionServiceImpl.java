package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.model.Infraccion;
import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.model.TipoContribuyente;
import com.gamq.ambiente.model.TipoInfraccion;
import com.gamq.ambiente.repository.InfraccionRepository;
import com.gamq.ambiente.repository.TipoInfraccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GeneradorInfraccionServiceImpl {
    @Autowired
    InfraccionRepository infraccionRepository;
    @Autowired
    TipoInfraccionRepository tipoInfraccionRepository;


    public Infraccion generarDesdeInspeccion(Inspeccion inspeccion) {
        // Obtener historial de infracciones del vehículo
        List<Infraccion> historial = infraccionRepository.findByInspeccionVehiculo(inspeccion.getVehiculo());

        // Clasificar grado de infraccion segun reglas de documento
        String grado = clasificarGrado(inspeccion, historial);
        if (grado == null) return null;// No hay infraccion

        // Obtener tipo de contribuyente
        TipoContribuyente tipoContribuyente = inspeccion.getVehiculo()
                .getPropietario().getTipoContribuyente();

        // Buscar el tipo de infracción correspondiente
        TipoInfraccion tipoInfraccion = tipoInfraccionRepository
                .findByGradoAndTipoContribuyente(grado, tipoContribuyente)
                .orElseThrow(() -> new IllegalStateException("No se encontro tipo de infraccion para grado: "
                        + grado + " y contribuyente: " + tipoContribuyente.getUuid()));

        // Crear nueva infraccion
        Infraccion infraccion = new Infraccion(UUID.randomUUID().toString());
        infraccion.setFechaInfraccion(new Date());
        infraccion.setInspeccion(inspeccion);
        infraccion.setTipoInfraccion(tipoInfraccion);
        infraccion.setMontoTotal(tipoInfraccion.getValorUFV());
        infraccion.setStatusInfraccion("Pendiente");
        infraccion.setEstadoPago(false);
        infraccion.setEstado(false);
       // infraccion.setEsGeneradaAutomaticamente(true); // campo opcional no existe

        // Guardar y devolver
        return infraccionRepository.save(infraccion);
    }

    private String clasificarGrado(Inspeccion inspeccion, List<Infraccion> historial) {
        boolean resultadoFallo = !inspeccion.isResultado();

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

        return null; // No hay infracción
    }
}