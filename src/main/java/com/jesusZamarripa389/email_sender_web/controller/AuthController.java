package com.jesusZamarripa389.email_sender_web.controller;


import com.jesusZamarripa389.email_sender_web.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    // muestra la página de Login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // Busca un archivo llamado login.html en templates
    }

    //  datos enviados por el formulario de Login
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo,
                                @RequestParam String contrasenia,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

        // validamos de forma dinámica contra el servidor SMTP
        boolean esValido = authService.validarCredenciales(correo, contrasenia);

        if (esValido) {
            // GUARDADO EN MEMORIA: guarda las credenciales en la sesión web actual
            session.setAttribute("usuarioCorreo", correo);
            session.setAttribute("usuarioClave", contrasenia);

            return "redirect:/panel"; // Redirige a la pantalla principal de envíos
        } else {
            // Si falla, mandamos un mensaje de error de vuelta al login
            redirectAttributes.addFlashAttribute("error", "Correo o Contraseña de aplicación incorrectos.");
            return "redirect:/login";
        }
    }

    // cierra la sesión y destruye las credenciales de la memoria
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate(); // Destruye por completo la sesión y borra los datos guardados
        return "redirect:/login";
    }
}