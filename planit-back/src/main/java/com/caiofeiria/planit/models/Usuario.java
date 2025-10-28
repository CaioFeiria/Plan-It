package com.caiofeiria.planit.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USUARIOS")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id;

    @NotBlank(message = "Digite seu nome.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "Digite o email.")
    @Email(message = "Email inválido.")
    @Column(name = "email", nullable = false, length = 150, unique = true)
    private String email;

    @NotBlank(message = "Digite a senha.")
    @Size(min = 6, max = 255, message = "A senha deve ter no mínimo 6 caracteres.")
    @Column(name = "senha", nullable = false, length = 255)
    @JsonIgnore
    private String senha;

    @NotNull(message = "A role é obrigatória.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @ManyToMany
    @JoinTable(
        name = "usuario_tarefa",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "tarefa_id")
    )
    @JsonIgnore
    private List<Tarefa> tarefas;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }
}
