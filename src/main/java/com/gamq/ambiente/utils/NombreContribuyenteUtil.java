package com.gamq.ambiente.utils;

import com.gamq.ambiente.model.Conductor;
import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.model.Propietario;
import com.gamq.ambiente.model.Vehiculo;

public class NombreContribuyenteUtil {

    private NombreContribuyenteUtil() {} // Evita instanciación

    public static String resolverNombreContribuyente(Inspeccion inspeccion) {
        if (inspeccion == null) {
            throw new IllegalArgumentException("La inspección no puede ser null.");
        }

        Vehiculo vehiculo = inspeccion.getVehiculo();
        if (vehiculo != null && vehiculo.getPropietario() != null) {
            Propietario propietario = vehiculo.getPropietario();
            StringBuilder nombreCompleto = new StringBuilder();

            nombreCompleto.append(propietario.getNombre());
            nombreCompleto.append(" ");
            nombreCompleto.append(propietario.getPrimerApellido());

            if (propietario.getSegundoApellido() != null && !propietario.getSegundoApellido().isBlank()) {
                nombreCompleto.append(" ").append(propietario.getSegundoApellido());
            }

            if (propietario.getApellidoEsposo() != null && !propietario.getApellidoEsposo().isBlank()) {
                nombreCompleto.append(" ").append(propietario.getApellidoEsposo());
            }
            return nombreCompleto.toString();
        } else if (inspeccion.getConductor() != null) {
            Conductor conductor = inspeccion.getConductor();
            StringBuilder nombreCompleto = new StringBuilder();

            nombreCompleto.append(conductor.getNombreCompleto());
            return nombreCompleto.toString();
        }

        throw new IllegalStateException("No se pudo determinar el nombre del contribuyente.");
    }
}