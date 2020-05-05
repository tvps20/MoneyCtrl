package com.santiago.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santiago.domain.Divida;

@Repository
public interface DividaRepository extends JpaRepository<Divida, Long> {

}
