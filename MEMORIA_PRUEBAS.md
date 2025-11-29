# 🧪 MEMORIA TÉCNICA - Fase 2: Automatización de Pruebas

Este documento detalla la estrategia, implementación y resultados de las pruebas automatizadas realizadas al Sistema de Autenticación.

---

## 1. 📋 Estrategia de Pruebas

Para garantizar la calidad del software, se implementó la pirámide de pruebas completa requerida en el proyecto:

1.  **Pruebas Unitarias (Unit Testing):** Foco en lógica de negocio aislada.
2.  **Pruebas de Integración (Integration Testing):** Verificación de comunicación entre Controladores y Servicios.
3.  **Pruebas E2E (End-to-End):** Simulación de usuario real usando navegador web.

---

## 2. ⚙️ Configuración del Entorno

### Dependencias Añadidas (`pom.xml`)
Se incorporaron las siguientes librerías para habilitar el testing avanzado:

*   **JUnit 5:** Framework base para ejecución de pruebas.
*   **Mockito:** Para crear objetos simulados ("mocks") en pruebas unitarias.
*   **Spring Boot Test:** Para pruebas de integración con el contexto de Spring.
*   **Selenium WebDriver:** Para pruebas de interfaz de usuario (E2E).
*   **WebDriverManager:** Gestión automática de drivers de navegador (ChromeDriver).

---

## 3. 🧪 Descripción de las Pruebas Implementadas

### A. Pruebas Unitarias (`/services`)

**Objetivo:** Validar reglas de negocio sin depender de base de datos ni servidores externos.

*   **`ValidacionServiceTest` (7 Casos):**
    *   ✅ Contraseña válida (cumple todas las reglas).
    *   ✅ Falla por longitud (< 5 caracteres).
    *   ✅ Falla por longitud (> 10 caracteres).
    *   ✅ Falla por falta de mayúscula.
    *   ✅ Falla por falta de carácter especial.
    *   ✅ Email válido.
    *   ✅ Email inválido.

*   **`AutenticacionServiceTest` (5 Casos):**
    *   ✅ Login exitoso (resetea intentos fallidos).
    *   ✅ Login fallido (usuario no existe).
    *   ✅ Login fallido (contraseña incorrecta).
    *   ✅ **Bloqueo de cuenta:** Verifica que tras modificar el mock para simular 5 intentos, el usuario queda bloqueado.
    *   ✅ Inicio de recuperación (genera token).

### B. Pruebas de Integración (`/controllers`)

**Objetivo:** Verificar que el controlador recibe peticiones HTTP y responde correctamente.

*   **`LoginControllerTest` (3 Casos):**
    *   ✅ `GET /login`: Retorna vista correcta y status 200 OK.
    *   ✅ `POST /login` (Correcto): Redirige a `/dashboard` (Status 302).
    *   ✅ `POST /login` (Incorrecto): Retorna a login con mensaje de error en el modelo.

### C. Pruebas E2E (`/e2e`)

**Objetivo:** Simular un usuario real navegando en Chrome.

*   **`SeleniumTest` (2 Escenarios Críticos):**
    *   **Herramienta:** Selenium con ChromeDriver (modo `headless` / sin interfaz gráfica para rapidez).
    *   ✅ **Flujo Completo:** Abre navegador -> Entra a Login -> Escribe credenciales reales -> Clic "Ingresar" -> Valida que la URL final sea `/dashboard`.
    *   ✅ **Flujo Error:** Ingresa credenciales falsas -> Valida que aparezca el texto "Credenciales inválidas" en el HTML.

---

## 4. 📊 Resultados de Ejecución

Al ejecutar la suite completa, se obtuvieron los siguientes resultados:

| Tipo de Prueba | Clase | Tests Ejecutados | Fallos | Estado |
| :--- | :--- | :---: | :---: | :---: |
| **Unitarias** | `ValidacionServiceTest` | 7 | 0 | ✅ PASÓ |
| **Unitarias** | `AutenticacionServiceTest` | 5 | 0 | ✅ PASÓ |
| **Integración** | `LoginControllerTest` | 3 | 0 | ✅ PASÓ |
| **E2E (Sistema)** | `SeleniumTest` | 2 | 0 | ✅ PASÓ |
| **TOTAL** | | **17** | **0** | **EXITOSO** |

---

## 5. 🚀 Cómo Ejecutar y Ver Resultados

### Opción 1: Desde Terminal (Recomendada)

1.  Abre la terminal en la carpeta del proyecto.
2.  Ejecuta:
    ```bash
    ./mvnw test
    ```
3.  **Ver Resultados:**
    Al finalizar, verás un resumen en la consola similar a:
    ```
    [INFO] Tests run: 17, Failures: 0, Errors: 0, Skipped: 0
    [INFO] BUILD SUCCESS
    ```

### Opción 2: Reporte Detallado (HTML)

Maven genera reportes automáticos tras la ejecución. Puedes verlos abriendo:

*   **Ruta:** `target/surefire-reports/`
*   Allí encontrarás archivos `.txt` y `.xml` con el detalle de cada prueba ejecutada.

