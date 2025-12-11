package com.leninauto.vendaveiculos.pessoa;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TB_VENDEDORES")
public class Vendedor extends Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idVendedor;
    private double comissao;
    private String dataContrato;


    public Vendedor(String nome, String cpf, String telefone, String email, String endereco, String idVendedor, double comissao, String dataContrato) {
        super(nome, cpf, telefone, email, endereco);
        this.idVendedor = idVendedor;
        this.comissao = comissao;
        this.dataContrato = dataContrato;
    }

    public Vendedor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }

    public double getComissao() {
        return comissao;
    }

    public void setComissao(double comissao) {
        this.comissao = comissao;
    }

    public String getDataContrato() {
        return dataContrato;
    }

    public void setDataContrato(String dataContrato) {
        this.dataContrato = dataContrato;
    }

}