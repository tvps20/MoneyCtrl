package com.santiago.moneyctrl.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.santiago.moneyctrl.domain.Fatura;
import com.santiago.moneyctrl.domain.enuns.TipoStatus;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Long> {

	@Query("SELECT f FROM Fatura f WHERE f.status = :status")
	List<Fatura> noFindByStatus(TipoStatus status);

	Page<Fatura> findByStatus(TipoStatus status, Pageable pageable);

	@Query("SELECT f FROM Fatura f WHERE f.status != :status")
	Page<Fatura> noFindByStatus(TipoStatus status, Pageable pageable);
}
