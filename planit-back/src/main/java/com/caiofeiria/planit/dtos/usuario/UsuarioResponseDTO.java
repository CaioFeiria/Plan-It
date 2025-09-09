package com.caiofeiria.planit.dtos.usuario;

import com.caiofeiria.planit.models.Tarefa;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UsuarioResponseDTO(

    @NotNull
    Long id,

    @NotBlank
    @Size(max = 100)
    String nome,

    @NotBlank
    @Email
    String email,
    
    List<Tarefa> tarefas 
) {}
