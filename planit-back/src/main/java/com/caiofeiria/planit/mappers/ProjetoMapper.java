package com.caiofeiria.planit.mappers;

import com.caiofeiria.planit.dtos.projeto.ProjetoRequestDTO;
import com.caiofeiria.planit.dtos.projeto.ProjetoResponseDTO;
import com.caiofeiria.planit.models.Projeto;
import org.springframework.beans.BeanUtils;

public class ProjetoMapper {

    public static Projeto toEntity(ProjetoRequestDTO dto) {
        Projeto entity = new Projeto();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public static ProjetoResponseDTO toResponseDTO(Projeto e) {
        return new ProjetoResponseDTO(
                e.getId(),
                e.getNome(),
                e.getDescricao()
        );
    }
    
    public static Projeto toEntityResponse(ProjetoResponseDTO dto) {
        Projeto entity = new Projeto();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
    
    public static ProjetoRequestDTO toRequestDTO(Projeto e) {
    	return new ProjetoRequestDTO(
    			e.getNome(),
    			e.getDescricao()
    	);
    }
}
