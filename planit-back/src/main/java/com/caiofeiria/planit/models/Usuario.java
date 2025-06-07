package com.caiofeiria.planit.models;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Validated
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USUARIOS")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuarios")
    @SequenceGenerator(name = "seq_usuarios", sequenceName = "seq_usuarios", allocationSize = 1)
    @Column(name = "usuario_id")
    private Long id;

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
