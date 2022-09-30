package br.com.jhonatapers.analisadorpreditivotabular.domain.service;

import br.com.jhonatapers.analisadorpreditivotabular.domain.entities.Gramatica;

public interface IAnalisadorPreditivoTabular {
    
    public Gramatica firstsAndFollows(Gramatica gramatica);

}
