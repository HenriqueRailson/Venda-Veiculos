package com.leninauto.vendaveiculos.controllers;

import com.leninauto.vendaveiculos.Venda.Venda;
import com.leninauto.vendaveiculos.dtos.VendaRequestDto;
import com.leninauto.vendaveiculos.services.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vendas")
@CrossOrigin(origins = "http://localhost:5173")
public class VendaController {

    @Autowired
    VendaService vendaService;


    @PostMapping
    public ResponseEntity<?> saveVenda(@RequestBody VendaRequestDto vendaDto) {
        try {
            Venda savedVenda = vendaService.registrarVenda(vendaDto);


            return ResponseEntity.status(HttpStatus.CREATED).body(savedVenda);

        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of("erro", "Erro de Negócio", "detalhes", e.getMessage())
            );
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("erro", "Erro Interno no Servidor", "detalhes", e.getMessage())
            );
        }
    }

    @GetMapping
    public ResponseEntity<List<Venda>> getAllVendas() {
        return ResponseEntity.status(HttpStatus.OK).body(vendaService.findAll());
    }


    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadNotaFiscal(@PathVariable Long id) {
        try {
            byte[] pdfBytes = vendaService.gerarPdfNotaFiscal(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String filename = "NotaFiscal_Venda_" + id + ".pdf";
            headers.setContentDispositionFormData(filename, filename);
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}