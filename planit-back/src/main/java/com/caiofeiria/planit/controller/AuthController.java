package com.caiofeiria.planit.controller;

import com.caiofeiria.planit.configs.JwtService;
import com.caiofeiria.planit.dtos.usuario.LoginRequestDTO;
import com.caiofeiria.planit.dtos.usuario.TokenResponseDTO;
import com.caiofeiria.planit.dtos.usuario.UsuarioRequestDTO;
import com.caiofeiria.planit.dtos.usuario.UsuarioResponseDTO;
import com.caiofeiria.planit.mappers.UsuarioMapper;
import com.caiofeiria.planit.models.Usuario;
import com.caiofeiria.planit.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Autenticação e emissão de token JWT")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    public AuthController(UsuarioService usuarioService, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    @Operation(summary = "Valida credenciais e gera token JWT")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        Usuario usuario = usuarioService.validarLogin(request.getEmail(), request.getSenha());
        String token = jwtService.gerarToken(usuario.getEmail());
        UsuarioResponseDTO usuarioDTO = UsuarioMapper.toResponseDTO(usuario);
        return ResponseEntity.ok(new TokenResponseDTO(token, usuarioDTO));
    }

    @PostMapping("/register")
    @Operation(summary = "Cadastra usuário e retorna token JWT")
    public ResponseEntity<TokenResponseDTO> register(@Valid @RequestBody UsuarioRequestDTO request) {
        Usuario usuario = usuarioService.registrarUsuario(request);
        String token = jwtService.gerarToken(usuario.getEmail());
        UsuarioResponseDTO usuarioDTO = UsuarioMapper.toResponseDTO(usuario);
        return ResponseEntity.ok(new TokenResponseDTO(token, usuarioDTO));
    }
}


