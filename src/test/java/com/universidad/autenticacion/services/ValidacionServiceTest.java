package com.universidad.autenticacion.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidacionServiceTest {

    private final ValidacionService validacionService = new ValidacionService();

    @Test
    void validarPassword_DebeRetornarTrue_CuandoPasswordEsValida() {
        System.out.println("JAVA VERSION: " + System.getProperty("java.version"));
        assertTrue(validacionService.validarPassword("Pass1@"));
        assertTrue(validacionService.validarPassword("Hola1*"));
    }

    @Test
    void validarPassword_DebeRetornarFalse_CuandoEsMuyCorta() {
        assertFalse(validacionService.validarPassword("Pa1@")); // < 5 chars
    }

    @Test
    void validarPassword_DebeRetornarFalse_CuandoEsMuyLarga() {
        assertFalse(validacionService.validarPassword("PasswordMuyLarga1@")); // > 10 chars
    }

    @Test
    void validarPassword_DebeRetornarFalse_CuandoNoTieneMayuscula() {
        assertFalse(validacionService.validarPassword("pass1@"));
    }

    @Test
    void validarPassword_DebeRetornarFalse_CuandoNoTieneCaracterEspecial() {
        assertFalse(validacionService.validarPassword("Pass12"));
    }

    @Test
    void validarEmail_DebeRetornarTrue_CuandoEmailEsValido() {
        assertTrue(validacionService.validarEmail("test@example.com"));
    }

    @Test
    void validarEmail_DebeRetornarFalse_CuandoEmailEsInvalido() {
        assertFalse(validacionService.validarEmail("testexample.com"));
        assertFalse(validacionService.validarEmail(""));
        assertFalse(validacionService.validarEmail(null));
    }
}

