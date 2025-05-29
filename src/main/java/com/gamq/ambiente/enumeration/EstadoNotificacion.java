package com.gamq.ambiente.enumeration;

public enum EstadoNotificacion {
    PENDIENTE, //aún no se ha generado o autorizado.
    ENVIADA, //fue generada y se intenta enviar.
    ENTREGADA,//confirmación de entrega (email leído o documento firmado).
    FALLIDA, //no se pudo entregar
    VENCIDA, //pasó el plazo sin cumplimiento
    CUMPLIDA //el destinatario actuó conforme (reinspección aprobada, multa pagada, etc.).
}
