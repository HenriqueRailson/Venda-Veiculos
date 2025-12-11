package com.leninauto.vendaveiculos.services;

import com.leninauto.vendaveiculos.Venda.NotaFiscal;
import com.leninauto.vendaveiculos.Venda.Venda;
import com.leninauto.vendaveiculos.automoveis.StatusVeiculo;
import com.leninauto.vendaveiculos.automoveis.Veiculo;
import com.leninauto.vendaveiculos.dtos.VendaRequestDto;
import com.leninauto.vendaveiculos.pessoa.Cliente;
import com.leninauto.vendaveiculos.pessoa.Vendedor;
import com.leninauto.vendaveiculos.repositories.ClienteRepository;
import com.leninauto.vendaveiculos.repositories.VendaRepository;
import com.leninauto.vendaveiculos.repositories.VeiculoRepository;
import com.leninauto.vendaveiculos.repositories.VendedorRepository;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VendaServiceImpl implements VendaService {

    @Autowired private VendaRepository vendaRepository;
    @Autowired private VeiculoRepository veiculoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private VendedorRepository vendedorRepository;

    @Override
    @Transactional
    public Venda registrarVenda(VendaRequestDto vendaDto) {


        Long clienteId = vendaDto.getClienteId();
        Long vendedorId = vendaDto.getVendedorId();
        Long veiculoId = vendaDto.getVeiculoId();


        if (clienteId == null || clienteId <= 0) throw new RuntimeException("Erro: ID do Cliente inválido.");
        if (vendedorId == null || vendedorId <= 0) throw new RuntimeException("Erro: ID do Vendedor inválido.");
        if (veiculoId == null || veiculoId <= 0) throw new RuntimeException("Erro: ID do Veículo inválido.");


        Optional<Cliente> clienteOp = clienteRepository.findById(clienteId);
        Optional<Vendedor> vendedorOp = vendedorRepository.findById(vendedorId);
        Optional<Veiculo> veiculoOp = veiculoRepository.findById(veiculoId);

        if (clienteOp.isEmpty()) throw new RuntimeException("Cliente não encontrado com ID: " + clienteId);
        if (vendedorOp.isEmpty()) throw new RuntimeException("Vendedor não encontrado com ID: " + vendedorId);
        if (veiculoOp.isEmpty()) throw new RuntimeException("Veículo não encontrado com ID: " + veiculoId);

        Cliente cliente = clienteOp.get();
        Vendedor vendedor = vendedorOp.get();
        Veiculo veiculo = veiculoOp.get();


        if (veiculo.getStatusVeiculo() != StatusVeiculo.Disponivel) {
            throw new RuntimeException("O veículo " + veiculo.getModelo() + " não está disponível. Status: " + veiculo.getStatusVeiculo());
        }


        Venda novaVenda = new Venda();
        novaVenda.setValorFinal(vendaDto.getValorFinal());
        novaVenda.setFormaPagamento(vendaDto.getFormaPagamento());
        novaVenda.setDataVenda(vendaDto.getDataVenda() != null ? vendaDto.getDataVenda() : LocalDate.now());


        novaVenda.setClienteComprador(cliente);
        novaVenda.setVendedor(vendedor);
        novaVenda.setVeiculoVendido(veiculo);


        veiculo.setStatusVeiculo(StatusVeiculo.Vendido_Aguardando_retirada);
        veiculoRepository.save(veiculo);


        Venda vendaSalva = vendaRepository.save(novaVenda);
        gerarNotaFiscal(vendaSalva, 0.15);

        return vendaRepository.save(vendaSalva);
    }

    @Override
    public Venda registrarVenda(Venda novaVenda) {
        return null;
    }

    @Override
    public void gerarNotaFiscal(Venda venda, double impostoPercentual) {
        NotaFiscal nf = new NotaFiscal(venda, impostoPercentual);
        venda.setNotaFiscal(nf);
    }

    @Override
    public byte[] gerarPdfNotaFiscal(Long vendaId) throws IOException {
        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada com ID: " + vendaId));

        NotaFiscal nf = venda.getNotaFiscal();
        if (nf == null) {
            nf = new NotaFiscal(venda, 0.15);
        }

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);


            Cliente cliente = venda.getClienteComprador();
            Vendedor vendedor = venda.getVendedor();
            Veiculo veiculo = venda.getVeiculoVendido();

            String clienteNome = cliente != null ? cliente.getNome() : "Não Informado";
            String clienteCpf = cliente != null ? cliente.getCpf() : "N/A";
            String vendedorNome = vendedor != null ? vendedor.getNome() : "Não Informado";
            String vendedorCpf = vendedor != null ? vendedor.getCpf() : "N/A";
            String veiculoModelo = veiculo != null ? veiculo.getModelo() : "N/A";
            String veiculoMarca = veiculo != null ? veiculo.getMarca() : "N/A";
            String veiculoAno = veiculo != null ? String.valueOf(veiculo.getAnoFabricacao()) : "N/A";
            String veiculoChassi = veiculo != null ? veiculo.getChassi() : "N/A";


            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {


                contentStream.setFont(PDType1Font.HELVETICA, 12);
                float margin = 50;
                float yPosition = 750;
                float leading = 20;

                contentStream.beginText();
                contentStream.setLeading(leading);
                contentStream.newLineAtOffset(margin, yPosition);

                // --- TÍTULO ---
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.showText("NOTA FISCAL - Venda #" + venda.getId());
                contentStream.newLine();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLine();

                // --- DADOS GERAIS ---
                contentStream.showText("Data de Emissão: " + nf.getDataEmissao().toString());
                contentStream.newLine();
                contentStream.showText("Forma de Pagamento: " + venda.getFormaPagamento());
                contentStream.newLine();
                contentStream.newLine();

                // --- CLIENTE ---
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.showText("CLIENTE:");
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLine();
                contentStream.showText("Nome: " + clienteNome);
                contentStream.newLine();
                contentStream.showText("CPF: " + clienteCpf);
                contentStream.newLine();
                contentStream.newLine();

                // --- VEÍCULO ---
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.showText("VEÍCULO:");
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLine();
                contentStream.showText("Modelo: " + veiculoModelo);
                contentStream.newLine();
                contentStream.showText("Marca: " + veiculoMarca);
                contentStream.newLine();
                contentStream.showText("Ano: " + veiculoAno);
                contentStream.newLine();
                contentStream.showText("Chassi: " + veiculoChassi);
                contentStream.newLine();
                contentStream.newLine();

                // --- VENDEDOR ---
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.showText("VENDEDOR:");
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLine();
                contentStream.showText("Nome: " + vendedorNome);
                contentStream.newLine();
                contentStream.showText("CPF: " + vendedorCpf);
                contentStream.newLine();
                contentStream.newLine();

                // --- VALORES ---
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.showText("TOTAL:");
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLine();
                contentStream.showText("Valor do Veículo: R$ " + String.format("%.2f", venda.getValorFinal()));
                contentStream.newLine();
                contentStream.showText("Impostos (Est.): R$ " + String.format("%.2f", nf.getValorImposto()));
                contentStream.newLine();

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLine();
                contentStream.showText("TOTAL A PAGAR: R$ " + String.format("%.2f", nf.getValorTotal()));

                contentStream.endText();
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            return out.toByteArray();
        }
    }

    @Override
    public List<Venda> findAll() { return vendaRepository.findAll(); }

    @Override
    @Transactional
    public Venda atualizarVenda(Long id, Venda vendaDetalhes) {
        return vendaRepository.save(vendaDetalhes);
    }

    @Override
    @Transactional
    public void deletarVenda(Long id) {
        Venda venda = vendaRepository.findById(id).orElseThrow();
        if(venda.getVeiculoVendido() != null) {
            venda.getVeiculoVendido().setStatusVeiculo(StatusVeiculo.Disponivel);
            veiculoRepository.save(venda.getVeiculoVendido());
        }
        vendaRepository.delete(venda);
    }
}