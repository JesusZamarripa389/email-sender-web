package com.jesusZamarripa389.email_sender_web.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

@Service
public class EmailService {

    // Lista de tipos permitidos
    private final String[] tiposPermitidos = {
            "jpg", "jpeg", "png", "gif", "bmp",
            "pdf", "doc", "docx", "ppt", "pptx", "xls", "xlsx",
            "zip", "rar", "tar", "gz",
            "txt", "rtf", "csv", "xml", "html",
            "mp3", "ogg", "mp4", "mov", "avi"
    };

    /**
     * Valida si la extension del archivo subido es permitida.
     */
    public boolean esTipoArchivoValido(String nombreArchivo) {
        if (nombreArchivo == null || !nombreArchivo.contains(".")) {
            return false;
        }
        String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1).toLowerCase();
        for (String tipo : tiposPermitidos) {
            if (extension.equals(tipo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Envia el correo usando las credenciales del usuario en sesion y los archivos adjuntos de la web.
     */
    public void enviarCorreoSmtp(String remitente, String clave, String destinatario,
                                 String asunto, String cuerpoMensaje, MultipartFile[] archivos) throws Exception {

        // configuración de propiedades SMTP dinámicas
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // crear sesión autenticada con las credenciales del usuario actual
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        // crear el mensaje de correo
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(remitente));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject(asunto);

        // estructurar el contenido (Cuerpo de texto + Adjuntos)
        MimeMultipart multipart = new MimeMultipart();

        //  texto del mensaje
        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText(cuerpoMensaje);
        multipart.addBodyPart(textBodyPart);

        // procesar y adjuntar archivos subidos desde el formulario web
        if (archivos != null) {
            for (MultipartFile archivoWeb : archivos) {
                // salta campos vacíos
                if (archivoWeb.isEmpty()) continue;

                String nombreOriginal = archivoWeb.getOriginalFilename();

                // valida la extension
                if (!esTipoArchivoValido(nombreOriginal)) {
                    throw new IllegalArgumentException("El archivo '" + nombreOriginal + "' tiene una extensión no permitida.");
                }

                // Convertimos el archivo de la petición web a un adjunto SMTP
                MimeBodyPart attachmentBodyPart = new MimeBodyPart();

                // Spring maneja los archivos en bytes temporales en memoria o disco
                jakarta.activation.DataSource source = new jakarta.mail.util.ByteArrayDataSource(
                        archivoWeb.getBytes(),
                        archivoWeb.getContentType()
                );

                attachmentBodyPart.setDataHandler(new jakarta.activation.DataHandler(source));
                attachmentBodyPart.setFileName(nombreOriginal);
                multipart.addBodyPart(attachmentBodyPart);
            }
        }

        // une todas las partes al mensaje definitivo y procedemos al envío
        message.setContent(multipart);
        Transport.send(message);
    }
}