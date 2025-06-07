package com.caiofeiria.planit.models;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.caiofeiria.planit.enums.Prioridade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
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
@Table(name = "TB_TAREFAS")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tarefas")
    @SequenceGenerator(name = "seq_tarefas", sequenceName = "seq_tarefas", allocationSize = 1)
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
    private Projeto projeto;
    
    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Usuario responsavel;

    @ManyToMany(mappedBy = "tarefas")
    private List<Usuario> usuarios;
}
