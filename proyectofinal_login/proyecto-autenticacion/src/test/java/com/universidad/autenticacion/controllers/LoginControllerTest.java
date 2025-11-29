package com.universidad.autenticacion.controllers;

import com.universidad.autenticacion.services.AutenticacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AutenticacionService autenticacionService;

    @BeforeEach
    void setup() {
        // Asegurar que existe el usuario de prueba
        autenticacionService.registrarUsuario("test_integration@test.com", "Pass1@");
    }

    @Test
    void loginPage_DebeMostrarFormulario() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Iniciar Sesión")));
    }

    @Test
    void login_DebeRedirigirADashboard_CuandoCredencialesSonCorrectas() throws Exception {
        mockMvc.perform(post("/login")
                        .param("email", "test_integration@test.com")
                        .param("password", "Pass1@"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));
    }

    @Test
    void login_DebeMostrarError_CuandoCredencialesSonIncorrectas() throws Exception {
        mockMvc.perform(post("/login")
                        .param("email", "test_integration@test.com")
                        .param("password", "WrongPass"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"));
    }
}

