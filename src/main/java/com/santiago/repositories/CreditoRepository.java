package com.santiago.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santiago.domain.Credito;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long> {

}
