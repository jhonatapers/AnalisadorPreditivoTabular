package br.com.jhonatapers.analisadorpreditivotabular.domain.entities;

import java.util.List;

public class Producao {

    private List<Simbolo> _simbolos;

    public Producao(List<Simbolo> simbolos) {
        _simbolos = simbolos;
    }

    public List<Simbolo> getSimbolos() {
        return _simbolos;
    }

}
