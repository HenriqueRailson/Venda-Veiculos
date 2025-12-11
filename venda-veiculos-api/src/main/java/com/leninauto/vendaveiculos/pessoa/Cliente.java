package com.leninauto.vendaveiculos.pessoa;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TB_CLIENTES")
public class Cliente extends Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public Cliente(String nome, String cpf, String telefone, String email, String endereco){
        super(nome, cpf, telefone, email, endereco);
    }

    public Cliente(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}