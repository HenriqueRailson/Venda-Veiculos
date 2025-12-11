package com.leninauto.vendaveiculos.automoveis;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TB_CARROS")
public class Carro extends Veiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private int numeroPortas;

    @Column(nullable = false, length = 30)
    private String tipoCarro;

    public Carro(){
        super();
        this.setTipo("CARRO");
    }


    public Carro(String modelo, String marca, String cor, int anoFabricacao, String chassi, double precoCompra, int numeroPortas, String tipoCarro) {

        super("CARRO", modelo, marca, cor, anoFabricacao, chassi, precoCompra, StatusVeiculo.Disponivel);
        this.numeroPortas = numeroPortas;
        this.tipoCarro = tipoCarro;
    }


    public Carro(String modelo, String marca, String cor, int anoFabricacao, String chassi, double precoCompra, int numeroPortas, String tipoCarro, StatusVeiculo statusVeiculo) {
        super("CARRO", modelo, marca, cor, anoFabricacao, chassi, precoCompra, statusVeiculo);
        this.numeroPortas = numeroPortas;
        this.tipoCarro = tipoCarro;
    }

    public int getNumeroPortas() {
        return numeroPortas;
    }

    public void setNumeroPortas(int numeroPortas) {
        this.numeroPortas = numeroPortas;
    }

    public String getTipoCarro() {
        return tipoCarro;
    }

    public void setTipoCarro(String tipoCarro) {
        this.tipoCarro = tipoCarro;
    }

    @Override
    public String ModeloVeiculo() {
        return getMarca() + " " + getModelo() + " - " + getAnoFabricacao();
    }
}