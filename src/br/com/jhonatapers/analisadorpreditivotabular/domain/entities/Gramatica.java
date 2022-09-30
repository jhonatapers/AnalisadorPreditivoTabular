package br.com.jhonatapers.analisadorpreditivotabular.domain.entities;

import java.util.List;

public class Gramatica {

    private List<SimboloGerador> _simbolosGeradores;

    public Gramatica(List<SimboloGerador> simbolosGeradores) {
        _simbolosGeradores = simbolosGeradores;
    }

    public SimboloGerador getInicial() {
        return _simbolosGeradores.get(0);
    }

    public List<SimboloGerador> getSimbolosGeradores() {
        return _simbolosGeradores;
    }

}
