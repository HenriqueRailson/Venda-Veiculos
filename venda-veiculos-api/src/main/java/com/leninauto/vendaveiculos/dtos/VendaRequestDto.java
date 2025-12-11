package com.leninauto.vendaveiculos.dtos;

import java.time.LocalDate;


public class VendaRequestDto {

    private Long clienteId;
    private Long vendedorId;
    private Long veiculoId;
    private double valorFinal;
    private String formaPagamento;
    private LocalDate dataVenda;

    public VendaRequestDto() {}



    public Long getClienteId() {
        return clienteId;
    }
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getVendedorId() {
        return vendedorId;
    }
    public void setVendedorId(Long vendedorId) {
        this.vendedorId = vendedorId;
    }

    public Long getVeiculoId() {
        return veiculoId;
    }
    public void setVeiculoId(Long veiculoId) {
        this.veiculoId = veiculoId;
    }

    public double getValorFinal() {
        return valorFinal;
    }
    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }
    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }
    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }
}