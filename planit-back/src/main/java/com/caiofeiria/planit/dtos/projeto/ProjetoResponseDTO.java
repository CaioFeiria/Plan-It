package com.caiofeiria.planit.dtos.projeto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ProjetoResponseDTO(

        @NotNull
        Long id,

        @NotBlank
        @Size(min = 3, max = 100)
        String nome,

        @NotBlank
        @Size(min = 3, max = 255)
        String descricao,

        @Size(max = 10)
        String emoji
) {}
