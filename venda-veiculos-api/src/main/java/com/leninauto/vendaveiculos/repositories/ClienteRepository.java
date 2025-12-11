package com.leninauto.vendaveiculos.repositories;

import com.leninauto.vendaveiculos.pessoa.Cliente; // Sua entidade
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}

