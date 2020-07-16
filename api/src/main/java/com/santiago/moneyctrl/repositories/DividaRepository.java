package com.santiago.moneyctrl.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santiago.moneyctrl.domain.Divida;

@Repository
public interface DividaRepository extends JpaRepository<Divida, Long> {

	List<Divida> findByPaga(boolean paga);

	Page<Divida> findByPaga(boolean paga, Pageable pageable);
}
