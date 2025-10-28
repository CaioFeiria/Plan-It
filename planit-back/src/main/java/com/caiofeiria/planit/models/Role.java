package com.caiofeiria.planit.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_ROLES")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @NotBlank(message = "O nome da role é obrigatório.")
    @Size(min = 2, max = 50, message = "O nome da role deve ter entre 2 e 50 caracteres.")
    @Column(name = "nome", nullable = false, length = 50, unique = true)
    private String nome;

    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
    @Column(name = "descricao", length = 255)
    private String descricao;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Usuario> usuarios;
}
