package com.caiofeiria.planit.dtos.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UsuarioResumoDTO(
    
	@NotNull
    UUID id,
	
	@NotBlank(message = "Digite o nome do Usuário.")
    @Size(min = 3 ,max = 100, message = "O nome do Usuário deve ter entre 3 e 100 caracteres.")
    String nome,
    
    @NotBlank(message = "Digite o email do Usuário.")
    @Email(message = "Email inválido.")
    String email
) {}