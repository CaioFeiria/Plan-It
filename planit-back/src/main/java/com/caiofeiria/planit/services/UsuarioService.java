package com.caiofeiria.planit.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private TarefaRepository tarefaRepository;

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

	public UsuarioResponseDTO buscarPorId(Long id) {
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

	public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO dto) {
		Usuario usuario = UsuarioMapper.toEntity(dto);
		repository.save(usuario);
		return UsuarioMapper.toResponseDTO(usuario);
	}

	public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioUpdateDTO dto) {
		Validate.validarId(id);

		if (id != dto.id()) {
			throw new InvalidBodyAndUrlId();
		}
		
		repository.findById(id)
			.orElseThrow(UsuarioNotFoundException::new);
		
		List<Long> tarefasDTO = dto.tarefas();
		List<Tarefa> tarefas = new ArrayList<>();
		
		for (Long idTarefa : tarefasDTO) {
			Tarefa tarefa = tarefaRepository.findById(idTarefa)
				.orElseThrow(TarefaNotFoundException::new);
			
			tarefas.add(tarefa);
		}

		Usuario updated = UsuarioMapper.toEntityUpdate(dto, tarefas);
		repository.save(updated);
		return UsuarioMapper.toResponseDTO(updated);
	}

	public void deletarUsuario(Long id) {
		Validate.validarId(id);
		Usuario existing = repository.findById(id).orElseThrow(UsuarioNotFoundException::new);
		repository.deleteById(existing.getId());
	}
}
