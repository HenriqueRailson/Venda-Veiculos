package com.leninauto.vendaveiculos.repositories;

import com.leninauto.vendaveiculos.Venda.Venda; // Importa a entidade Venda
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

}