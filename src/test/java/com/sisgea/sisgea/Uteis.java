package com.sisgea.sisgea;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Uteis {
    private static final Set<String> CPFS_GERADOS = new HashSet<>();

    public static String gerarMatriculaAleatoria() {
        String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random RANDOM = new Random();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 2; i++) sb.append(LETTERS.charAt(RANDOM.nextInt(LETTERS.length())));
        sb.append('-');
        for (int i = 0; i < 3; i++) sb.append(LETTERS.charAt(RANDOM.nextInt(LETTERS.length())));
        return sb.toString();
    }

    public static String gerarCPFValido() {
        Random random = new Random();
        String cpf;
        do {
            int[] n = new int[9];
            for (int i = 0; i < 9; i++) n[i] = random.nextInt(10);

            int d1 = 0, d2 = 0;
            for (int i = 0, peso = 10; i < 9; i++, peso--) d1 += n[i] * peso;
            d1 = d1 % 11 < 2 ? 0 : 11 - (d1 % 11);

            for (int i = 0, peso = 11; i < 9; i++, peso--) d2 += n[i] * peso;
            d2 += d1 * 2;
            d2 = d2 % 11 < 2 ? 0 : 11 - (d2 % 11);

            StringBuilder sb = new StringBuilder();
            for (int i : n) sb.append(i);
            sb.append(d1).append(d2);

            cpf = sb.toString();
        } while (CPFS_GERADOS.contains(cpf));

        CPFS_GERADOS.add(cpf);
        return cpf;
    }

    public static void resetarCPFsGerados() {
        CPFS_GERADOS.clear();
    }
}
