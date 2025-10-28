package com.caiofeiria.planit.models;

import com.caiofeiria.planit.enums.Prioridade;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "TB_TAREFAS")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tarefa_id")
    private Long id;

    @NotBlank(message = "O nome da tarefa não pode estar em branco.")
    @Size(max = 100, message = "O nome da tarefa deve ter no máximo 100 caracteres.")
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade")
    private Prioridade prioridade;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Future(message = "Data de conclusão deve ser maior que a data atual.")
    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;

    @ManyToOne
    @JoinColumn(name = "projeto_id")
    @JsonIgnoreProperties({"tarefas"})
    private Projeto projeto;
    
    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    @JsonIgnoreProperties({"tarefas", "senha"})
    private Usuario responsavel;

    @ManyToMany(mappedBy = "tarefas")
    @JsonIgnoreProperties({"tarefas", "senha"})
    private List<Usuario> usuarios;
}
