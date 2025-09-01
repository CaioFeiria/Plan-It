package com.caiofeiria.planit.repositories;

import com.caiofeiria.planit.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
	
	List<Usuario> findByNomeContainingIgnoreCase(String nome);
	
	@Query("SELECT u FROM Usuario u WHERE u.email LIKE %:email%")
	List<Usuario> buscarPorEmail(@Param("email") String email);

	@Query(value = "SELECT tarefa_id FROM usuario_tarefa WHERE usuario_id = :id", nativeQuery = true)
	List<UUID> buscarTarefaIdsPorUsuario(@Param("id") UUID usuarioId);
	
    @Query(value = "DELETE FROM usuario_tarefa WHERE tarefa_id = :idTarefa", nativeQuery = true)
    void deleteByTarefaId(@Param("idTarefa") UUID idTarefa);

}
