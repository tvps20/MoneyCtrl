package com.santiago.moneyctrl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.santiago.moneyctrl.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM Usuario c WHERE c.username = :campo")
	boolean verificarCampoUnico(String campo);
	
	@Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM Usuario c WHERE c.email = :email")
	boolean verificarEmailUnico(String email);
}
