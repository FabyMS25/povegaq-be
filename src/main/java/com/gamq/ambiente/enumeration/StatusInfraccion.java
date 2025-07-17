package com.gamq.ambiente.enumeration;

public enum StatusInfraccion {
        PENDIENTE, //GENERADA, //Se detectó la infracción y fue registrada en el sistema
        NOTIFICADA, //El infractor fue notificado oficialmente
        PAGADA, //La infracción fue pagada
        VENCIDA, //Se venció el plazo de pago y no pago
        CANCELADA  //Fue anulada por autoridad competente (apelación válida, error, etc.)
}

