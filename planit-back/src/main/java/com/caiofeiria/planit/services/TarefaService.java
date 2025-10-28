package com.caiofeiria.planit.services;

import com.caiofeiria.planit.dtos.tarefa.TarefaRequestDTO;
import com.caiofeiria.planit.dtos.tarefa.TarefaResponseDTO;
import com.caiofeiria.planit.dtos.tarefa.TarefaUpdateDTO;
import com.caiofeiria.planit.exceptions.invalid.InvalidBodyAndUrlId;
import com.caiofeiria.planit.exceptions.invalid.TarefaInvalidaException;
import com.caiofeiria.planit.exceptions.nocontent.TarefaNoContentException;
import com.caiofeiria.planit.exceptions.notfound.ProjetoNotFoundException;
import com.caiofeiria.planit.exceptions.notfound.TarefaNotFoundException;
import com.caiofeiria.planit.exceptions.notfound.UsuarioNotFoundException;
import com.caiofeiria.planit.mappers.TarefaMapper;
import com.caiofeiria.planit.models.Projeto;
import com.caiofeiria.planit.models.Tarefa;
import com.caiofeiria.planit.models.Usuario;
import com.caiofeiria.planit.repositories.ProjetoRepository;
import com.caiofeiria.planit.repositories.TarefaRepository;
import com.caiofeiria.planit.repositories.UsuarioRepository;
import com.caiofeiria.planit.utils.Validate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TarefaService {

	@Autowired
	private TarefaRepository tarefaRepository;
	
	@Autowired
	private ProjetoRepository projetoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<TarefaResponseDTO> listarTarefas() {
		List<TarefaResponseDTO> listaTarefas = tarefaRepository.findAll()
				.stream()
				.map(TarefaMapper::toResponseDTO)
				.toList();

		if (listaTarefas.isEmpty()) {
			throw new TarefaNoContentException();
		}

		return listaTarefas;
	}

	public TarefaResponseDTO buscarPorId(Long id) {
		Validate.validarId(id);
		Tarefa tarefa = tarefaRepository.findById(id)
				.orElseThrow(TarefaNoContentException::new);

		return TarefaMapper.toResponseDTO(tarefa);
	}

	@Transactional
	public TarefaResponseDTO criarTarefa(TarefaRequestDTO dto) {
		
		if (dto.responsavel() == null || dto.projeto() == null) {
			throw new TarefaInvalidaException("A tarefa deve ter um responsável e estar vinculada a um projeto.");
		}
		
		Projeto projeto = projetoRepository.findById(dto.projeto())
				.orElseThrow(ProjetoNotFoundException::new);
		
		Usuario usuario = usuarioRepository.findById(dto.responsavel())
				.orElseThrow(UsuarioNotFoundException::new);
				
		List<Long> usuariosIds = dto.usuariosIds();
		List<Usuario> usuarios = new ArrayList<>();

		usuarios = usuariosIds.stream()
				.map(usuarioId -> usuarioRepository.findById(usuarioId)
						.orElseThrow(UsuarioNotFoundException::new))
				.toList();
		
		Tarefa tarefa = TarefaMapper.toEntity(dto);
		tarefa.setDataCriacao(LocalDateTime.now());
		tarefa.setProjeto(projeto);
		tarefa.setResponsavel(usuario);
		tarefa.setUsuarios(usuarios);
		tarefaRepository.save(tarefa);
		return TarefaMapper.toResponseDTO(tarefa);
	}

	@Transactional
	public TarefaResponseDTO atualizarTarefa(Long id, TarefaUpdateDTO dto) {
		Validate.validarId(id);

		if (id != dto.id()) {
			throw new InvalidBodyAndUrlId();
		}
		
		Projeto projeto = projetoRepository.findById(dto.projeto())
				.orElseThrow(ProjetoNotFoundException::new);
		
		Usuario responsavel = usuarioRepository.findById(dto.responsavel())
				.orElseThrow(UsuarioNotFoundException::new);
		
		List<Long> usuariosIds = dto.usuariosIds();
		List<Usuario> usuarios = new ArrayList<>();

		usuarios = usuariosIds.stream()
				.map(usuarioId -> usuarioRepository.findById(usuarioId)
						.orElseThrow(UsuarioNotFoundException::new))
				.toList();

		Tarefa updated = TarefaMapper.updateEntityDTO(dto, projeto, responsavel, usuarios);
		
		updated.getUsuarios().forEach(usuario -> System.out.println(usuario.getNome()));
		
		tarefaRepository.save(updated);
		return TarefaMapper.toResponseDTO(updated);
	}

	@Transactional
	public void deletarTarefa(Long id) {
		Validate.validarId(id);
		
		Tarefa existing = tarefaRepository.findById(id)
				.orElseThrow(TarefaNotFoundException::new);
		
		usuarioRepository.deleteByTarefaId(existing.getId());
		
		tarefaRepository.deleteById(existing.getId());
	}

	public List<TarefaResponseDTO> buscarPorProjeto(Long projetoId) {
		Validate.validarId(projetoId);
		
		projetoRepository.findById(projetoId)
				.orElseThrow(ProjetoNotFoundException::new);
		
		List<TarefaResponseDTO> tarefas = tarefaRepository.findAll()
				.stream()
				.filter(tarefa -> tarefa.getProjeto().getId().equals(projetoId))
				.map(TarefaMapper::toResponseDTO)
				.toList();
		
		if (tarefas.isEmpty()) {
			throw new TarefaNoContentException();
		}
		
		return tarefas;
	}

	public List<TarefaResponseDTO> buscarPorResponsavel(Long responsavelId) {
		Validate.validarId(responsavelId);
		
		usuarioRepository.findById(responsavelId)
				.orElseThrow(UsuarioNotFoundException::new);
		
		List<TarefaResponseDTO> tarefas = tarefaRepository.findAll()
				.stream()
				.filter(tarefa -> tarefa.getResponsavel().getId().equals(responsavelId))
				.map(TarefaMapper::toResponseDTO)
				.toList();
		
		if (tarefas.isEmpty()) {
			throw new TarefaNoContentException();
		}
		
		return tarefas;
	}
}
