package com.caiofeiria.planit.repositories;

import com.caiofeiria.planit.models.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, UUID> {
	
	List<Projeto> findByNomeContainingIgnoreCase(String nome);
}
