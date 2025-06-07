package com.caiofeiria.planit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caiofeiria.planit.dtos.usuario.UsuarioRequestDTO;
import com.caiofeiria.planit.dtos.usuario.UsuarioUpdateDTO;
import com.caiofeiria.planit.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Usuários", description = "Todos os endpoints relacionados à Usuários")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    @Operation(summary = "Busca todos os Usuários", description = "Retorna todos os Usuários cadastrados")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.listarUsuarios());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Procura um Usuário pelo seu ID", description = "Retorna os dados de um Usuário correspondente ao ID informado")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    
    @GetMapping("/buscar-nome")
    @Operation(summary = "Busca todos os Usuários que contem esse nome", description = "Retorna todos os Usuários que contém esses caracteres no nome")
    public ResponseEntity<?> getByName(@RequestParam String nome) {
    	return ResponseEntity.ok(service.buscarPorNome(nome));
    }
    
    @GetMapping("/buscar-email")
    @Operation(summary = "Busca todos os Usuários que contem esse email", description = "Retorna todos os Usuários que contém esses caracteres no email")
    public ResponseEntity<?> getByEmail(@RequestParam String email) {
    	return ResponseEntity.ok(service.buscarPorEmail(email));
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo Usuário")
    public ResponseEntity<?> createUser(@Valid @RequestBody UsuarioRequestDTO usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarUsuario(usuario));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um Usuário existente")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateDTO usuario) {
    	return ResponseEntity.ok(service.atualizarUsuario(id, usuario));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um Usuário", description = "Deleta um Usuário correspondente ao ID informado")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
