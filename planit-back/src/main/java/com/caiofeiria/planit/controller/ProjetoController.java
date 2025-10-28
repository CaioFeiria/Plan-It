package com.caiofeiria.planit.controller;

import com.caiofeiria.planit.dtos.projeto.ProjetoRequestDTO;
import com.caiofeiria.planit.dtos.projeto.ProjetoResponseDTO;
import com.caiofeiria.planit.services.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Projetos", description = "Todos os endpoints relacionados à Projetos")
@RestController
@RequestMapping("/api/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService service;

    @GetMapping
    @Operation(summary = "Busca todos os Projetos", description = "Retorna todos os Projetos cadastrados")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.listarProjetos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Procura um Projeto pelo seu ID", description = "Retorna os dados de um Projeto correspondente ao ID informado")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    
    @GetMapping("/buscar")
    @Operation(summary = "Busca todos os Projetos que contem esse nome", description = "Retorna todos os Projetos que contém esses caracteres no nome")
    public ResponseEntity<?> getByName(@RequestParam String nome) {
    	return ResponseEntity.ok(service.buscarPorNome(nome));
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo Projeto")
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjetoRequestDTO projeto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarProjeto(projeto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um Projeto existente")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @Valid @RequestBody ProjetoResponseDTO projeto) {
        return ResponseEntity.ok(service.atualizarProjeto(id, projeto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um Projeto", description = "Deleta um Projeto correspondente ao ID informado")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        service.deletarProjeto(id);
        return ResponseEntity.noContent().build();
    }
}
