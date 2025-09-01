package com.caiofeiria.planit.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Entity
@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USUARIOS")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "usuario_id")
    private UUID id;

    @NotBlank(message = "Digite seu nome.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "Digite o email.")
    @Email(message = "Email iválido.")
    @Column(name = "email", nullable = false, length = 150, unique = true)
    private String email;

    @ManyToMany
    @JoinTable(
        name = "usuario_tarefa",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "tarefa_id")
    )
    private List<Tarefa> tarefas;
}
