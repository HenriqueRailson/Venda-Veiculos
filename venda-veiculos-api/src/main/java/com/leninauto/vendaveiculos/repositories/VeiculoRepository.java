package com.leninauto.vendaveiculos.repositories;

import com.leninauto.vendaveiculos.automoveis.Veiculo; // Importa a superclasse Veiculo
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

}