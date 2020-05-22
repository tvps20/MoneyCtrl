package com.santiago.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santiago.domain.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

	List<Pagamento> findByDividaId(Long dividaId);

	Page<Pagamento> findByDividaId(Long dividaId, Pageable pageable);
}
