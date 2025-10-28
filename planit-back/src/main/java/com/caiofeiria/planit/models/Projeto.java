package com.caiofeiria.planit.models;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Table(name = "TB_PROJETOS")
public class Projeto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projeto_id")
    private Long id;

    @NotBlank(message = "Digite o nome do Projeto.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "Adicione uma descrição ao Projeto.")
    @Size(min = 3, max = 255, message = "A descrição deve ter entre 3 e 255 caracteres.")
    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @Size(max = 10, message = "Emoji deve ter no máximo 10 caracteres.")
    @Column(name = "emoji", length = 10)
    private String emoji;
    
    @OneToMany(mappedBy = "projeto", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<Tarefa> tarefas;
    
}
