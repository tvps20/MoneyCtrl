package com.santiago.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santiago.domain.Cartao;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long>{

}
