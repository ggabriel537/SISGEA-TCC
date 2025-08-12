package com.sisgea.sisgea;

import java.util.Random;

public class Uteis {
    //Necessario para gerar uma matricula aleatoria para o teste nao repetir a mesma matricula e dar erro
    public static String gerarMatriculaAleatoria() {
        String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random RANDOM = new Random();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 2; i++) {
            sb.append(LETTERS.charAt(RANDOM.nextInt(LETTERS.length())));
        }
        sb.append('-');
        for (int i = 0; i < 3; i++) {
            sb.append(LETTERS.charAt(RANDOM.nextInt(LETTERS.length())));
        }
        return sb.toString();
    }
}
