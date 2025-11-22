# 📚 GUÍA DEL PROYECTO - Calidad y Pruebas de Software

**Curso:** PSWE-06 Calidad y Pruebas de Software  
**Profesor:** Ing. Francisco Vargas Navarro  
**Grupo:** 2 (JAVA)

---

## 🎯 RESUMEN DEL PROYECTO

Desarrollar un **sistema de autenticación web** con interfaz gráfica en Java y posteriormente implementar pruebas automatizadas exhaustivas.

**Dos Pasos Principales:**

1. **PASO 1:** Aplicación funcional de autenticación
2. **PASO 2:** Suite completa de pruebas automatizadas

---

## 📝 PASO 1: APLICACIÓN DE AUTENTICACIÓN

### Requisitos Funcionales

#### Interfaz de Login

- Interfaz gráfica con colores
- Campo de email (usuario)
- Campo de contraseña con validaciones:
  - Mínimo: 5 caracteres
  - Máximo: 10 caracteres
  - Al menos 1 mayúscula
  - Al menos 1 carácter especial
- Título de la aplicación
- Diseño profesional

#### Funcionalidades

- **Sistema de intentos:** Máximo 5 intentos fallidos
- **Recuperación de contraseña:** Envío por email
- **Base de datos:** Tabla usuarios con campos (email, password, intentos, etc.)

#### Entregable

✅ Aplicación totalmente funcional

---

## 🧪 PASO 2: AUTOMATIZACIÓN DE PRUEBAS

### Tipos de Pruebas Requeridas

1. **Caja Negra:** Pruebas unitarias de funciones
2. **Integración:** Entre módulos
3. **Sistema y UAT:** Aceptación del usuario
4. **Rendimiento:** Carga, estrés, volumen
5. **Seguridad:** Autenticación, autorización, penetración
6. **Usabilidad:** Experiencia de usuario
7. **Compatibilidad:** Navegadores, dispositivos, OS
8. **Recuperación:** Resiliencia ante fallas

#### Entregable

✅ Memoria de trabajo con:

- Investigación de herramientas
- Plan de pruebas
- Ejecución y resultados

---

## 🏗️ ARQUITECTURA DEL PROYECTO

### Patrón MVC (Model-View-Controller)

```
┌─────────────────────────────────────────────┐
│           NAVEGADOR (Cliente)               │
│  • HTML (Thymeleaf + Bootstrap)            │
│  • CSS (estilos)                           │
│  • JavaScript (validaciones)               │
└──────────────────┬──────────────────────────┘
                   │ HTTP
┌──────────────────▼──────────────────────────┐
│         SPRING BOOT (Servidor)              │
│                                             │
│  ┌─────────────────────────────────────┐   │
│  │  CONTROLLERS (Controladores)        │   │
│  │  • LoginController                  │   │
│  │  • RecuperarPasswordController      │   │
│  └───────────────┬─────────────────────┘   │
│                  │                          │
│  ┌───────────────▼─────────────────────┐   │
│  │  SERVICES (Lógica de Negocio)      │   │
│  │  • AutenticacionService             │   │
│  │  • EmailService                     │   │
│  │  • ValidacionService                │   │
│  └───────────────┬─────────────────────┘   │
│                  │                          │
│  ┌───────────────▼─────────────────────┐   │
│  │  REPOSITORIES (Acceso a Datos)     │   │
│  │  • UsuarioRepository                │   │
│  └───────────────┬─────────────────────┘   │
│                  │                          │
│  ┌───────────────▼─────────────────────┐   │
│  │  MODELS (Entidades)                 │   │
│  │  • Usuario                          │   │
│  └─────────────────────────────────────┘   │
└──────────────────┬──────────────────────────┘
                   │ JDBC
┌──────────────────▼──────────────────────────┐
│         BASE DE DATOS (H2)                  │
│  • Tabla: usuarios                         │
└─────────────────────────────────────────────┘
```

---

## 🛠️ STACK TECNOLÓGICO

### Backend

- **Lenguaje:** Java 17 o 21
- **Framework:** Spring Boot 3.x
  - Spring Web (MVC)
  - Spring Data JPA
  - Spring Mail
  - Spring Validation

### Frontend

- **Plantillas:** Thymeleaf
- **CSS Framework:** Bootstrap 5
- **JavaScript:** Vanilla JS

### Base de Datos

- **Opción 1 (Recomendada):** H2 Database (en memoria)
- **Opción 2:** MySQL (local o cloud)

### Testing

- **Unitarias:** JUnit 5, Mockito
- **Integración:** Spring Boot Test
- **E2E:** Selenium WebDriver
- **Rendimiento:** JMeter o Gatling
- **Seguridad:** OWASP ZAP
- **Cobertura:** JaCoCo

### Email

- **Gmail SMTP** (producción)
- **Mailtrap** (pruebas)

### Build Tool

- **Maven** (recomendado)

---

## 📁 ESTRUCTURA DEL PROYECTO

