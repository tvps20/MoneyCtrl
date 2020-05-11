package com.santiago.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santiago.domain.Cota;

@Repository
public interface CotaRepository extends JpaRepository<Cota, Long> {
	
	List<Cota> findByLancamentoId(Long lancamentoId);
	
	Page<Cota> findByLancamentoId(Long lancamentoId, Pageable pageable);
}
