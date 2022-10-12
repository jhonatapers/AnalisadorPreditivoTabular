package br.com.jhonatapers.analisadorpreditivotabular.domain.entities;

import java.util.Stack;

public class PassoReconhecimento {

    private Stack<Simbolo> pilha;

    private Stack<Simbolo> entrada;

    private SimboloGerador geradorProducao;

    private Producao producaoAplicada;

    private boolean passou;

    public PassoReconhecimento(Stack<Simbolo> pilha, Stack<Simbolo> entrada, SimboloGerador geradorProducao,
            Producao producaoAplicada, boolean passou) {
        this.pilha = pilha;
        this.entrada = entrada;
        this.geradorProducao = geradorProducao;
        this.producaoAplicada = producaoAplicada;
        this.passou = passou;
    }

    public Stack<Simbolo> getPilha() {
        return pilha;
    }

    public Stack<Simbolo> getEntrada() {
        return entrada;
    }

    public Producao getProducaoAplicada() {
        return producaoAplicada;
    }

    public boolean isPassou() {
        return passou;
    }

    @Override
    public String toString() {

        String toString = "PILHA : ";
        for (Simbolo simbolo : pilha) {
            toString += simbolo.getSimbolo();
        }

        toString = "| ENTRADA : ";
        for (Simbolo simbolo : entrada) {
            toString += simbolo.getSimbolo();
        }

        toString += "| PRODUCAO :";

        if (geradorProducao != null) {

            toString += geradorProducao.getSimboloGerador() + " -> ";

            for (Simbolo simbolo : producaoAplicada.getSimbolos()) {
                toString += simbolo.getSimbolo();
            }
        }

        toString += "| PASSOU :";
        toString += passou ? "SIM" : "NAO";

        return toString;
    }

}
