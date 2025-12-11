package com.leninauto.vendaveiculos.automoveis;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TB_MOTOS")
public class Moto extends Veiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 20)
    private String cilindrada;


    @Column(nullable = false, length = 20)
    private String tipoFreio;

    public Moto(){
        super();
        this.setTipo("MOTO");
    }


    public Moto(String modelo, String marca, String cor, int anoFabricacao, String chassi, double precoCompra, String cilindrada, String tipoFreio) {

        super("MOTO", modelo, marca, cor, anoFabricacao, chassi, precoCompra, StatusVeiculo.Disponivel);
        this.cilindrada = cilindrada;
        this.tipoFreio = tipoFreio;
    }


    public Moto(String modelo, String marca, String cor, int anoFabricacao, String chassi, double precoCompra, String cilindrada, String tipoFreio, StatusVeiculo statusVeiculo) {
        super("MOTO", modelo, marca, cor, anoFabricacao, chassi, precoCompra, statusVeiculo);
        this.cilindrada = cilindrada;
        this.tipoFreio = tipoFreio;
    }

    public String getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(String cilindrada) {
        this.cilindrada = cilindrada;
    }

    public String getTipoFreio() {
        return tipoFreio;
    }

    public void setTipoFreio(String tipoFreio) {
        this.tipoFreio = tipoFreio;
    }

    @Override
    public String ModeloVeiculo() {
        return getMarca() + " " + getModelo() + " " + cilindrada + " - " + getAnoFabricacao();
    }
}