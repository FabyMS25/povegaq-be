package com.gamq.ambiente.enumeration;

public enum StatusInfraccion {
        PENDIENTE, //GENERADA, //Se detectó la infracción y fue registrada en el sistema
        NOTIFICADA, //El infractor fue notificado oficialmente
        EN_PLAZO_PAGO, //El plazo para pagar la multa está corriendo
        PAGADA, //La infracción fue pagada
        NO_PAGADA, //Se venció el plazo de pago y no pago
        CANCELADA  //Fue anulada por autoridad competente (apelación válida, error, etc.)
}

