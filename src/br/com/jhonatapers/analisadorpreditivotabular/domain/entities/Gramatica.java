package br.com.jhonatapers.analisadorpreditivotabular.domain.entities;

import java.util.LinkedList;
import java.util.List;

public class Gramatica {

    private List<SimboloGerador> _simbolosGeradores;

    public Gramatica(List<SimboloGerador> simbolosGeradores) {

        _simbolosGeradores = new LinkedList<SimboloGerador>();

        simbolosGeradores
                .stream()
                .filter(s -> {
                    return true;
                })
                .forEach(s -> {

                    List<Producao> producoes = new LinkedList<Producao>();

                    final SimboloGerador geradorAtual = s;

                    simbolosGeradores
                            .stream()
                            .filter(s2 -> {
                                return s2.getSimboloGerador().equals(geradorAtual.getSimboloGerador());
                            }).forEach(s2 -> {
                                s2.getProducoes()
                                        .stream()
                                        .filter(p -> {
                                            return producoes
                                                    .stream()
                                                    .filter(p2 -> {
                                                        return p2.equals(p);
                                                    })
                                                    .findFirst()
                                                    .isEmpty();
                                        }).forEach(p -> {
                                            producoes.add(p);
                                        });
                            });

                    if (!_simbolosGeradores.stream().filter(sg -> sg.getSimboloGerador().equals(s.getSimboloGerador()))
                            .findFirst().isPresent()) {
                        _simbolosGeradores.add(new SimboloGerador(s.getSimboloGerador(), s.isInicial(), producoes));
                    }

                });
    }

    public SimboloGerador getInicial() {
        return _simbolosGeradores.get(0);
    }

    public List<SimboloGerador> getSimbolosGeradores() {
        return _simbolosGeradores;
    }

}
