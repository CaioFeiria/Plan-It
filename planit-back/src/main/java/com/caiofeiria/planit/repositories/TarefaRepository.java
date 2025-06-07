package com.caiofeiria.planit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caiofeiria.planit.models.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
	


}
