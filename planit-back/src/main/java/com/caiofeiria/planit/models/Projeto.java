package com.caiofeiria.planit.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Table(name = "TB_PROJETOS")
public class Projeto {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "projeto_id")
    private UUID id;

    @NotBlank(message = "Digite o nome do Projeto.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "Adicione uma descrição ao Projeto.")
    @Size(min = 3, max = 255, message = "A descrição deve ter entre 3 e 255 caracteres.")
    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;
    
}
