package com.leninauto.vendaveiculos.services;

import com.leninauto.vendaveiculos.Venda.Venda;
import com.leninauto.vendaveiculos.dtos.VendaRequestDto;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

public interface VendaService {

    @Transactional
    Venda registrarVenda(VendaRequestDto vendaDto);

    Venda registrarVenda(Venda novaVenda);


    void gerarNotaFiscal(Venda venda, double impostoPercentual);


    byte[] gerarPdfNotaFiscal(Long vendaId) throws IOException;

    List<Venda> findAll();


    Venda atualizarVenda(Long id, Venda vendaAtualizada);

    void deletarVenda(Long id);
}