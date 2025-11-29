package com.universidad.autenticacion.services;

import com.universidad.autenticacion.models.Usuario;
import com.universidad.autenticacion.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AutenticacionServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AutenticacionService autenticacionService;

    private Usuario usuario;
    private final String PASSWORD_RAW = "Pass1@";
    private String passwordEncoded;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        // Manually encoding password to match service logic
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(PASSWORD_RAW.getBytes());
        passwordEncoded = Base64.getEncoder().encodeToString(hash);

        usuario = new Usuario();
        usuario.setEmail("test@test.com");
        usuario.setPassword(passwordEncoded);
        usuario.setIntentosFallidos(0);
        usuario.setBloqueado(false);
    }

    @Test
    void login_DebeSerExitoso_CuandoCredencialesSonCorrectas() {
        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.of(usuario));

        boolean resultado = autenticacionService.login("test@test.com", PASSWORD_RAW);

        assertTrue(resultado);
        verify(usuarioRepository).save(usuario);
        assertEquals(0, usuario.getIntentosFallidos());
    }

    @Test
    void login_DebeFallar_CuandoUsuarioNoExiste() {
        when(usuarioRepository.findByEmail("noexiste@test.com")).thenReturn(Optional.empty());

        boolean resultado = autenticacionService.login("noexiste@test.com", PASSWORD_RAW);

        assertFalse(resultado);
    }

    @Test
    void login_DebeFallar_CuandoPasswordEsIncorrecta() {
        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.of(usuario));

        boolean resultado = autenticacionService.login("test@test.com", "WrongPass");

        assertFalse(resultado);
        verify(usuarioRepository).save(usuario);
        assertEquals(1, usuario.getIntentosFallidos());
    }

    @Test
    void login_DebeBloquearUsuario_DespuesDe5Intentos() {
        usuario.setIntentosFallidos(4);
        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.of(usuario));

        boolean resultado = autenticacionService.login("test@test.com", "WrongPass");

        assertFalse(resultado);
        assertTrue(usuario.isBloqueado());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void iniciarRecuperacion_DebeGenerarTokenYEnviarEmail() {
        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.of(usuario));

        autenticacionService.iniciarRecuperacion("test@test.com");

        assertNotNull(usuario.getTokenRecuperacion());
        assertNotNull(usuario.getFechaExpiracionToken());
        verify(usuarioRepository).save(usuario);
        verify(emailService).enviarEmailRecuperacion(eq("test@test.com"), anyString());
    }
}

