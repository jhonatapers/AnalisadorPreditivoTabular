package br.com.jhonatapers.analisadorpreditivotabular;

import java.io.File;
import java.util.Queue;

import br.com.jhonatapers.analisadorpreditivotabular.domain.entities.Gramatica;
import br.com.jhonatapers.analisadorpreditivotabular.domain.entities.PassoReconhecimento;
import br.com.jhonatapers.analisadorpreditivotabular.domain.service.IAnalisadorPreditivoTabular;
import br.com.jhonatapers.analisadorpreditivotabular.domain.service.ILeitorGramatica;
import br.com.jhonatapers.analisadorpreditivotabular.domain.service.impl.AnalisadorPreditivoTabular;
import br.com.jhonatapers.analisadorpreditivotabular.domain.service.impl.LeitorGramatica;

public class App {

    private static final String LOCAL_PATH = "/src/br/com/jhonatapers/analisadorpreditivotabular/resources";

    public static final String PALAVRA_VAZIA = "E";

    public static final String ENTRADA = "i + i * i";

    public static void main(String[] args) throws Exception {

        ILeitorGramatica leitor = new LeitorGramatica();
        IAnalisadorPreditivoTabular analisador = new AnalisadorPreditivoTabular();
        Gramatica gramatica = leitor.read(new File(".").getCanonicalPath() + LOCAL_PATH + "/gramatica");

        analisador.firstsAndFollows(gramatica);
        analisador.tabela(gramatica);
        //Parte 1 e 2
        System.out.println(gramatica);

        System.out.println("\r\n");
        System.out.println("RECONHECIMENTO -> " + ENTRADA);

        //Parte 3
        Queue<PassoReconhecimento> passosReconhecimento = analisador.reconhecimento(gramatica, ENTRADA);

        for (PassoReconhecimento passoReconhecimento : passosReconhecimento) {
            System.out.println(passoReconhecimento.toString() + "\r\n");
        }


    }
}
