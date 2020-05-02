package com.santiago.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santiago.domain.Comprador;

@Repository
public interface CompradorRepository extends JpaRepository<Comprador, Long> {

}
