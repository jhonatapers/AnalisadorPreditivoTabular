package br.com.jhonatapers.analisadorpreditivotabular.domain.entities;

import java.util.List;

public class PassoReconhecimento {
    
    private List<Simbolo> pilha;

    private List<Simbolo> entrada;

    private Producao producaoAplicada;

    private boolean passou;

    public PassoReconhecimento(List<Simbolo> pilha, List<Simbolo> entrada, Producao producaoAplicada, boolean passou) {
        this.pilha = pilha;
        this.entrada = entrada;
        this.producaoAplicada = producaoAplicada;
        this.passou = passou;
    }

    public List<Simbolo> getPilha() {
        return pilha;
    }

    public List<Simbolo> getEntrada() {
        return entrada;
    }

    public Producao getProducaoAplicada() {
        return producaoAplicada;
    }

    public boolean isPassou() {
        return passou;
    }

}
