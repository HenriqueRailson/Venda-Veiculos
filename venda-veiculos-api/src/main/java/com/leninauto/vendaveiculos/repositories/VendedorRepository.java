package com.leninauto.vendaveiculos.repositories;

import com.leninauto.vendaveiculos.pessoa.Vendedor; // Importa sua entidade Vendedor
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

}