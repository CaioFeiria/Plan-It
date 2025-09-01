package com.caiofeiria.planit.services;

import com.caiofeiria.planit.dtos.usuario.UsuarioRequestDTO;
import com.caiofeiria.planit.dtos.usuario.UsuarioResponseDTO;
import com.caiofeiria.planit.dtos.usuario.UsuarioUpdateDTO;
import com.caiofeiria.planit.exceptions.invalid.InvalidBodyAndUrlId;
import com.caiofeiria.planit.exceptions.notfound.TarefaNotFoundException;
import com.caiofeiria.planit.exceptions.notfound.UsuarioNotFoundException;
import com.caiofeiria.planit.mappers.UsuarioMapper;
import com.caiofeiria.planit.models.Tarefa;
import com.caiofeiria.planit.models.Usuario;
import com.caiofeiria.planit.repositories.TarefaRepository;
import com.caiofeiria.planit.repositories.UsuarioRepository;
import com.caiofeiria.planit.utils.Validate;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

	private final UsuarioRepository repository;
	private final TarefaRepository tarefaRepository;

	public UsuarioService(UsuarioRepository repository, TarefaRepository tarefaRepository){
		this.repository = repository;
		this.tarefaRepository = tarefaRepository;
	}

	public List<UsuarioResponseDTO> listarUsuarios() {
		List<UsuarioResponseDTO> listarUsuarios = repository.findAll()
				.stream()
				.map(UsuarioMapper::toResponseDTO)
				.collect(Collectors.toList());

		if (listarUsuarios.isEmpty()) {
			throw new UsuarioNotFoundException();
		}

		return listarUsuarios;
	}

	public UsuarioResponseDTO buscarPorId(UUID id) {
		Validate.validarId(id);
		Usuario usuario = repository.findById(id).orElseThrow(UsuarioNotFoundException::new);

		return UsuarioMapper.toResponseDTO(usuario);
	}

	public List<UsuarioResponseDTO> buscarPorNome(String nome) {
		List<UsuarioResponseDTO> listaNomes = repository.findByNomeContainingIgnoreCase(nome)
				.stream()
				.map(UsuarioMapper::toResponseDTO)
				.collect(Collectors.toList());

		if (listaNomes.isEmpty()) {
			throw new UsuarioNotFoundException();
		}

		return listaNomes;
	}
	
	public List<UsuarioResponseDTO> buscarPorEmail(String email) {
		List<UsuarioResponseDTO> listaUsuarios = repository.buscarPorEmail(email)
				.stream()
				.map(UsuarioMapper::toResponseDTO)
				.collect(Collectors.toList());

		if (listaUsuarios.isEmpty()) {
			throw new UsuarioNotFoundException();
		}

		return listaUsuarios;
	}

	@Transactional
	public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO dto) {
		Usuario usuario = UsuarioMapper.toEntity(dto);
		repository.save(usuario);
		return UsuarioMapper.toResponseDTO(usuario);
	}

	@Transactional
	public UsuarioResponseDTO atualizarUsuario(UUID id, UsuarioUpdateDTO dto) {
		Validate.validarId(id);

		if (id != dto.id()) {
			throw new InvalidBodyAndUrlId();
		}
		
		repository.findById(id)
			.orElseThrow(UsuarioNotFoundException::new);
		
		List<UUID> tarefasDTO = dto.tarefas();
		List<Tarefa> tarefas = new ArrayList<>();
		
		for (UUID idTarefa : tarefasDTO) {
			Tarefa tarefa = tarefaRepository.findById(idTarefa)
				.orElseThrow(TarefaNotFoundException::new);
			
			tarefas.add(tarefa);
		}

		Usuario updated = UsuarioMapper.toEntityUpdate(dto, tarefas);
		repository.save(updated);
		return UsuarioMapper.toResponseDTO(updated);
	}

	@Transactional
	public void deletarUsuario(UUID id) {
		Validate.validarId(id);
		Usuario existing = repository.findById(id)
				.orElseThrow(UsuarioNotFoundException::new);
		repository.deleteById(existing.getId());
	}
}
