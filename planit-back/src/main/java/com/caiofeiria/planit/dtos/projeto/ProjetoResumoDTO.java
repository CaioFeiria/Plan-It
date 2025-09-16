package com.caiofeiria.planit.dtos.projeto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProjetoResumoDTO(
    @NotNull
    Long id,

    @NotBlank
    @Size(max = 100)
    String nome,

    @Size(max = 255)
    String descricao,

    @Size(max = 10)
    String emoji
) {}
