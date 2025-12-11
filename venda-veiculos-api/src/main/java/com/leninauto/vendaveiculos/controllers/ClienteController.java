package com.leninauto.vendaveiculos.controllers;

import com.leninauto.vendaveiculos.pessoa.Cliente;
import com.leninauto.vendaveiculos.repositories.ClienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:5173")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;


    @PostMapping
    public ResponseEntity<Cliente> saveCliente(@RequestBody Cliente cliente) {
        Cliente savedCliente = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCliente);
    }


    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(clientes);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCliente(@PathVariable(value="id") Long id) {
        Optional<Cliente> clienteOp = clienteRepository.findById(id);

        if (clienteOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(clienteOp.get());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCliente(@PathVariable(value="id") Long id,
                                                @RequestBody Cliente clienteDetalhes) {

        Optional<Cliente> clienteOp = clienteRepository.findById(id);

        if (clienteOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado para atualização.");
        }


        Cliente clienteExistente = clienteOp.get();


        BeanUtils.copyProperties(clienteDetalhes, clienteExistente, "id");


        return ResponseEntity.status(HttpStatus.OK).body(clienteRepository.save(clienteExistente));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCliente(@PathVariable(value="id") Long id) {

        Optional<Cliente> clienteOp = clienteRepository.findById(id);

        if (clienteOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado para exclusão.");
        }


        clienteRepository.delete(clienteOp.get());


        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente excluído com sucesso.");
    }
}