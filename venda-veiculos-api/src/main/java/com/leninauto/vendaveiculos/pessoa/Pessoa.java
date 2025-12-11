package com.leninauto.vendaveiculos.pessoa;

import jakarta.persistence.Column; // NOVO: Importar Column
import jakarta.persistence.MappedSuperclass;
import java.io.Serial;
import java.io.Serializable;

@MappedSuperclass
public abstract class Pessoa implements IPessoa, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 14, unique = true) // CPF deve ser único
    private String cpf;

    @Column(length = 20)
    private String telefone;

    @Column(length = 100)
    private String email;

    @Column(length = 255)
    private String endereco;

    public Pessoa(String nome, String cpf, String telefone, String email, String endereco){
        this.nome=nome;
        this.cpf=cpf;
        this.telefone=telefone;
        this.email=email;
        this.endereco=endereco;
    }
    public Pessoa(){

    }



    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getCpf() {
        return cpf;
    }

    @Override
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String getTelefone() {
        return telefone;
    }

    @Override
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getEndereco() {
        return endereco;
    }

    @Override
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}