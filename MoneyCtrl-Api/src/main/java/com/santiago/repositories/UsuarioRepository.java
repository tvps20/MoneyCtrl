package com.santiago.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.santiago.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM USUARIO c WHERE c.email = ?1) THEN CAST (1 as BIT) ELSE CAST (0 as BIT) END", nativeQuery = true)
	boolean verificarCampoUnico(String campo);
}
