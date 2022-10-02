package br.com.jhonatapers.analisadorpreditivotabular.domain.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import br.com.jhonatapers.analisadorpreditivotabular.domain.entities.Gramatica;
import br.com.jhonatapers.analisadorpreditivotabular.domain.entities.Producao;
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

    public Gramatica tabela(Gramatica gramatica) {

        List<HashMap<Simbolo, Producao>> tabela = new LinkedList<HashMap<Simbolo, Producao>>();

        gramatica.getSimbolosGeradores()
                .forEach(gerador -> {

                    HashMap<Simbolo, Producao> linhaTabela = new HashMap<Simbolo, Producao>();

                    gerador.getProducoes()
                            .forEach(producao -> {

                                Simbolo firstSimboloProducao = producao.getSimbolos().get(0);
                                if (firstSimboloProducao.isPalavraVazia()) {

                                    follows(gerador.getSimboloGerador(), gramatica)
                                            .forEach(follow -> {
                                                linhaTabela.put(firstSimboloProducao, producao);
                                            });

                                } else if (firstSimboloProducao.isTerminal()) {

                                    linhaTabela.put(firstSimboloProducao, producao);
                                    tabela.add(linhaTabela);

                                } else {

                                    firts(gerador.getSimboloGerador(), gramatica)
                                            .stream()
                                            .filter(first -> !first.isPalavraVazia())
                                            .forEach(first -> {
                                                linhaTabela.put(firstSimboloProducao, producao);
                                            });

                                }

                            });

                    gerador.setLinhaTabela(linhaTabela);
                });

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

                                if (simboloFirst.isTerminal()) {
                                    firts.add(simboloFirst);
                                } else {
                                    firts(simboloFirst.getSimbolo(), gramatica)
                                            .forEach(f -> {
                                                firts.add(f);
                                            });
                                }

                                ;
                            });
                });

        return firts;
    }

    private List<Simbolo> follows(String simboloGerador, Gramatica gramatica) {

        List<Simbolo> follows = new LinkedList<Simbolo>();

        gramatica.getSimbolosGeradores()
                .stream()
                .filter(gerador -> {
                    return gerador.getProducoes()
                            .stream()
                            .filter(producao -> {
                                return producao.getSimbolos()
                                        .stream()
                                        .filter(simboloProducao -> {
                                            return simboloProducao.getSimbolo().equals(simboloGerador);
                                        }).findFirst().isPresent();
                            }).findFirst().isPresent();
                }).forEach(gerador -> {

                    gerador.getProducoes()
                            .stream()
                            .filter(producao -> {
                                return producao.getSimbolos()
                                        .stream()
                                        .filter(simboloProducao -> {
                                            return simboloProducao.getSimbolo().equals(simboloGerador);
                                        }).findFirst().isPresent();
                            }).forEach(producao -> {

                                for (int i = 0; i < producao.getSimbolos().size(); i++) {

                                    Simbolo simboloAtual = producao.getSimbolos().get(i);

                                    if (!simboloAtual.getSimbolo().equals(simboloGerador) || simboloAtual.isTerminal())
                                        continue;

                                    if (gramatica.getInicial().getSimboloGerador().equals(simboloGerador)) {

                                        boolean contemSimboloPilha = follows
                                                .stream()
                                                .filter(follow -> {
                                                    return follow.getSimbolo().equals("$");
                                                })
                                                .findFirst()
                                                .isPresent();

                                        if (!contemSimboloPilha) {
                                            follows.add(new Simbolo("$"));
                                        }
                                    }

                                    if (i < producao.getSimbolos().size() - 1) {
                                        Simbolo proxiSimbolo = producao.getSimbolos().get(i + 1);

                                        if (proxiSimbolo.isTerminal()) {
                                            follows.add(proxiSimbolo);
                                            continue;
                                        }

                                        SimboloGerador geradorFollow = gramatica.getSimbolosGeradores()
                                                .stream()
                                                .filter(geradorFollowAux -> {
                                                    return geradorFollowAux.getSimboloGerador()
                                                            .equals(proxiSimbolo.getSimbolo());
                                                }).findFirst().get();

                                        geradorFollow.getFirsts()
                                                .stream()
                                                .filter(first -> {
                                                    return !first.isPalavraVazia() && !follows
                                                            .stream()
                                                            .filter(follow -> {
                                                                return follow.getSimbolo().equals(first.getSimbolo());
                                                            }).findFirst().isPresent();
                                                }).forEach(first -> {
                                                    follows.add(first);
                                                });

                                        boolean produzPalavraVazia = geradorFollow.getFirsts()
                                                .stream()
                                                .filter(f -> {
                                                    return f.isPalavraVazia();
                                                }).findFirst()
                                                .isPresent();

                                        if (produzPalavraVazia) {
                                            follows(geradorFollow.getSimboloGerador(), gramatica)
                                                    .stream()
                                                    .filter(follow -> {
                                                        return !follows
                                                                .stream()
                                                                .filter(f -> {
                                                                    return f.getSimbolo().equals(follow.getSimbolo());
                                                                })
                                                                .findAny()
                                                                .isPresent();
                                                    })
                                                    .forEach(follow -> {
                                                        follows.add(follow);
                                                    });
                                        }

                                    } else {

                                        if (gerador.getSimboloGerador().equals(simboloGerador))
                                            continue;

                                        follows(gerador.getSimboloGerador(), gramatica)
                                                .stream()
                                                .filter(follow -> {
                                                    return !follows
                                                            .stream()
                                                            .filter(f -> {
                                                                return f.getSimbolo().equals(follow.getSimbolo());
                                                            })
                                                            .findAny()
                                                            .isPresent();
                                                })
                                                .forEach(follow -> {
                                                    follows.add(follow);
                                                });

                                        continue;
                                    }

                                }
                            });
                });

        return follows;
    }

}
