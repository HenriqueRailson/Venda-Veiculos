package com.leninauto.vendaveiculos.Venda;
import com.leninauto.vendaveiculos.automoveis.Veiculo; // Importa a classe base Veiculo
import java.util.ArrayList;
import java.util.List;

public class Estoque {


    private final List<Veiculo> listaVeiculos = new ArrayList<>();


    public Estoque() {

    }


    public void adicionarVeiculo(Veiculo veiculo) {
        if (veiculo != null) {
            this.listaVeiculos.add(veiculo);
            System.out.println("Veículo adicionado ao estoque: " + veiculo.getMarca() + " " + veiculo.getModelo());
        }
    }


    public boolean removerVeiculo(Veiculo veiculo) {
        return this.listaVeiculos.remove(veiculo);
    }


    public List<Veiculo> getListaVeiculos() {
        return listaVeiculos;
    }
}