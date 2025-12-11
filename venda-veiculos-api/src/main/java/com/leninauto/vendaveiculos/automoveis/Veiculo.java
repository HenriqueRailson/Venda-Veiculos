package com.leninauto.vendaveiculos.automoveis;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import java.io.Serializable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "tipo",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Carro.class, name = "CARRO"),
})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TB_VEICULOS_BASE")
public abstract class Veiculo implements IVeiculo, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String tipo;

    @Column(nullable = false, length = 50)
    private String modelo;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, length = 30)
    private String cor;

    @Column(nullable = false)
    private int anoFabricacao;

    @Column(nullable = false, unique = true, length = 20)
    private String chassi;

    @Column(nullable = false)
    private double precoCompra;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusVeiculo statusVeiculo;


    public Veiculo() {
        this.statusVeiculo = StatusVeiculo.Disponivel;
    }

    public Veiculo(String tipo, String modelo, String marca, String cor, int anoFabricacao, String chassi, double precoCompra, StatusVeiculo statusVeiculo) {
        this.tipo = tipo;
        this.modelo = modelo;
        this.marca = marca;
        this.cor = cor;
        this.anoFabricacao = anoFabricacao;
        this.chassi = chassi;
        this.precoCompra = precoCompra;
        this.statusVeiculo = statusVeiculo;
    }



    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    @Override public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    @Override public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    public int getAnoFabricacao() { return anoFabricacao; }
    public void setAnoFabricacao(int anoFabricacao) { this.anoFabricacao = anoFabricacao; }

    public String getChassi() { return chassi; }
    public void setChassi(String chassi) { this.chassi = chassi; }

    @Override public double getPrecoCompra() { return precoCompra; }
    @Override public void setPrecoCompra(double precoCompra) { this.precoCompra = precoCompra; }

    @Override public StatusVeiculo getStatusVeiculo() { return statusVeiculo; }
    @Override public void setStatusVeiculo(StatusVeiculo statusVeiculo) { this.statusVeiculo = statusVeiculo; }


    @Override
    public abstract String ModeloVeiculo();

    @Override
    public double precoSugerido() {
        final double margemPadrao = 1.10;
        return this.precoCompra * margemPadrao;
    }
}