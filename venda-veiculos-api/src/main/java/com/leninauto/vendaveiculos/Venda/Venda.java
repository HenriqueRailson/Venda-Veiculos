package com.leninauto.vendaveiculos.Venda;

import com.leninauto.vendaveiculos.automoveis.Veiculo;
import com.leninauto.vendaveiculos.pessoa.Cliente;
import com.leninauto.vendaveiculos.pessoa.Vendedor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "TB_VENDAS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Venda implements IVenda, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente clienteComprador;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Vendedor vendedor;

    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculoVendido;


    @OneToOne(mappedBy = "vendaAssociada", cascade = CascadeType.ALL)
    @JsonIgnore
    private NotaFiscal notaFiscal;


    private LocalDate dataVenda;
    private double valorFinal;
    private String formaPagamento;

    public Venda() {
    }

 

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @Override
    public Cliente getClienteComprador() { return clienteComprador; }

    @Override
    public void setClienteComprador(Cliente clienteComprador) {
        this.clienteComprador = clienteComprador; // ATRIBUIÇÃO CORRETA
    }

    @Override
    public Vendedor getVendedor() { return vendedor; }

    @Override
    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor; // ATRIBUIÇÃO CORRETA
    }

    @Override
    public Veiculo getVeiculoVendido() { return veiculoVendido; }

    @Override
    public void setVeiculoVendido(Veiculo veiculoVendido) {
        this.veiculoVendido = veiculoVendido; // ATRIBUIÇÃO CORRETA
    }

    @Override
    public LocalDate getDataVenda() { return dataVenda; }
    @Override
    public void setDataVenda(LocalDate dataVenda) { this.dataVenda = dataVenda; }

    @Override
    public double getValorFinal() { return valorFinal; }
    @Override
    public void setValorFinal(double valorFinal) { this.valorFinal = valorFinal; }

    @Override
    public String getFormaPagamento() { return formaPagamento; }
    @Override
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }

    public NotaFiscal getNotaFiscal() { return notaFiscal; }
    public void setNotaFiscal(NotaFiscal nf) {
        this.notaFiscal = nf;
        if (nf != null && !Objects.equals(nf.getVendaAssociada(), this)) {
            nf.setVendaAssociada(this);
        }
    }

    @Override
    public double calcularValorTotal() {
        return this.valorFinal;
    }
}