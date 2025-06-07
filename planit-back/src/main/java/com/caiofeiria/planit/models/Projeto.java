package com.caiofeiria.planit.models;

import org.springframework.validation.annotation.Validated;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Table(name = "TB_PROJETOS")
public class Projeto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_projetos")
    @SequenceGenerator(name = "seq_projetos", sequenceName = "seq_projetos", allocationSize = 1)
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
    
}
