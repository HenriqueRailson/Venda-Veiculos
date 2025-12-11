package com.leninauto.vendaveiculos.Venda;

import com.leninauto.vendaveiculos.pessoa.Cliente;
import com.leninauto.vendaveiculos.pessoa.Vendedor;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "TB_NOTAS_FISCAIS")
public class NotaFiscal implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "venda_id", nullable = false)
    private Venda vendaAssociada;


    @Column(nullable = false)
    private long numero;

    @Column(nullable = false)
    private LocalDate dataEmissao;

    @Column(nullable = false)
    private double valorTotal;

    @Column(nullable = false)
    private double valorImposto;


    @Transient
    private Cliente cliente;
    @Transient
    private Vendedor vendedor;


    public NotaFiscal() {
    }

    public NotaFiscal(Venda venda, double impostoPercentual) {

        this.numero = System.currentTimeMillis();
        this.dataEmissao = LocalDate.now();
        this.vendaAssociada = venda;


        this.valorTotal = venda.getValorFinal();
        this.valorImposto = this.valorTotal * impostoPercentual;
        this.valorTotal += this.valorImposto; // Soma o imposto ao total


        this.cliente = venda.getClienteComprador();
        this.vendedor = venda.getVendedor();
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public long getNumero() { return numero; }
    public void setNumero(long numero) { this.numero = numero; }

    public LocalDate getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(LocalDate dataEmissao) { this.dataEmissao = dataEmissao; }

    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public double getValorImposto() { return valorImposto; }
    public void setValorImposto(double valorImposto) { this.valorImposto = valorImposto; }

    public Venda getVendaAssociada() { return vendaAssociada; }
    public void setVendaAssociada(Venda vendaAssociada) { this.vendaAssociada = vendaAssociada; }


    public Cliente getCliente() { return cliente; }
    public Vendedor getVendedor() { return vendedor; }

}