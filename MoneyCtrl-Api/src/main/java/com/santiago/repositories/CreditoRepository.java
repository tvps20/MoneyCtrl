package com.santiago.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.santiago.domain.Credito;

public interface CreditoRepository extends JpaRepository<Credito, Long> {

}
