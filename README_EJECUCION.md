# 📚 Documentación del Proyecto de Autenticación

Esta guía detalla cómo iniciar la aplicación, explica la estructura del código y describe las tecnologías utilizadas en el backend.

---

## 🚀 Cómo Arrancar la Aplicación

### Requisitos Previos
- Tener Java 17 instalado (el proyecto usa OpenJDK 17).
- Terminal (Bash, Zsh, PowerShell o CMD).

### Pasos para Ejecutar

1.  **Abrir una terminal** y navegar a la carpeta del proyecto:
    ```bash
    cd proyecto-autenticacion
    ```

2.  **Ejecutar el comando de inicio:**
    *   **En Mac/Linux:**
        ```bash
        ./mvnw spring-boot:run
        ```
    *   **En Windows:**
        ```bash
        mvnw spring-boot:run
        ```

3.  **Verificar el inicio:**
    Espera hasta ver un mensaje en la consola que diga:
    `Tomcat started on port 8080`

4.  **Acceder a la aplicación:**
    Abre tu navegador web y visita: [http://localhost:8080](http://localhost:8080)

### 🔑 Credenciales de Prueba (Generadas Automáticamente)
Al arrancar, el sistema crea un usuario administrador por defecto para pruebas:
*   **Email:** `admin@test.com`
*   **Contraseña:** `Pass1@`

---

## 📂 Explicación de Archivos Clave

El proyecto sigue el patrón de arquitectura **MVC (Modelo-Vista-Controlador)**.

### 1. Modelos (`src/main/java/.../models/`)
Representan los datos de la aplicación.
*   **`Usuario.java`**: Define la estructura de la tabla `usuarios` en la base de datos. Contiene campos como `email`, `password` (encriptada), `intentosFallidos` y banderas de bloqueo.

### 2. Repositorios (`src/main/java/.../repositories/`)
Se encargan de la comunicación directa con la base de datos.
*   **`UsuarioRepository.java`**: Interfaz que extiende de `JpaRepository`. Proporciona métodos listos para usar como `save()` (guardar), `findByEmail()` (buscar por correo) y `existsByEmail()`.

### 3. Servicios (`src/main/java/.../services/`)
Contienen la lógica de negocio y seguridad.
*   **`AutenticacionService.java`**: El "cerebro" del sistema.
    *   Verifica contraseñas (usando SHA-256).
    *   Controla el contador de intentos fallidos.
    *   Bloquea la cuenta tras 5 errores.
    *   Gestiona tokens de recuperación.
*   **`ValidacionService.java`**: Reglas de negocio para contraseñas seguras (Mayúsculas, caracteres especiales, longitud).
*   **`EmailService.java`**: Simula el envío de correos electrónicos para la recuperación de contraseñas.

### 4. Controladores (`src/main/java/.../controllers/`)
Manejan las peticiones HTTP del navegador.
*   **`LoginController.java`**: Gestiona la visualización del formulario de login y procesa el ingreso.
*   **`RecuperarPasswordController.java`**: Maneja el flujo de "Olvidé mi contraseña", validación de tokens y cambio de clave.

### 5. Vistas (`src/main/resources/templates/`)
Archivos HTML que ve el usuario (Frontend).
*   **`login.html`**: Formulario de inicio de sesión.
*   **`dashboard.html`**: Página segura a la que se redirige tras un login exitoso.
*   **`recuperar-password.html`**: Formulario para solicitar reset de clave.

### 6. Configuración
*   **`application.properties`**: Configuración de la base de datos, puerto del servidor y servicios de correo.

---

## 🛠️ Tecnologías y Sistemas (Backend)

### 1. Spring Boot (Framework Principal)
Es el marco de trabajo que une todas las piezas. Facilita la creación de aplicaciones Java robustas sin necesidad de configuraciones complejas.

### 2. H2 Database (Base de Datos en Memoria)
*   **¿Qué es?**: Una base de datos SQL ligera que vive en la memoria RAM mientras la aplicación está corriendo.
*   **¿Por qué se usa?**: Ideal para desarrollo y pruebas rápidas porque no requiere instalación.
*   **Persistencia**: Al detener la aplicación, los datos se borran (volátil).

### 3. Spring Data JPA (Acceso a Datos)
Abstrae las consultas SQL complejas. Permite interactuar con la base de datos usando objetos Java (Entidades) en lugar de escribir comandos SQL puros.

### 4. Thymeleaf (Motor de Plantillas)
Permite generar HTML dinámico desde el servidor. Inyecta datos (como mensajes de error o nombres de usuario) directamente en las páginas web antes de enviarlas al navegador.

### 5. SHA-256 (Seguridad)
Algoritmo de hashing utilizado para proteger las contraseñas. Las contraseñas nunca se guardan en texto plano; se transforman en una cadena de caracteres ilegible (hash) antes de guardarse en la base de datos.

---

## 🧪 Cómo ver la Base de Datos (Consola H2)

1.  Con la app corriendo, ve a: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
2.  **JDBC URL:** `jdbc:h2:mem:testdb`
3.  **User Name:** `sa`
4.  **Password:** (Dejar vacío)
5.  Click en **Connect**.

