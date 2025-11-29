package com.universidad.autenticacion.services;

import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class ValidacionService {

    // Min 5, Max 10, 1 Uppercase, 1 Special char
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{5,10}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public boolean validarPassword(String password) {
        if (password == null) {
            return false;
        }
        return pattern.matcher(password).matches();
    }
    
    public boolean validarEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}


