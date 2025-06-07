package com.caiofeiria.planit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caiofeiria.planit.models.Projeto;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
	
	List<Projeto> findByNomeContainingIgnoreCase(String nome);
}
