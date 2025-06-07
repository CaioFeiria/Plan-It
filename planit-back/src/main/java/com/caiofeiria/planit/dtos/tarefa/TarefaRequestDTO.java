package com.caiofeiria.planit.dtos.tarefa;

import java.time.LocalDateTime;
import java.util.List;

import com.caiofeiria.planit.enums.Prioridade;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para criação de tarefas. O campo 'usuariosIds' é opcional.")
public record TarefaRequestDTO(

		@NotBlank(message = "Adicione o titulo da Tarefa.")
        @Size(min = 3, max = 100, message = "O titulo deve da Tarefa ter entre 3 e 100 caracteres.")
	    String nome,

	    @Size(max = 255, message = "Descrição tem no máximo 255 caracteres.")
	    String descricao,

	    @NotNull(message = "Prioridade não pode ser nula.")
		@Schema(
			    description = "Prioridade da tarefa. Valores possíveis: BAIXO, MEDIO, ALTO, URGENTE",
			    example = "BAIXO"
			)
	    Prioridade prioridade,
	    
	    @NotNull(message = "A data de conclusão da tarefa não pode ser nula.")
	    LocalDateTime dataConclusao,

	    @NotNull(message = "Id do projeto não pode ser nulo.")
	    Long projetoId,

	    @NotNull(message = "Id do responsável não pode ser nulo.")
	    Long responsavelId,

	    @Schema(
	            description = "IDs dos usuários participantes da tarefa. Para não informar nenhum usuário, omita completamente este campo ou envie um array vazio []",
	            example = "[1, 2, 3]",
	            nullable = true
	        )
	    List<Long> usuariosIds 

	) {}
