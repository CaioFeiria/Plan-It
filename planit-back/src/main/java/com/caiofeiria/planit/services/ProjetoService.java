package com.caiofeiria.planit.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caiofeiria.planit.dtos.projeto.ProjetoRequestDTO;
import com.caiofeiria.planit.dtos.projeto.ProjetoResponseDTO;
import com.caiofeiria.planit.exceptions.invalid.InvalidBodyAndUrlId;
import com.caiofeiria.planit.exceptions.nocontent.ProjetoNoContentException;
import com.caiofeiria.planit.exceptions.notfound.ProjetoNotFoundException;
import com.caiofeiria.planit.mappers.ProjetoMapper;
import com.caiofeiria.planit.models.Projeto;
import com.caiofeiria.planit.repositories.ProjetoRepository;
import com.caiofeiria.planit.utils.Validate;

@Service
public class ProjetoService {

	@Autowired
	private ProjetoRepository repository;

	public List<ProjetoResponseDTO> listarProjetos() {
		List<ProjetoResponseDTO> listaProjetos = repository.findAll().stream().map(ProjetoMapper::toResponseDTO)
				.collect(Collectors.toList());

		if (listaProjetos.isEmpty()) {
			throw new ProjetoNoContentException();
		}

		return listaProjetos;
	}

	public ProjetoResponseDTO buscarPorId(Long id) {
		Validate.validarId(id);
		Projeto projeto = repository.findById(id).orElseThrow(ProjetoNotFoundException::new);

		return ProjetoMapper.toResponseDTO(projeto);
	}

	public List<ProjetoResponseDTO> buscarPorNome(String nome) {
		List<ProjetoResponseDTO> listaNomes = repository.findByNomeContainingIgnoreCase(nome).stream()
				.map(ProjetoMapper::toResponseDTO).collect(Collectors.toList());

		if (listaNomes.isEmpty()) {
			throw new ProjetoNoContentException();
		}

		return listaNomes;
	}

	public ProjetoResponseDTO criarProjeto(ProjetoRequestDTO dto) {
		Projeto projeto = ProjetoMapper.toEntity(dto);
		repository.save(projeto);
		return ProjetoMapper.toResponseDTO(projeto);
	}

	public ProjetoResponseDTO atualizarProjeto(Long id, ProjetoResponseDTO dto) {
		Validate.validarId(id);

		if (id != dto.id()) {
			throw new InvalidBodyAndUrlId();
		}

		Projeto updated = ProjetoMapper.toEntityResponse(dto);
		repository.save(updated);
		return ProjetoMapper.toResponseDTO(updated);
	}

	public void deletarProjeto(Long id) {
		Validate.validarId(id);
		Projeto existing = repository.findById(id).orElseThrow(ProjetoNotFoundException::new);
		repository.deleteById(existing.getId());
	}
}
