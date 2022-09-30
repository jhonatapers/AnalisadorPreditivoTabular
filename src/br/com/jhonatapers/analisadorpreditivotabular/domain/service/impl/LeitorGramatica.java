package br.com.jhonatapers.analisadorpreditivotabular.domain.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

import br.com.jhonatapers.analisadorpreditivotabular.domain.entities.Gramatica;
import br.com.jhonatapers.analisadorpreditivotabular.domain.entities.Producao;
import br.com.jhonatapers.analisadorpreditivotabular.domain.entities.Simbolo;
import br.com.jhonatapers.analisadorpreditivotabular.domain.entities.SimboloGerador;
import br.com.jhonatapers.analisadorpreditivotabular.domain.service.ILeitorGramatica;

public class LeitorGramatica implements ILeitorGramatica {

    @Override
    public Gramatica read(String path) {
        try (BufferedReader buffRead = new BufferedReader(new FileReader(path))) {

            
            //SimboloGerador simboloGeradorInicial = DeserializeLine(line);

            List<SimboloGerador> simbolosGeradores = new LinkedList<SimboloGerador>();

            String line = buffRead.readLine();
            while (true) {
                if (line == null)
                    break;
                else
                    simbolosGeradores.add(DeserializeLine(line));

                line = buffRead.readLine();
            }

            buffRead.close();

            return new Gramatica(simbolosGeradores);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private SimboloGerador DeserializeLine(String fileLine) {
        String[] geradorXProducoes = fileLine.replace(" ", "").split("->");

        String simboloGerador = geradorXProducoes[0];

        List<Producao> producoes = new LinkedList<Producao>();
        for (String producao : geradorXProducoes[1].split("\\|")) {
            List<Simbolo> simbolosProducao = new LinkedList<>();

            for (String simboloProducao : producao.split("|")) 
                simbolosProducao.add(new Simbolo(simboloProducao));
        
            producoes.add(new Producao(simbolosProducao));
        }
        

        return new SimboloGerador(simboloGerador, producoes);
    }

}
