package com.santiago.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santiago.domain.Credito;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long> {

	List<Credito> findByCompradorId(Long compradorId);
	
	Page<Credito> findByCompradorId(Long compradorId, Pageable pageable);
}
