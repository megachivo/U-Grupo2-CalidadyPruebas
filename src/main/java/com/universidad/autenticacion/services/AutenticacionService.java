package com.universidad.autenticacion.services;

import com.universidad.autenticacion.models.Usuario;
import com.universidad.autenticacion.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutenticacionService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    private static final int MAX_INTENTOS = 5;

    public boolean login(String email, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            return false;
        }

        Usuario usuario = usuarioOpt.get();

        if (usuario.isBloqueado()) {
            return false; // O lanzar excepción especifica
        }

        if (verificarPassword(password, usuario.getPassword())) {
            // Reset intentos
            usuario.setIntentosFallidos(0);
            usuarioRepository.save(usuario);
            return true;
        } else {
            // Incrementar intentos
            usuario.setIntentosFallidos(usuario.getIntentosFallidos() + 1);
            if (usuario.getIntentosFallidos() >= MAX_INTENTOS) {
                usuario.setBloqueado(true);
            }
            usuarioRepository.save(usuario);
            return false;
        }
    }

    public void iniciarRecuperacion(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            String token = UUID.randomUUID().toString();
            usuario.setTokenRecuperacion(token);
            usuario.setFechaExpiracionToken(LocalDateTime.now().plusHours(1));
            usuarioRepository.save(usuario);
            
            emailService.enviarEmailRecuperacion(email, token);
        }
    }
    
    public boolean validarToken(String token) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByTokenRecuperacion(token);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return usuario.getFechaExpiracionToken().isAfter(LocalDateTime.now());
        }
        return false;
    }

    public boolean cambiarPassword(String token, String newPassword) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByTokenRecuperacion(token);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getFechaExpiracionToken().isAfter(LocalDateTime.now())) {
                usuario.setPassword(encriptarPassword(newPassword));
                usuario.setTokenRecuperacion(null);
                usuario.setFechaExpiracionToken(null);
                usuario.setIntentosFallidos(0); // Desbloquear si estaba bloqueado por olvido
                usuario.setBloqueado(false);
                usuarioRepository.save(usuario);
                return true;
            }
        }
        return false;
    }

    public void registrarUsuario(String email, String password) {
        if (usuarioRepository.existsByEmail(email)) return;
        
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(encriptarPassword(password));
        usuarioRepository.save(usuario);
    }

    private String encriptarPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error encriptando password", e);
        }
    }
    
    private boolean verificarPassword(String inputPassword, String storedPassword) {
        return encriptarPassword(inputPassword).equals(storedPassword);
    }
    
    public boolean isBloqueado(String email) {
        return usuarioRepository.findByEmail(email)
                .map(Usuario::isBloqueado)
                .orElse(false);
    }
}


