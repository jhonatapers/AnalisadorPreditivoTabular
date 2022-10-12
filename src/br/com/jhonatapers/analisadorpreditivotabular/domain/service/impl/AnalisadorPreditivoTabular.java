package br.com.jhonatapers.analisadorpreditivotabular.domain.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import br.com.jhonatapers.analisadorpreditivotabular.domain.entities.Gramatica;
import br.com.jhonatapers.analisadorpreditivotabular.domain.entities.PassoReconhecimento;
import br.com.jhonatapers.analisadorpreditivotabular.domain.entities.Relacao;
import br.com.jhonatapers.analisadorpreditivotabular.domain.entities.Simbolo;
import br.com.jhonatapers.analisadorpreditivotabular.domain.entities.SimboloGerador;
import br.com.jhonatapers.analisadorpreditivotabular.domain.service.IAnalisadorPreditivoTabular;

public class AnalisadorPreditivoTabular implements IAnalisadorPreditivoTabular {

    @Override
    public Gramatica firstsAndFollows(Gramatica gramatica) {

        for (SimboloGerador simboloGerador : gramatica.getSimbolosGeradores()) {
            simboloGerador.setFirsts(firsts(simboloGerador.getSimboloGerador(), gramatica));
        }

        for (SimboloGerador simboloGerador : gramatica.getSimbolosGeradores()) {
            simboloGerador.setFollow(follows(simboloGerador.getSimboloGerador(), gramatica));
        }

        return gramatica;
    }

    public Gramatica tabela(Gramatica gramatica) {

        gramatica.getSimbolosGeradores()
                .forEach(gerador -> {

                    List<Relacao> linhaTabela = new LinkedList<Relacao>();

                    gerador.getProducoes()
                            .forEach(producao -> {

                                Simbolo firstSimboloProducao = producao.getSimbolos().get(0);
                                if (firstSimboloProducao.isPalavraVazia()) {

                                    follows(gerador.getSimboloGerador(), gramatica)
                                            .forEach(follow -> {
                                                linhaTabela.add(new Relacao(producao, firstSimboloProducao));
                                            });

                                } else if (firstSimboloProducao.isTerminal()) {

                                    linhaTabela.add(new Relacao(producao, firstSimboloProducao));

                                } else {

                                    firsts(gerador.getSimboloGerador(), gramatica)
                                            .stream()
                                            .filter(first -> !first.isPalavraVazia())
                                            .forEach(first -> {
                                                linhaTabela.add(new Relacao(producao, first));
                                            });

                                }

                            });

                    gerador.setLinhaTabela(linhaTabela);
                });

        return gramatica;
    }

    private List<Simbolo> firsts(String simboloGerador, Gramatica gramatica) {

        List<Simbolo> firsts = new LinkedList<Simbolo>();

        gramatica.getSimbolosGeradores()
                .stream()
                .filter(s -> s.getSimboloGerador().equals(simboloGerador))
                .forEach(s -> {
                    s.getProducoes()
                            .forEach(p -> {
                                Simbolo simboloFirst = p.getSimbolos().get(0);

                                if (simboloFirst.isTerminal()) {
                                    firsts.add(simboloFirst);
                                } else {
                                    firsts(simboloFirst.getSimbolo(), gramatica)
                                            .forEach(f -> {
                                                firsts.add(f);
                                            });
                                }

                                ;
                            });
                });

        return firsts;
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

    @Override
    public Queue<PassoReconhecimento> reconhecimento(Gramatica gramatica, String entrada) {

        Queue<PassoReconhecimento> passoReconhecimento = new LinkedList<PassoReconhecimento>();
        Stack<Simbolo> _entrada = leEntrada(entrada);

        Stack<Simbolo> pilha = new Stack<Simbolo>();

        pilha.add(new Simbolo("$"));
        pilha.add(new Simbolo(gramatica.getInicial().getSimboloGerador()));

        // Aqui printar estado
        passoReconhecimento.add(new PassoReconhecimento((Stack) pilha.clone(), (Stack) _entrada.clone(), null, true));

        while (!_entrada.empty()) {

            Simbolo simboloTopoPilha = pilha.pop();

            Boolean podeProsseguir = gramatica.getSimbolosGeradores()
                    .stream()
                    .filter(gerador -> {
                        return gerador.getSimboloGerador().equals(simboloTopoPilha.getSimbolo());
                    })
                    .findFirst()
                    .get()
                    .getLinhaTabela()
                    .stream()
                    .filter(relacao -> {
                        return relacao.getSimbolo().getSimbolo().equals(_entrada.peek().getSimbolo());
                    })
                    .findFirst()
                    .isPresent();

            if (!podeProsseguir) {
                passoReconhecimento
                        .add(new PassoReconhecimento((Stack) pilha.clone(), (Stack) _entrada.clone(), null, false));
                break;
            } else {

                if (simboloTopoPilha.getSimbolo().equals(_entrada.peek().getSimbolo())) {
                    
                    //deu match
                    

                }

            }

            System.out.println("aham");

        }

        return passoReconhecimento;
    }

    private Stack<Simbolo> leEntrada(String entrada) {
        Stack<Simbolo> pilhaEntrada = new Stack<Simbolo>();

        for (String simboloProducao : entrada.replace(" ", "").split("|"))
            pilhaEntrada.add(new Simbolo(simboloProducao));

        pilhaEntrada.add(new Simbolo("$"));

        return pilhaEntrada;
    }

}