```
proyecto-autenticacion/
│
├── src/
│   ├── main/
│   │   ├── java/com/universidad/autenticacion/
│   │   │   ├── controllers/
│   │   │   │   ├── LoginController.java
│   │   │   │   └── RecuperarPasswordController.java
│   │   │   │
│   │   │   ├── services/
│   │   │   │   ├── AutenticacionService.java
│   │   │   │   ├── EmailService.java
│   │   │   │   └── ValidacionService.java
│   │   │   │
│   │   │   ├── repositories/
│   │   │   │   └── UsuarioRepository.java
│   │   │   │
│   │   │   ├── models/
│   │   │   │   └── Usuario.java
│   │   │   │
│   │   │   ├── dto/
│   │   │   │   ├── LoginRequest.java
│   │   │   │   └── RecuperarPasswordRequest.java
│   │   │   │
│   │   │   ├── config/
│   │   │   │   └── EmailConfig.java
│   │   │   │
│   │   │   └── Application.java
│   │   │
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── login.html
│   │       │   ├── recuperar-password.html
│   │       │   └── dashboard.html
│   │       │
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   └── custom.css
│   │       │   └── js/
│   │       │       └── validaciones.js
│   │       │
│   │       ├── application.properties
│   │       └── data.sql
│   │
│   └── test/
│       └── java/com/universidad/autenticacion/
│           ├── unit/
│           ├── integration/
│           ├── e2e/
│           └── performance/
│
├── pom.xml
└── README.md
```

---

## 🔄 FLUJO DE AUTENTICACIÓN

### Login

```
1. Usuario accede a /login
2. Sistema muestra formulario (login.html)
3. Usuario ingresa email y contraseña
4. JavaScript valida formato (cliente)
5. POST a /login
6. LoginController recibe datos
7. AutenticacionService valida:
   • Busca usuario en BD
   • Verifica si está bloqueado
   • Valida contraseña
   • Actualiza intentos fallidos
8. Si válido: Redirige a /dashboard
9. Si inválido: Retorna error y cuenta intento
10. Si 5 intentos: Bloquea cuenta
```

### Recuperación de Contraseña

```
1. Usuario click en "¿Olvidaste tu contraseña?"
2. Sistema muestra formulario
3. Usuario ingresa email
4. Sistema valida que email existe
5. Genera token único
6. EmailService envía email con enlace
7. Usuario click en enlace del email
8. Sistema valida token
9. Usuario ingresa nueva contraseña
10. Sistema actualiza contraseña en BD
```

---

## 📋 COMPONENTES PRINCIPALES

### 1. Controllers (Controladores)

**Responsabilidad:** Manejar peticiones HTTP y retornar vistas

**LoginController:**

- `GET /login` → Muestra formulario
- `POST /login` → Procesa autenticación
- `GET /dashboard` → Página después de login exitoso

**RecuperarPasswordController:**

- `GET /recuperar` → Muestra formulario
- `POST /recuperar` → Envía email de recuperación
- `GET /reset?token=xxx` → Formulario nueva contraseña
- `POST /reset` → Actualiza contraseña

### 2. Services (Servicios)

**Responsabilidad:** Lógica de negocio

**AutenticacionService:**

- Validar credenciales
- Gestionar intentos fallidos
- Encriptar/verificar contraseñas
- Bloquear/desbloquear cuentas

**ValidacionService:**

- Validar formato email
- Validar requisitos de contraseña (5-10 chars, mayúscula, especial)

**EmailService:**

- Configurar SMTP
- Enviar emails de recuperación
- Generar tokens únicos

### 3. Repositories (Repositorios)

**Responsabilidad:** Acceso a datos (CRUD)

**UsuarioRepository:**

- `findByEmail(String email)` → Buscar usuario
- `save(Usuario usuario)` → Guardar/actualizar
- `existsByEmail(String email)` → Verificar existencia

### 4. Models (Modelos)

**Responsabilidad:** Representar entidades de BD

**Usuario:**

```java
- id: Long
- email: String
- password: String (encriptada)
- intentosFallidos: int
- bloqueado: boolean
- fechaCreacion: LocalDateTime
```

### 5. Views (Vistas - Thymeleaf)

**Responsabilidad:** Interfaz de usuario

**login.html:**

- Formulario de login
- Mensajes de error
- Contador de intentos
- Link a recuperación

**recuperar-password.html:**

- Formulario para email
- Confirmación de envío

**dashboard.html:**

- Página principal después de login

---

## 🧪 ESTRATEGIA DE TESTING (PASO 2)

### Pruebas Unitarias

**Framework:** JUnit 5 + Mockito

**Qué probar:**

- Métodos de servicios (aislados)
- Validaciones de contraseña
- Lógica de intentos fallidos
- Generación de tokens

**Ejemplo:**

```java
@Test
void validarPassword_DebeRetornarTrue_CuandoPasswordEsValida() {
    // Given
    String password = "Pass1@";

    // When
    boolean resultado = validacionService.validarPassword(password);

    // Then
    assertTrue(resultado);
}
```

### Pruebas de Integración

**Framework:** Spring Boot Test

**Qué probar:**

