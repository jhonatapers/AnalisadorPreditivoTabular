package br.com.jhonatapers.analisadorpreditivotabular.domain.entities;

public class Relacao {
    
    private Producao producao;

    private Simbolo simbolo;

    public Relacao(Producao producao, Simbolo simbolo) {
        this.producao = producao;
        this.simbolo = simbolo;
    }

    public Producao getProducao() {
        return producao;
    }

    public Simbolo getSimbolo() {
        return simbolo;
    }

}
