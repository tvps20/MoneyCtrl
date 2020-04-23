package com.santiago.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.santiago.domain.Cartao;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
	
	@Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM CARTAO c WHERE c.nome = ?1) THEN CAST (1 as BIT) ELSE CAST (0 as BIT) END", nativeQuery = true)
	boolean verificarCampoUnico(String campo);
}
