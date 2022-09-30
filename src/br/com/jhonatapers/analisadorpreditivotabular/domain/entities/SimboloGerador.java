package br.com.jhonatapers.analisadorpreditivotabular.domain.entities;

import java.util.LinkedList;
import java.util.List;

public class SimboloGerador {

    private String _simboloGerador;

    private List<Producao> _producoes;

    private List<Simbolo> _firsts;

    private List<Simbolo> _follows;

    public SimboloGerador(String simboloGerador, List<Producao> producoes) {
        _simboloGerador = simboloGerador;
        _producoes = producoes;

        _firsts = new LinkedList<Simbolo>();
        _follows = new LinkedList<Simbolo>();
    }

    public String getSimboloGerador() {
        return _simboloGerador;
    }

    public List<Producao> getProducoes() {
        return _producoes;
    }

    public List<Simbolo> getFirsts() {
        return _firsts;
    }

    public void setFirsts(List<Simbolo> _firsts) {
        this._firsts = _firsts;
    }

    public List<Simbolo> getFollows() {
        return _follows;
    }

    public void setFollow(List<Simbolo> _follows) {
        this._follows = _follows;
    }
    
}
