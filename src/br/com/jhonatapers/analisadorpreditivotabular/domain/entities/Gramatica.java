package br.com.jhonatapers.analisadorpreditivotabular.domain.entities;

import java.util.List;

public class Gramatica {

    private List<SimboloGerador> _simboloesGeradores;

    public Gramatica(List<SimboloGerador> simbolosGeradores) {
        _simboloesGeradores = simbolosGeradores;
    }

    public SimboloGerador getInicial() {
        return _simboloesGeradores.get(0);
    }

    public List<SimboloGerador> getSimboloesGeradores() {
        return _simboloesGeradores;
    }

}
