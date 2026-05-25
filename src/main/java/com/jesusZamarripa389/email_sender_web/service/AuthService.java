package com.jesusZamarripa389.email_sender_web.service;

import org.springframework.stereotype.Service;
import java.util.Properties;
import jakarta.mail.Session;
import jakarta.mail.Transport;

@Service
public class AuthService {

    /**
     * Valida si las credenciales de Gmail son correctas intentando conectar al servidor SMTP.
     */
    public boolean validarCredenciales(String correo, String contrasenia) {
        // configura las propiedades para conectarnos al servidor de Gmail
        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "587");
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");

        try {
            // crea una sesión de correo estándar
            Session session = Session.getInstance(propiedades, null);
            Transport transport = session.getTransport("smtp");

            // intenta conectar con el correo y contraseña
            transport.connect("smtp.gmail.com", correo, contrasenia);
            transport.close(); // Si conecta con éxito, cerramos la conexión inmediatamente

            return true; // Credenciales válidas
        } catch (Exception e) {
            // Si salta una excepción (error de autenticación, red, etc.), las credenciales no son válidas
            return false;
        }
    }
}
