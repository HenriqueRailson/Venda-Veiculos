package com.leninauto.vendaveiculos.controllers;

import com.leninauto.vendaveiculos.automoveis.Veiculo;
import com.leninauto.vendaveiculos.repositories.VeiculoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/veiculos")
@CrossOrigin(origins = "http://localhost:5173")
public class VeiculoController {

    @Autowired
    VeiculoRepository veiculoRepository;


    @PostMapping
    public ResponseEntity<Veiculo> saveVeiculo(@RequestBody Veiculo veiculo) {
        Veiculo savedVeiculo = veiculoRepository.save(veiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVeiculo);
    }


    @GetMapping
    public ResponseEntity<List<Veiculo>> getAllVeiculos() {
        List<Veiculo> veiculos = veiculoRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(veiculos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneVeiculo(@PathVariable(value="id") Long id) {
        Optional<Veiculo> veiculoOp = veiculoRepository.findById(id);

        if (veiculoOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veículo não encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(veiculoOp.get());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateVeiculo(@PathVariable(value="id") Long id,
                                                @RequestBody Veiculo veiculoDetalhes) {

        Optional<Veiculo> veiculoOp = veiculoRepository.findById(id);

        if (veiculoOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veículo não encontrado para atualização.");
        }


        Veiculo veiculoExistente = veiculoOp.get();


        BeanUtils.copyProperties(veiculoDetalhes, veiculoExistente, "id", "@class");


        return ResponseEntity.status(HttpStatus.OK).body(veiculoRepository.save(veiculoExistente));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVeiculo(@PathVariable(value="id") Long id) {

        Optional<Veiculo> veiculoOp = veiculoRepository.findById(id);

        if (veiculoOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veículo não encontrado para exclusão.");
        }


        veiculoRepository.delete(veiculoOp.get());


        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Veículo excluído com sucesso.");
    }
}