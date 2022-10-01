package br.com.jhonatapers.analisadorpreditivotabular.domain.entities;

import java.util.regex.Pattern;

import br.com.jhonatapers.analisadorpreditivotabular.App;

public class Simbolo {

    private String _simbolo;
    
    private boolean _palavraVazia;

    private boolean _terminal;

    public Simbolo(String simbolo) {
        _simbolo = simbolo;

        if(!Pattern.matches("(?![" + App.PALAVRA_VAZIA + "])[A-Z]", simbolo))
            _terminal = true;

        if(Pattern.matches( App.PALAVRA_VAZIA, simbolo))
            _palavraVazia = true;
    }
    
    public String getSimbolo() {
        return _simbolo;
    }

    public boolean isTerminal() {
        return _terminal;
    }

    public boolean isPalavraVazia() {
        return _palavraVazia;
    }

}
