package com.universidad.autenticacion.controllers;

import com.universidad.autenticacion.dto.LoginRequest;
import com.universidad.autenticacion.services.AutenticacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private AutenticacionService autenticacionService;

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute LoginRequest loginRequest, Model model) {
        boolean isAuthenticated = autenticacionService.login(loginRequest.getEmail(), loginRequest.getPassword());

        if (isAuthenticated) {
            return "redirect:/dashboard";
        } else {
            boolean isBlocked = autenticacionService.isBloqueado(loginRequest.getEmail());
            if (isBlocked) {
                model.addAttribute("error", "Cuenta bloqueada por demasiados intentos fallidos. Recupere su contraseña.");
            } else {
                model.addAttribute("error", "Credenciales inválidas.");
            }
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}


