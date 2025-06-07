package com.caiofeiria.planit.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caiofeiria.planit.dtos.tarefa.TarefaResponseDTO;
import com.caiofeiria.planit.exceptions.invalid.TarefaInvalidaException;
import com.caiofeiria.planit.exceptions.nocontent.TarefaNoContentException;
import com.caiofeiria.planit.exceptions.notfound.TarefaNotFoundException;
import com.caiofeiria.planit.exceptions.notfound.UsuarioNotFoundException;
import com.caiofeiria.planit.mappers.TarefaMapper;
import com.caiofeiria.planit.models.Tarefa;
import com.caiofeiria.planit.models.Usuario;
import com.caiofeiria.planit.repositories.TarefaRepository;
import com.caiofeiria.planit.repositories.UsuarioRepository;
import com.caiofeiria.planit.utils.Validate;

@Service
public class ApontamentoService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	public void associarTarefa(Long usuarioId, Long tarefaId) {
        Validate.validarId(usuarioId);
        Validate.validarId(tarefaId);

        Usuario usuario = repository.findById(usuarioId)
                .orElseThrow(UsuarioNotFoundException::new);

        Tarefa tarefa = tarefaRepository.findById(tarefaId)
                .orElseThrow(TarefaNotFoundException::new);

        if (!usuario.getTarefas().contains(tarefa)) {
            usuario.getTarefas().add(tarefa);
            repository.save(usuario);
        } else {
        	throw new TarefaInvalidaException("O usuário " + usuario.getNome() + ", já está associado à essa tarefa.");
        }
    }

    public List<TarefaResponseDTO> listarTarefasDoUsuario(Long usuarioId) {
        Validate.validarId(usuarioId);
        
        Usuario usuario = repository.findById(usuarioId)
                .orElseThrow(UsuarioNotFoundException::new);
        
        List<TarefaResponseDTO> tarefasUsuario = usuario.getTarefas()
        		.stream()
        		.map(TarefaMapper::toResponseDTO)
        		.collect(Collectors.toList());
        
        if (tarefasUsuario.isEmpty()) {
			throw new TarefaNoContentException();
		}
        
        return tarefasUsuario;
    }
}
