package br.com.jhonatapers.analisadorpreditivotabular.domain.entities;

import java.util.LinkedList;
import java.util.List;

public class SimboloGerador {

    private String _simboloGerador;

    private boolean _inicial;

    private List<Producao> _producoes;

    private List<Relacao> _linhaTabela;

    private List<Simbolo> _firsts;

    private List<Simbolo> _follows;

    public SimboloGerador(String simboloGerador, boolean inicial, List<Producao> producoes) {
        _simboloGerador = simboloGerador;
        _inicial = inicial;
        _producoes = producoes;

        _firsts = new LinkedList<Simbolo>();
        _follows = new LinkedList<Simbolo>();
    }

    public String getSimboloGerador() {
        return _simboloGerador;
    }
    
    public boolean isInicial() {
        return _inicial;
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
    
    public List<Relacao> getLinhaTabela() {
        return _linhaTabela;
    }

    public void setLinhaTabela(List<Relacao> _linhaTabela) {
        this._linhaTabela = _linhaTabela;
    }

}
