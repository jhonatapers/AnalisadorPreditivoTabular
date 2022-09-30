package br.com.jhonatapers.analisadorpreditivotabular.domain.service;

import br.com.jhonatapers.analisadorpreditivotabular.domain.entities.Gramatica;

public interface ILeitorGramatica {
    
    public Gramatica read(String path);
    
}
