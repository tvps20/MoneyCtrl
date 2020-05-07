package com.santiago.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.santiago.domain.Bandeira;

@Repository
public interface BandeiraRepository extends JpaRepository<Bandeira, Long> {

	@Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM Bandeira c WHERE c.nome = :campo")
	boolean verificarCampoUnico(@Param("campo") String campo);
}
