package com.gamq.ambiente.utils;

import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.model.TipoContribuyente;
import com.gamq.ambiente.model.Vehiculo;

public class ContribuyenteUtil {
    private ContribuyenteUtil() {}

    public static TipoContribuyente resolverTipoContribuyente(Inspeccion inspeccion) {
        if (inspeccion == null) {
            throw new IllegalArgumentException("La inspección no puede ser null.");
        }

        Vehiculo vehiculo = inspeccion.getVehiculo();
        if (vehiculo != null && vehiculo.getPropietario() != null) {
            return vehiculo.getPropietario().getTipoContribuyente();
        } else if (inspeccion.getConductor() != null) {
            return inspeccion.getConductor().getTipoContribuyente();
        }

        throw new IllegalStateException("No se pudo determinar el tipo de contribuyente.");
    }
}
