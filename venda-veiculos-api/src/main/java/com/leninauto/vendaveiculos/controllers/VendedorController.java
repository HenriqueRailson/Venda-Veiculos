package com.leninauto.vendaveiculos.controllers;

import com.leninauto.vendaveiculos.pessoa.Vendedor;
import com.leninauto.vendaveiculos.repositories.VendedorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vendedores")
@CrossOrigin(origins = "http://localhost:5173")
public class VendedorController {

    @Autowired
    VendedorRepository vendedorRepository;


    @PostMapping
    public ResponseEntity<Vendedor> saveVendedor(@RequestBody Vendedor vendedor) {
        Vendedor savedVendedor = vendedorRepository.save(vendedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVendedor);
    }


    @GetMapping
    public ResponseEntity<List<Vendedor>> getAllVendedores() {
        List<Vendedor> vendedores = vendedorRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(vendedores);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneVendedor(@PathVariable(value="id") Long id) {
        Optional<Vendedor> vendedorOp = vendedorRepository.findById(id);

        if (vendedorOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendedor não encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(vendedorOp.get());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateVendedor(@PathVariable(value="id") Long id,
                                                 @RequestBody Vendedor vendedorDetalhes) {
        Optional<Vendedor> vendedorOp = vendedorRepository.findById(id);

        if (vendedorOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendedor não encontrado para atualização.");
        }


        Vendedor vendedorExistente = vendedorOp.get();

        BeanUtils.copyProperties(vendedorDetalhes, vendedorExistente, "id");

        // 3. Salvar (UPDATE)
        return ResponseEntity.status(HttpStatus.OK).body(vendedorRepository.save(vendedorExistente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVendedor(@PathVariable(value="id") Long id) {

        Optional<Vendedor> vendedorOp = vendedorRepository.findById(id);

        if (vendedorOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendedor não encontrado para exclusão.");
        }


        vendedorRepository.delete(vendedorOp.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Vendedor excluído com sucesso.");
    }
}