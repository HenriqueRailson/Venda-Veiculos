package com.leninauto.vendaveiculos.Venda;
import com.leninauto.vendaveiculos.automoveis.Veiculo; // Importe os objetos
import com.leninauto.vendaveiculos.pessoa.Cliente;
import com.leninauto.vendaveiculos.pessoa.Vendedor;
import java.time.LocalDate;

public interface IVenda {

    Veiculo getVeiculoVendido();
    void setVeiculoVendido(Veiculo veiculoVendido);


    Cliente getClienteComprador();
    void setClienteComprador(Cliente clienteComprador);


    Vendedor getVendedor();
    void setVendedor(Vendedor vendedor);


    LocalDate getDataVenda();
    void setDataVenda(LocalDate dataVenda);

    double getValorFinal();
    void setValorFinal(double valorFinal);

    String getFormaPagamento();
    void setFormaPagamento(String formaPagamento);

    double calcularValorTotal();
}