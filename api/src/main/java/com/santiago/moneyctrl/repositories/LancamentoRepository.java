package com.santiago.moneyctrl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santiago.moneyctrl.domain.Lancamento;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
