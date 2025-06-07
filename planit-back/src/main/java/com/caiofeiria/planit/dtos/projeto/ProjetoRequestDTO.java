package com.caiofeiria.planit.dtos.projeto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para criação de projetos.")
public record ProjetoRequestDTO(

		@NotBlank(message = "Digite o nome do Projeto.")
        @Size(min = 3, max = 100, message = "O nome do Projeto deve ter entre 3 e 100 caracteres.")
        String nome,

        @NotBlank(message = "Adicione uma descrição ao Projeto.")
        @Size(min = 3, max = 255, message = "A descrição deve ter entre 3 e 255 caracteres.")
        String descricao
) {
}
