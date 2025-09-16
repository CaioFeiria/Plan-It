package com.caiofeiria.planit.dtos.tarefa;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TarefaResumoDTO(
    @NotNull
    Long id,

    @NotBlank
    @Size(max = 100)
    String nome,

    @Size(max = 255)
    String descricao
) {}
