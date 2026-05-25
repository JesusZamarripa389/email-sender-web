<img width="1385" height="819" alt="image" src="https://github.com/user-attachments/assets/5d73dae9-4d44-46cc-8157-70ead346d234" />
<img width="1907" height="892" alt="Captura de pantalla 2026-05-25 124726" src="https://github.com/user-attachments/assets/ffde0b1e-f72c-4233-9e74-6e0e6e6a3382" />


# Email Sender Web 
Una aplicación web desarrollada en Java utilizando **Spring Boot** y **Thymeleaf**, diseñada para el envío de correos electrónicos con soporte multiparte (texto y archivos adjuntos múltiples).
Este proyecto nace de la migración completa y refactorización de una aplicación de escritorio clásica (Java Swing/JFrame) hacia una arquitectura web moderna y desacoplada bajo el patrón **MVC (Modelo-Vista-Controlador)**.

##  Objetivo del Proyecto
El objetivo principal de este proyecto fue migrar la arquitectura y corregir **vulnerabilidades críticas** de seguridad presentes en el diseño original de escritorio, adaptándolo a los estándares web.

##  Mejoras de Seguridad Implementadas

| Vulnerabilidad Original (Swing) | Solución Implementada (Spring Boot) |
| :--- | :--- |
| **Credenciales:** Correo de soporte y clave expuestos en el código fuente. | **Credenciales 100% Dinámicas:** Cada usuario introduce y utiliza sus propios datos de configuración. |
| **Login Estático:** Acceso mediante credenciales quemadas (`Admin / 123`). | **Autenticación SMTP Real:** *Handshake* en tiempo real con los servidores de Google para validar la cuenta y la contraseña de aplicación (16 dígitos) antes de otorgar acceso. |
| **Inseguridad en el Cliente:** Datos sensibles expuestos en la interfaz de usuario. | **Gestión Segura de Estado:** Las credenciales validadas se encapsulan en la sesión del servidor (`HttpSession`). La vista jamás vuelve a tener acceso a las contraseñas. |
| **Carga de Archivos Vulnerable:** Sin filtros restrictivos en el sistema de archivos local. | **Validación en Backend:** Intercepción de flujos de carga web (`MultipartFile`) mediante listas blancas para verificar extensiones permitidas antes del envío. |


##  Tecnologías Utilizadas
* **Backend:** Java 21, Spring Boot, Spring Web.
* **Motor de Plantillas (Frontend):** Thymeleaf, HTML5, Bootstrap 5.
* **Protocolo de Red:** Jakarta Mail (JavaMail API) sobre redes seguras TLS (Puerto 587).
* **Gestor de Dependencias:** Maven.


##  Requisitos Previos
Antes de ejecutar la aplicación, asegúrate de contar con:
* **Java JDK 21** o superior.
* **Maven 3.9+** instalado.
* Una **Contraseña de Aplicación de Google** (Gmail como servidor SMTP).

##  Instalación y Ejecución
**Clonar el repositorio:**
   ```bash
   git clone [https://github.com/tu-usuario/email-sender-web.git](https://github.com/tu-usuario/email-sender-web.git)
   cd email-sender-web
