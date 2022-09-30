package br.com.jhonatapers.analisadorpreditivotabular.domain.service.impl;

import java.util.LinkedList;
import java.util.List;

import br.com.jhonatapers.analisadorpreditivotabular.domain.entities.Gramatica;
import br.com.jhonatapers.analisadorpreditivotabular.domain.entities.Simbolo;
import br.com.jhonatapers.analisadorpreditivotabular.domain.entities.SimboloGerador;
import br.com.jhonatapers.analisadorpreditivotabular.domain.service.IAnalisadorPreditivoTabular;

public class AnalisadorPreditivoTabular implements IAnalisadorPreditivoTabular {

    @Override
    public Gramatica firstsAndFollows(Gramatica gramatica) {

        for (SimboloGerador simboloGerador : gramatica.getSimbolosGeradores()) {
            simboloGerador.setFirsts(firts(simboloGerador.getSimboloGerador(), gramatica));
        }

        for (SimboloGerador simboloGerador : gramatica.getSimbolosGeradores()) {
            simboloGerador.setFollow(follows(simboloGerador.getSimboloGerador(), gramatica));
        }

        return gramatica;
    }

    private List<Simbolo> firts(String simboloGerador, Gramatica gramatica) {

        List<Simbolo> firts = new LinkedList<Simbolo>();

        gramatica.getSimbolosGeradores()
                .stream()
                .filter(s -> s.getSimboloGerador().equals(simboloGerador))
                .forEach(s -> {
                    s.getProducoes()
                            .forEach(p -> {
                                Simbolo simboloFirst = p.getSimbolos().get(0);

                                if (simboloFirst.isTerminal())
                                    firts.add(simboloFirst);
                                else
                                    firts(simboloFirst.getSimbolo(), gramatica)
                                            .forEach(f -> {
                                                firts.add(f);
                                            });
                                ;
                            });
                });

        return firts;
    }

    private List<Simbolo> follows(String simboloGerador, Gramatica gramatica) {
        List<Simbolo> follows = new LinkedList<Simbolo>();

        


        return follows;
    }

}
