package com.santiago.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santiago.domain.Fatura;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Long> {
	
}