- Integración entre capas
- Controllers + Services + Repositories
- Transacciones de BD
- Configuración de Spring

### Pruebas E2E (End-to-End)

**Framework:** Selenium WebDriver

**Qué probar:**

- Flujo completo de login
- Flujo de recuperación de contraseña
- Bloqueo de cuenta tras 5 intentos
- Navegación entre páginas

**Ejemplo de escenario:**

```
1. Abrir navegador
2. Ir a /login
3. Ingresar credenciales inválidas 5 veces
4. Verificar que cuenta queda bloqueada
5. Verificar mensaje de error apropiado
```

### Pruebas de Rendimiento

**Framework:** JMeter o Gatling

**Qué probar:**

- **Carga:** 100 usuarios concurrentes
- **Estrés:** Incrementar hasta fallo
- **Volumen:** Muchos datos en BD

**Métricas:**

- Tiempo de respuesta
- Throughput (peticiones/segundo)
- Tasa de error

### Pruebas de Seguridad

**Herramienta:** OWASP ZAP

**Qué probar:**

- SQL Injection
- XSS (Cross-Site Scripting)
- CSRF
- Contraseñas encriptadas en BD
- Validación de tokens
- Sesiones seguras

### Pruebas de Usabilidad

**Qué evaluar:**

- Claridad de mensajes de error
- Facilidad de navegación
- Accesibilidad (WCAG)
- Responsive design

### Pruebas de Compatibilidad

**Qué probar:**

- Navegadores: Chrome, Firefox, Safari, Edge
- Dispositivos: Desktop, tablet, móvil
- Sistemas Operativos: Windows, Mac, Linux

### Pruebas de Recuperación

**Qué probar:**

- Comportamiento ante BD caída
- Comportamiento ante servidor email caído
- Manejo de excepciones
- Recuperación de sesiones

---

## 💻 HERRAMIENTAS DE DESARROLLO

### IDEs Recomendados

#### IntelliJ IDEA Community ⭐

- El mejor para Spring Boot
- Autocompletado inteligente
- Debugging excelente
- **Gratis**

#### Visual Studio Code

- Ligero y rápido
- Requiere extensiones:
  - Extension Pack for Java
  - Spring Boot Extension Pack

#### Android Studio

- Basado en IntelliJ
- Funciona perfectamente para Java

### Requisitos

- **JDK:** Java 17 o 21
- **Maven:** Gestión de dependencias
- **Git:** Control de versiones

---

## 📊 BASE DE DATOS

### Opción Recomendada: H2 Database

**Ventajas:**

- No requiere instalación
- Configuración mínima
- Consola web integrada (`/h2-console`)
- Perfecto para desarrollo/pruebas

**Configuración básica:**

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.h2.console.enabled=true
```

**Tabla usuarios:**

```sql
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    intentos_fallidos INT DEFAULT 0,
    bloqueado BOOLEAN DEFAULT FALSE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Alternativa: MySQL

**Si prefieres MySQL:**

- Local: Instalar MySQL Workbench
- Cloud: PlanetScale (5GB gratis)

---

## 📧 CONFIGURACIÓN DE EMAIL

### Opción 1: Gmail SMTP (Para demostración)

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu-email@gmail.com
spring.mail.password=tu-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

**Nota:** Necesitas crear "App Password" en configuración de Google

### Opción 2: Mailtrap (Para desarrollo/pruebas)

- No envía emails reales
- Solo para verificar que funciona
- Gratis: https://mailtrap.io

---

## ✅ ENTREGABLES FINALES

### PASO 1

1. Código fuente completo
2. Aplicación funcional
3. Base de datos con datos de prueba
4. README con instrucciones de ejecución

### PASO 2

1. **Memoria técnica** que incluya:

   - Investigación de herramientas de testing
   - Plan de pruebas detallado
   - Casos de prueba documentados
   - Ejecución de pruebas
   - Resultados y métricas
   - Capturas de pantalla
   - Conclusiones

2. **Código de pruebas** automatizadas

3. **Reportes** de cobertura y resultados

---

## 🎯 CRITERIOS DE ÉXITO

### PASO 1 - Aplicación Funcional

✅ Login funciona correctamente  
✅ Validaciones de contraseña implementadas  
✅ Sistema de 5 intentos funciona  
✅ Recuperación de contraseña por email funciona  
✅ Interfaz gráfica profesional  
✅ Base de datos conectada y funcional

### PASO 2 - Testing

✅ Todos los tipos de pruebas implementados  
✅ Cobertura de código > 70%  
✅ Documentación completa  
✅ Resultados medibles y reportados

---

## 📚 RECURSOS ÚTILES

### Documentación Oficial

- Spring Boot: https://spring.io/projects/spring-boot
- Thymeleaf: https://www.thymeleaf.org/
- Bootstrap: https://getbootstrap.com/
- JUnit 5: https://junit.org/junit5/
- Selenium: https://www.selenium.dev/

### Tutoriales

- Spring Boot + Thymeleaf
- JUnit Testing
- Selenium WebDriver
- JMeter Performance Testing
