package com.gamq.ambiente.utils;

import com.gamq.ambiente.enumeration.TipoCombustible;

public class TipoCombustionUtil {
    public static String clasificarTipoCombustion(TipoCombustible tipoCombustible) {
        if (tipoCombustible == null) return "desconocido";

        switch (tipoCombustible) {
            case DIESEL:
            case DUAL_DIESEL_GNV:
                return "diésel";

            case GASOLINA:
                return "gasolina";

            case GNV:
                return "Gas natural vehicular (GNV o CNG)";

            case DUAL_GNV_GASOLINA:
                return "Gas natural vehicular (GNV o CNG) - Gasolina";

            case HIBRIDO:
            case HIBRIDO_ENCHUFABLE:
                return "híbrido";

            case ELECTRICO:
                return "eléctrico";

            case GLP:
                return "Gas Licuado de Petróleo (GLP)";

            default:
                return "desconocido";
        }
    }
}
