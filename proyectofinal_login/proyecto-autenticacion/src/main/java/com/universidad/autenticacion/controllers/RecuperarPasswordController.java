package com.universidad.autenticacion.controllers;

import com.universidad.autenticacion.services.AutenticacionService;
import com.universidad.autenticacion.services.ValidacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RecuperarPasswordController {

    @Autowired
    private AutenticacionService autenticacionService;
    
    @Autowired
    private ValidacionService validacionService;

    @GetMapping("/recuperar")
    public String showRecuperarForm() {
        return "recuperar-password";
    }

    @PostMapping("/recuperar")
    public String processRecuperar(@RequestParam("email") String email, Model model) {
        autenticacionService.iniciarRecuperacion(email);
        model.addAttribute("message", "Si el email existe, se ha enviado un enlace de recuperación.");
        return "recuperar-password";
    }

    @GetMapping("/reset")
    public String showResetForm(@RequestParam("token") String token, Model model) {
        if (autenticacionService.validarToken(token)) {
            model.addAttribute("token", token);
            return "reset-password";
        } else {
            model.addAttribute("error", "Token inválido o expirado.");
            return "login";
        }
    }

    @PostMapping("/reset")
    public String processReset(@RequestParam("token") String token, 
                               @RequestParam("password") String password, 
                               Model model) {
        
        if (!validacionService.validarPassword(password)) {
            model.addAttribute("token", token);
            model.addAttribute("error", "La contraseña no cumple los requisitos (5-10 caracteres, 1 mayúscula, 1 especial).");
            return "reset-password";
        }

        if (autenticacionService.cambiarPassword(token, password)) {
            model.addAttribute("message", "Contraseña actualizada correctamente. Inicie sesión.");
            model.addAttribute("loginRequest", new com.universidad.autenticacion.dto.LoginRequest());
            return "login";
        } else {
            model.addAttribute("error", "Error al actualizar la contraseña. Intente iniciar el proceso nuevamente.");
            return "login";
        }
    }
}

