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
import org.springframework.web.bind.annotation.RestController;

import com.caiofeiria.planit.dtos.tarefa.TarefaRequestDTO;
import com.caiofeiria.planit.dtos.tarefa.TarefaUpdateDTO;
import com.caiofeiria.planit.services.TarefaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Tarefas", description = "Todos os endpoints relacionados à Tarefas")
@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService service;

    @GetMapping
    @Operation(summary = "Busca todos as Tarefas", description = "Retorna todas as Tarefas cadastrados")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.listarTarefas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Procura uma Tarefa pelo seu ID", description = "Retorna os dados de uma Tarefa correspondente ao ID informado")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra uma nova Tarefa")
    public ResponseEntity<?> createTask(@Valid @RequestBody TarefaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarTarefa(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma Tarefa existente")
    public ResponseEntity<?> updateTask(@PathVariable("id") Long id, @Valid @RequestBody TarefaUpdateDTO dto) {
        return ResponseEntity.ok(service.atualizarTarefa(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma Tarefa", description = "Deleta uma Tarefa correspondente ao ID informado")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        service.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }
}