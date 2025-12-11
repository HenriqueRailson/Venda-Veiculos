package com.leninauto.vendaveiculos.tests;

import com.leninauto.vendaveiculos.Venda.Venda;
import com.leninauto.vendaveiculos.automoveis.Carro;
import com.leninauto.vendaveiculos.automoveis.StatusVeiculo;
import com.leninauto.vendaveiculos.automoveis.Veiculo;
import com.leninauto.vendaveiculos.pessoa.Cliente;
import com.leninauto.vendaveiculos.pessoa.Vendedor;
import com.leninauto.vendaveiculos.repositories.ClienteRepository;
import com.leninauto.vendaveiculos.repositories.VeiculoRepository;
import com.leninauto.vendaveiculos.repositories.VendedorRepository;
import com.leninauto.vendaveiculos.services.VendaService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// Anotação principal para carregar o contexto completo do Spring Boot para testes
@SpringBootTest
// Garante que o teste não persista dados permanentemente no banco
@Transactional
public class VendaServiceIntegrationTest {

    @Autowired
    private VendaService vendaService;

    // Repositórios necessários para configurar o estado inicial (setup)
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private VendedorRepository vendedorRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;

    @Test
    void registrarVenda_DeveAtualizarStatusDoVeiculoParaVendido() {
        // --- 1. SETUP: Criar e salvar entidades no banco de dados ---

        // Cliente
        Cliente cliente = new Cliente("Teste Cliente", "11122233344", "12345", "a@a.com", "Rua A");
        cliente = clienteRepository.save(cliente);

        // Vendedor
        Vendedor vendedor = new Vendedor("Teste Vendedor", "99988877766", "12345", "v@v.com", "Rua B", "V001", 10.0, "2024-01-01");
        vendedor = vendedorRepository.save(vendedor);

        // Veículo (Carro) - IMPORTANTE: status 'Disponivel'
        Veiculo veiculo = new Carro("Modelo Teste", "Marca Teste", "Azul", 2024, 12345, 50000.0, 4, "SUV");
        veiculo.setStatusVeiculo(StatusVeiculo.Disponivel); // Configurar status
        veiculo = veiculoRepository.save(veiculo);

        // --- 2. AÇÃO: Simular a venda ---

        Venda novaVenda = new Venda();
        // Usamos os objetos persistidos (com IDs) para ligar a nova Venda
        novaVenda.setClienteComprador(cliente);
        novaVenda.setVendedor(vendedor);
        novaVenda.setVeiculoVendido(veiculo);
        novaVenda.setValorFinal(55000.00);
        novaVenda.setFormaPagamento("Cartao");
        novaVenda.setDataVenda(LocalDate.now());

        // Chamada direta ao serviço (ignora Controller e Frontend)
        Venda vendaRegistrada = vendaService.registrarVenda(novaVenda);

        // --- 3. VERIFICAÇÃO: Checar o resultado ---

        // 3a. Checar se a venda foi salva
        assertNotNull(vendaRegistrada.getId(), "A Venda deve ter sido registrada e ter um ID.");

        // 3b. Checar se o status do veículo foi atualizado (Baixa de estoque)
        Veiculo veiculoAtualizado = veiculoRepository.findById(veiculo.getId()).orElseThrow();
        assertEquals(StatusVeiculo.Vendido_Aguardando_retirada, veiculoAtualizado.getStatusVeiculo(),
                "O status do veículo DEVE ser atualizado para 'Vendido_Aguardando_retirada'.");
    }

    @Test
    void registrarVenda_DeveLancarExcecaoSeVeiculoNaoEstiverDisponivel() {
        // --- 1. SETUP: Criar e salvar veículo indisponível ---
        Cliente cliente = clienteRepository.save(new Cliente("C", "11", "1", "a@a.com", "Rua"));
        Vendedor vendedor = vendedorRepository.save(new Vendedor("V", "22", "2", "v@v.com", "Rua", "V002", 5.0, "2024-01-01"));

        // Veículo com status que BLOQUEIA a venda
        Veiculo veiculoIndisponivel = new Carro("Bloqueado", "Marca X", "Vermelho", 2023, 54321, 10000.0, 2, "Sedan");
        veiculoIndisponivel.setStatusVeiculo(StatusVeiculo.Em_Preparacao); // Status incorreto
        veiculoIndisponivel = veiculoRepository.save(veiculoIndisponivel);

        Venda novaVenda = new Venda();
        novaVenda.setClienteComprador(cliente);
        novaVenda.setVendedor(vendedor);
        novaVenda.setVeiculoVendido(veiculoIndisponivel);
        novaVenda.setValorFinal(11000.00);
        novaVenda.setFormaPagamento("Cartao");
        novaVenda.setDataVenda(LocalDate.now());

        // --- 2. VERIFICAÇÃO: Checar se o serviço lança a exceção ---

        // AssertThrows verifica se uma exceção é lançada durante a execução
        assertThrows(RuntimeException.class, () -> {
            vendaService.registrarVenda(novaVenda);
        }, "O serviço DEVE lançar uma exceção se o veículo não estiver Disponível.");
    }

}