package com.caiofeiria.planit.services;

import com.caiofeiria.planit.dtos.usuario.UsuarioRequestDTO;
import com.caiofeiria.planit.dtos.usuario.UsuarioResponseDTO;
import com.caiofeiria.planit.dtos.usuario.UsuarioUpdateDTO;
import com.caiofeiria.planit.exceptions.invalid.InvalidBodyAndUrlId;
import com.caiofeiria.planit.exceptions.notfound.TarefaNotFoundException;
import com.caiofeiria.planit.exceptions.notfound.UsuarioNotFoundException;
import com.caiofeiria.planit.mappers.UsuarioMapper;
import com.caiofeiria.planit.models.Role;
import com.caiofeiria.planit.models.Tarefa;
import com.caiofeiria.planit.models.Usuario;
import com.caiofeiria.planit.repositories.TarefaRepository;
import com.caiofeiria.planit.repositories.UsuarioRepository;
import com.caiofeiria.planit.utils.Validate;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

	private final UsuarioRepository repository;
	private final TarefaRepository tarefaRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleService roleService;

	public UsuarioService(UsuarioRepository repository, TarefaRepository tarefaRepository, PasswordEncoder passwordEncoder, RoleService roleService){
		this.repository = repository;
		this.tarefaRepository = tarefaRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleService = roleService;
	}

	public Usuario buscarEntidadePorEmailExato(String email) {
		return repository.findByEmail(email).orElseThrow(UsuarioNotFoundException::new);
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

	@Transactional
	public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO dto) {
		Usuario usuario = UsuarioMapper.toEntity(dto);
		usuario.setSenha(passwordEncoder.encode(dto.senha()));
		
		if (dto.role() != null && !dto.role().isEmpty()) {
			Role role = roleService.findByNome(dto.role())
				.orElseThrow(() -> new RuntimeException("Role " + dto.role() + " não encontrada"));
			usuario.setRole(role);
		} else {
			Role rolePadrao = roleService.findByNome("USUARIO")
				.orElseThrow(() -> new RuntimeException("Role USUARIO não encontrada"));
			usuario.setRole(rolePadrao);
		}
		
		repository.save(usuario);
		return UsuarioMapper.toResponseDTO(usuario);
	}

	@Transactional
	public Usuario registrarUsuario(UsuarioRequestDTO dto) {
		Usuario usuario = UsuarioMapper.toEntity(dto);
		usuario.setSenha(passwordEncoder.encode(dto.senha()));
		
		if (dto.role() != null && !dto.role().isEmpty()) {
			Role role = roleService.findByNome(dto.role())
				.orElseThrow(() -> new RuntimeException("Role " + dto.role() + " não encontrada"));
			usuario.setRole(role);
		} else {
			Role rolePadrao = roleService.findByNome("USUARIO")
				.orElseThrow(() -> new RuntimeException("Role USUARIO não encontrada"));
			usuario.setRole(rolePadrao);
		}
		
		repository.save(usuario);
		return usuario;
	}

	public Usuario validarLogin(String email, String senha) {
		Usuario usuario = repository.findByEmail(email).orElseThrow(UsuarioNotFoundException::new);
		if (!passwordEncoder.matches(senha, usuario.getSenha())) {
			throw new UsuarioNotFoundException();
		}
		return usuario;
	}

	@Transactional
	public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioUpdateDTO dto) {
		Validate.validarId(id);

		if (id != dto.id()) {
			throw new InvalidBodyAndUrlId();
		}
		
		repository.findById(id)
			.orElseThrow(UsuarioNotFoundException::new);
		
		List<Long> tarefasDTO = dto.tarefas();
		List<Tarefa> tarefas = new ArrayList<>();

		tarefas = tarefasDTO.stream()
				.map(tarefa -> tarefaRepository.findById(tarefa)
						.orElseThrow(TarefaNotFoundException::new))
				.toList();

		Usuario updated = UsuarioMapper.toEntityUpdate(dto, tarefas);
		repository.save(updated);
		return UsuarioMapper.toResponseDTO(updated);
	}

	@Transactional
	public void deletarUsuario(Long id) {
		Validate.validarId(id);
		Usuario existing = repository.findById(id)
				.orElseThrow(UsuarioNotFoundException::new);
		repository.deleteById(existing.getId());
	}
}
