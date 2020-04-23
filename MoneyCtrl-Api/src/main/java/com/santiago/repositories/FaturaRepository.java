package com.santiago.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.santiago.domain.Fatura;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Long> {

	@Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM FATURA f WHERE f.mes_referente = ?1) THEN CAST (1 as BIT) ELSE CAST (0 as BIT) END", nativeQuery = true)
	boolean verificarCampoUnico(String campo);
}
