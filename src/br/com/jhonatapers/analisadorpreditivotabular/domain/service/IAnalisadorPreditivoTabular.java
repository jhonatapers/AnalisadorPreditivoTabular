package br.com.jhonatapers.analisadorpreditivotabular.domain.service;

import java.util.Queue;

import br.com.jhonatapers.analisadorpreditivotabular.domain.entities.Gramatica;
import br.com.jhonatapers.analisadorpreditivotabular.domain.entities.PassoReconhecimento;

public interface IAnalisadorPreditivoTabular {

    public Gramatica firstsAndFollows(Gramatica gramatica);

    public Gramatica tabela(Gramatica gramatica);

    public Queue<PassoReconhecimento> reconhecimento(Gramatica gramatica, String entrada);

}
