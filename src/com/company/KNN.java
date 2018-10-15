package com.company;

import java.io.*;
import java.util.*;
import java.lang.Math;
import java.util.stream.IntStream;


public class KNN {
    private int label0 = 0, label1 = 0, label2 = 0;

    public static void main(String[] args) {
        KNN knn = new KNN();

        Flower[] flowers = knn.readFlowers("treinamento.csv");

        Flower[] flowersTest = knn.readFlowers("teste.csv");

        for(int i = 0; i < flowersTest.length; i++) {
            System.out.println(knn.classify(flowersTest[i], flowers, 1));
            knn.label0 = 0;
            knn.label1 = 0;
            knn.label2 = 0;
        }
    }

    private Integer classify(Flower flower, Flower[] flowers, int k) {
        Distance[] dists = new Distance[flowers.length];
        IntStream.range(0, flowers.length)
                .forEach(index -> dists[index] = getDistance(flower, flowers[index]));

        Arrays.sort(dists);
        IntStream.range(0, k)
                .forEach(index -> {
                    if(dists[index].getLabel() == 0) {
                        label0++;
                    } else if(dists[index].getLabel() == 1) {
                        label1++;
                    } else if(dists[index].getLabel() == 2) {
                        label2++;
                    }
                });

        return getLabel(label0, label1, label2);
    }

    private Integer getLabel(int label0, int label1, int label2) {
        if(label0 > label1 && label0 > label2) {
            return 0;
        } else if(label1 > label0 && label1 > label2) {
            return 1;
        } else {
            return 2;
        }
    }

    private Flower[] readFlowers(String filename) {
        List<Flower> flowers = new LinkedList<>();
        String line = "";
        String[] lineArray = null;

        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();
            line = bufferedReader.readLine();
            while(line != null) {
                lineArray = line.split(",");
                flowers.add(createFlower(lineArray));
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Flower[] flowersToArray = new Flower[flowers.size()];
        return flowers.toArray(flowersToArray);
    }

    private Distance getDistance(Flower flowerOne, Flower flowerTwo) {
        double soma = 0;
        soma += Math.pow((flowerOne.getSepalLength() - flowerTwo.getSepalLength()), 2);
        soma += Math.pow((flowerOne.getSepalWidth() - flowerTwo.getSepalWidth()), 2);
        soma += Math.pow((flowerOne.getPetalLength() - flowerTwo.getPetalLength()) ,2);
        soma += Math.pow((flowerOne.getPetalWidth() - flowerTwo.getPetalWidth()), 2);

        double dist = Math.sqrt(soma);
        return new Distance(dist, flowerTwo.getLabel());
    }

    private Flower createFlower(String[] flowerAttrs) {
        final Integer SEPALLENGTH   = 0;
        final Integer SEPALWIDTH    = 1;
        final Integer PETALLENGTH   = 2;
        final Integer PETALWIDTH    = 3;
        final Integer LABEL         = 4;

        Flower flower = null;

        if(flowerAttrs.length == 5) {
            flower = new Flower(
                    Double.parseDouble(flowerAttrs[SEPALLENGTH]),
                    Double.parseDouble(flowerAttrs[SEPALWIDTH]),
                    Double.parseDouble(flowerAttrs[PETALLENGTH]),
                    Double.parseDouble(flowerAttrs[PETALWIDTH]),
                    Integer.parseInt(flowerAttrs[LABEL])
            );
        } else {
            flower = new Flower(
                    Double.parseDouble(flowerAttrs[SEPALLENGTH]),
                    Double.parseDouble(flowerAttrs[SEPALWIDTH]),
                    Double.parseDouble(flowerAttrs[PETALLENGTH]),
                    Double.parseDouble(flowerAttrs[PETALWIDTH])
            );
        }

        return flower;
    }
}
