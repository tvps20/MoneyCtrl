package com.santiago.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.santiago.domain.Cartao;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
	
	@Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM Cartao c WHERE c.nome = :campo")
	boolean verificarCampoUnico(String campo);
}
