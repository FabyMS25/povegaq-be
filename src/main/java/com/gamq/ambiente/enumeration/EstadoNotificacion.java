package com.gamq.ambiente.enumeration;

public enum EstadoNotificacion {
    PENDIENTE, //notificacion creada .
    ENVIADA, //fue envia por correo o fisicamente.
    ENTREGADA,//confirmación de entrega (email leído o documento firmado) o recibio fisicamente .
    FALLIDA, //no se pudo entregar
    VENCIDA, //pasó el plazo sin cumplimiento la fechaAsistencia < fechaActrual Hoy
    CUMPLIDA //el destinatario actuó conforme (reinspección aprobada, multa pagada, etc.). dentro los plazos
}
