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

            nombreCompleto.append(conductor.getNombre());
            nombreCompleto.append(" ");
            nombreCompleto.append(conductor.getPrimerApellido());

            if (conductor.getSegundoApellido() != null && !conductor.getSegundoApellido().isBlank()) {
                nombreCompleto.append(" ").append(conductor.getSegundoApellido());
            }

            if (conductor.getApellidoEsposo() != null && !conductor.getApellidoEsposo().isBlank()) {
                nombreCompleto.append(" ").append(conductor.getApellidoEsposo());
            }

            return nombreCompleto.toString();
        }

        throw new IllegalStateException("No se pudo determinar el nombre del contribuyente.");
    }
}