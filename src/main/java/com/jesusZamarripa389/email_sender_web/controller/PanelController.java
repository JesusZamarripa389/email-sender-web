package com.jesusZamarripa389.email_sender_web.controller;

import com.jesusZamarripa389.email_sender_web.service.EmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PanelController {

    @Autowired
    private EmailService emailService;

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

        // verifica Seguridad de la Sesión
        String correoUsuario = (String) session.getAttribute("usuarioCorreo");
        String claveUsuario = (String) session.getAttribute("usuarioClave");

        if (correoUsuario == null || claveUsuario == null) {
            return "redirect:/login"; // Si expiró la sesión, lo obligamos a loguearse
        }

        try {
            //envío dinámico a través de SMTP
            emailService.enviarCorreoSmtp(correoUsuario, claveUsuario, destinatario, asunto, mensaje, archivos);

            // mensaje de éxito de retorno a la vista web
            redirectAttributes.addFlashAttribute("msgExito", "¡Correo electrónico enviado con éxito a " + destinatario + "! 🚀");

        } catch (IllegalArgumentException e) {
            // Error controlado por validación de extensión inválida
            redirectAttributes.addFlashAttribute("msgError", "Error de Validación: " + e.getMessage());
        } catch (Exception e) {
            // Cualquier otro fallo del servidor SMTP o de red
            redirectAttributes.addFlashAttribute("msgError", "No se pudo enviar el correo: " + e.getMessage());
        }

        return "redirect:/panel"; // muestra el banner de resultado
    }
}