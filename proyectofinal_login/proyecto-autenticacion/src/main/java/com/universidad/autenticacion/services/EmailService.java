package com.universidad.autenticacion.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailRecuperacion(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@universidad.com");
        message.setTo(to);
        message.setSubject("Recuperación de Contraseña");
        message.setText("Para restablecer tu contraseña, haz clic en el siguiente enlace:\n\n" +
                "http://localhost:8080/reset?token=" + token);
        
        try {
            mailSender.send(message);
        } catch (Exception e) {
            // Log error but don't break flow for user in this demo
            System.err.println("Error enviando email: " + e.getMessage());
        }
    }
}

