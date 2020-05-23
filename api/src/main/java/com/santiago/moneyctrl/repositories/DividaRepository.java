package com.santiago.moneyctrl.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santiago.moneyctrl.domain.Divida;

@Repository
public interface DividaRepository extends JpaRepository<Divida, Long> {

}
