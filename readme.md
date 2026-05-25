# Email Sender Web Application 
Una aplicación web desarrollada en Java utilizando Spring Boot y Thymeleaf, diseñada para el envío de correos electrónicos con soporte multiparte (texto y archivos adjuntos múltiples).
Este proyecto nace de la migración completa y refactorización de una aplicación de escritorio clásica (Java Swing/JFrame) hacia una arquitectura web desacoplada bajo el patrón MVC (Modelo-Vista-Controlador)

#Objetivo del proyecto
El objetivo principal de este proyecto fue corregir vulnerabilidades críticas presentes en el diseño original de escritorio:

#Mejoras implementadas
Eliminación de Credenciales Hardcodeadas: La versión de escritorio original exponía un correo de soporte y una clave de aplicación directamente en el código fuente. En esta versión, las credenciales son 100% dinámicas; cada usuario introduce sus propios datos. 
Autenticación SMTP Real: Se eliminó un sistema de login estático de prueba (`Admin / 123`). Ahora, la aplicación realiza un "handshake" en tiempo real con los servidores SMTP de Google para validar si la cuenta y su contraseña de aplicación de 16 dígitos son legítimas antes de otorgar acceso. 
Gestión Segura de Estado (Session Management): Las credenciales validadas se encapsulan exclusivamente en la memoria del servidor (`HttpSession`). La capa de presentación (la vista del navegador) jamás vuelve a tener acceso o visibilidad de las contraseñas tras el inicio de sesión.
Validación de Archivos en Backend: Se adaptaron las reglas de negocio del sistema nativo para interceptar los flujos de carga web (`MultipartFile`), verificando mediante listas blancas que solo se procesen extensiones permitidas antes de interactuar con el servidor de correo.

#Tecnologías utilizadas
* **Backend:** Java 21, Spring Boot, Spring Web.
* **Motor de Plantillas (Frontend):** Thymeleaf, HTML5, Bootstrap 5 .
* **Protocolo de Red:** Jakarta Mail (JavaMail API) sobre redes seguras TLS (Puerto 587).
* **Gestor de Dependencias:** Maven.

---

## 📂 Estructura del Proyecto

src/main/
├── java/com/miportafolio/emailsenderweb/
│   ├── EmailSenderWebApiApplication.java  # Clase de arranque del servidor
│   ├── controller/                        # Controladores HTTP (Auth y Panel)
│   └── service/                           # Lógica de negocio (Autenticación y Envío SMTP)
└── resources/
    ├── application.properties             # Parámetros del servidor
    └── templates/                         # Vistas del frontend (Login y Panel)