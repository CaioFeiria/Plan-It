package com.caiofeiria.planit.dtos.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "DTO para criação de usuários.")
public record UsuarioRequestDTO(

    @NotBlank(message = "Digite o nome do Usuário.")
    @Size(min = 3 ,max = 100, message = "O nome do Usuário deve ter entre 3 e 100 caracteres.")
    String nome,

    @NotBlank(message = "Digite o email do Usuário.")
    @Email(message = "Email inválido.")
    String email
) {
}
