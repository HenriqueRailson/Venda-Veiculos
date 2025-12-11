package com.leninauto.vendaveiculos.automoveis;

public interface IVeiculo{
    String getModelo();
    
    String getMarca();
    
    double getPrecoCompra();
    
    double precoSugerido();


    void setPrecoCompra(double precoCompra);

    String ModeloVeiculo();
    
    StatusVeiculo getStatusVeiculo();
    
    void setStatusVeiculo(StatusVeiculo statusVeiculo);
}

