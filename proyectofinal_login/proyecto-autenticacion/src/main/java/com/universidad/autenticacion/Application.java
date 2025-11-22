package com.universidad.autenticacion;

import com.universidad.autenticacion.services.AutenticacionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner initData(AutenticacionService authService) {
        return args -> {
            // Crear usuario de prueba
            // Email: admin@test.com
            // Pass: Pass1@
            authService.registrarUsuario("admin@test.com", "Pass1@");
            System.out.println("Usuario de prueba creado: admin@test.com / Pass1@");
        };
    }
}

