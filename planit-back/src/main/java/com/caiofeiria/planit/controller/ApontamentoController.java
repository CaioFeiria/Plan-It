package com.caiofeiria.planit.controller;

import com.caiofeiria.planit.services.ApontamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Apontamentos", description = "Todos os endpoints relacionados à Apontamentos")
@RestController
@RequestMapping("/api/apontamentos")
public class ApontamentoController {
	
	@Autowired
	private ApontamentoService service;

    @Operation(summary = "Cria um apontamento de tarefa para o usuário", description = "Associa uma tarefa a um usuário")
    @PostMapping("/{usuarioId}/tarefas/{tarefaId}")
    public ResponseEntity<?> criarApontamento(
            @PathVariable Long usuarioId,
            @PathVariable Long tarefaId
    ) {
        service.associarTarefa(usuarioId, tarefaId);
        return ResponseEntity.ok("Apontamento criado com sucesso.");
    }

    @Operation(summary = "Lista apontamentos de um usuário", description = "Retorna as tarefas apontadas ao usuário")
    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<?> listarApontamentosDoUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.listarTarefasDoUsuario(usuarioId));
    }
}

