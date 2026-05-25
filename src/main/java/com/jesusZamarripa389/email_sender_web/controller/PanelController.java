package com.jesusZamarripa389.email_sender_web.controller;

import com.jesusZamarripa389.email_sender_web.service.EmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping; // <-- ASEGÚRATE DE IMPORTAR ESTO
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PanelController {

    @Autowired
    private EmailService emailService;

    /**
     * NUEVO: Ruta GET para mostrar la vista del panel de control
     */
    @GetMapping("/panel")
    public String mostrarPanel(HttpSession session) {
        // Protección de ruta: si no hay sesión activa, mandamos al usuario al login
        String correoUsuario = (String) session.getAttribute("usuarioCorreo");
        String claveUsuario = (String) session.getAttribute("usuarioClave");

        if (correoUsuario == null || claveUsuario == null) {
            return "redirect:/login";
        }

        return "panel"; // Busca el archivo panel.html en src/main/resources/templates/
    }

    /**
     * Ruta POST que procesa el envio del correo desde el formulario web
     */
    @PostMapping("/panel/enviar")
    public String procesarEnvioCorreo(@RequestParam String destinatario,
                                      @RequestParam String asunto,
                                      @RequestParam String mensaje,
                                      @RequestParam("archivos") MultipartFile[] archivos,
                                      HttpSession session,
                                      RedirectAttributes redirectAttributes) {
        // ... (Tu código actual se queda exactamente igual aquí abajo) ...
        String correoUsuario = (String) session.getAttribute("usuarioCorreo");
        String claveUsuario = (String) session.getAttribute("usuarioClave");

        if (correoUsuario == null || claveUsuario == null) {
            return "redirect:/login";
        }

        try {
            emailService.enviarCorreoSmtp(correoUsuario, claveUsuario, destinatario, asunto, mensaje, archivos);
            redirectAttributes.addFlashAttribute("msgExito", "¡Correo electrónico enviado con éxito a " + destinatario + "! ");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("msgError", "Error de Validación: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msgError", "No se pudo enviar el correo: " + e.getMessage());
        }

        return "redirect:/panel";
    }
}