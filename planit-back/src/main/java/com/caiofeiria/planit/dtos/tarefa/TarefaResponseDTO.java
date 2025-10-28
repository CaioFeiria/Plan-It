package com.caiofeiria.planit.dtos.tarefa;

import com.caiofeiria.planit.dtos.usuario.UsuarioResumoDTO;
import com.caiofeiria.planit.enums.Prioridade;
import com.caiofeiria.planit.models.Projeto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public record TarefaResponseDTO(

    @NotNull
    Long id,

    @NotBlank
    @Size(min = 3 ,max = 100)
    String nome,

    @Size(max = 255)
    String descricao,
    
    @NotNull
    Prioridade prioridade,

    @NotNull
    LocalDateTime dataCriacao,

    @NotNull
    LocalDateTime dataConclusao,

    @NotNull
    Projeto projeto,

    @NotNull
    UsuarioResumoDTO responsavel,

    List<UsuarioResumoDTO> usuariosIds
) {}
