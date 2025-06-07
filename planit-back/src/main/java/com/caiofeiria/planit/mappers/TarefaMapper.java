package com.caiofeiria.planit.mappers;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.caiofeiria.planit.dtos.tarefa.TarefaRequestDTO;
import com.caiofeiria.planit.dtos.tarefa.TarefaResponseDTO;
import com.caiofeiria.planit.dtos.tarefa.TarefaUpdateDTO;
import com.caiofeiria.planit.dtos.usuario.UsuarioResumoDTO;
import com.caiofeiria.planit.models.Projeto;
import com.caiofeiria.planit.models.Tarefa;
import com.caiofeiria.planit.models.Usuario;

public class TarefaMapper {

    public static Tarefa toEntity(TarefaRequestDTO dto) {
        Tarefa entity = new Tarefa();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public static TarefaResponseDTO toResponseDTO(Tarefa e) {
        return new TarefaResponseDTO(
                e.getId(),
                e.getNome(),
                e.getDescricao(),
                e.getPrioridade(),
                e.getDataCriacao(),
                e.getDataConclusao(),
                e.getProjeto(),
                toUsuarioResumoDto(e.getResponsavel()),
                e.getUsuarios().stream()
                	.map(TarefaMapper::toUsuarioResumoDto)
                	.toList()
        );
        
    }
    
    public static Tarefa updateEntityDTO(
            TarefaUpdateDTO dto,
            Projeto projeto,
            Usuario responsavel,
            List<Usuario> usuarios
    ) {
        Tarefa e = new Tarefa();
        e.setId(dto.id());
        e.setNome(dto.nome());
        e.setDescricao(dto.descricao());
        e.setDataCriacao(dto.dataCriacao());
        e.setDataConclusao(dto.dataConclusao());
        e.setProjeto(projeto);
        e.setResponsavel(responsavel);
        e.setPrioridade(dto.prioridade());
        e.setUsuarios(usuarios);
        return e;
    }
    
    private static UsuarioResumoDTO toUsuarioResumoDto(Usuario usuario) {
        return usuario != null ?
            new UsuarioResumoDTO(usuario.getId(), usuario.getNome(), usuario.getEmail()) :
            null;
    }

}
