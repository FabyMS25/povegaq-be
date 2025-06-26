package com.gamq.ambiente.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailUtil {
     /*   /**
         * Envía un correo con adjunto PDF.
         *
         * @param mailSender      JavaMailSender inyectado
         * @param destinatario    correo destino
         * @param asunto          asunto del correo
         * @param mensajeTexto    cuerpo del mensaje (texto plano)
         * @param rutaArchivoPDF  ruta del archivo PDF a adjuntar
         * @throws MessagingException en caso de error de envío
         */
    /*    public static void enviarCorreoConAdjunto(JavaMailSender mailSender, String destinatario, String asunto, String mensajeTexto, String rutaArchivoPDF) throws MessagingException {
            MimeMessage message = mailSender.createMimeMessage();

            // true indica multipart (adjuntos)
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(mensajeTexto, false); // false: texto plano, true: html

            FileSystemResource archivo = new FileSystemResource(new File(rutaArchivoPDF));
            helper.addAttachment("Notificacion_Inspeccion.pdf", archivo);

            mailSender.send(message);
        }*/


    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envía un correo con un archivo PDF adjunto de forma asíncrona.
     *
     * @param destinatario  correo electrónico del destinatario
     * @param asunto        asunto del correo
     * @param mensaje       cuerpo del mensaje en texto plano
     * @param rutaArchivoPdf ruta absoluta del archivo PDF a adjuntar
     */
    @Async
    public void enviarPdfPorCorreo(String destinatario, String asunto, String mensaje, String rutaArchivoPdf) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(mensaje, false);

            FileSystemResource archivo = new FileSystemResource(new File(rutaArchivoPdf));
            helper.addAttachment("Informe_Inspeccion.pdf", archivo);

            mailSender.send(message);
        } catch (MessagingException e) {
            // Manejar excepción según política de logs o reintentos
            e.printStackTrace();
        }
    }
}
