package com.caiofeiria.planit.mappers;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.caiofeiria.planit.dtos.usuario.UsuarioRequestDTO;
import com.caiofeiria.planit.dtos.usuario.UsuarioResponseDTO;
import com.caiofeiria.planit.dtos.usuario.UsuarioUpdateDTO;
import com.caiofeiria.planit.models.Tarefa;
import com.caiofeiria.planit.models.Usuario;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario entity = new Usuario();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public static UsuarioResponseDTO toResponseDTO(Usuario e) {
        return new UsuarioResponseDTO(
                e.getId(),
                e.getNome(),
                e.getEmail(),
                e.getTarefas()
        );
    }
    
    public static Usuario toEntityResponse(UsuarioResponseDTO dto) {
    	Usuario entity = new Usuario();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
    
    public static Usuario toEntityUpdate(UsuarioUpdateDTO dto, List<Tarefa> tarefas) {
    	Usuario e = new Usuario();
        e.setId(dto.id());
        e.setNome(dto.nome());
        e.setEmail(dto.email());
        e.setTarefas(tarefas);
        return e;
    }
}
