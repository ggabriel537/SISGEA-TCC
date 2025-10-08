package com.sisgea.BancoDados.Controllers;

import java.util.List;

import com.sisgea.BancoDados.Models.AeronaveModel;
import com.sisgea.Entidades.Aeronave;
import com.sisgea.Entidades.Manutencao;

public class AeronaveController {
    public static void salvarAeronave(
        String matricula, String modelo, String fabricante, String habilitacao, String tipo_de_voo, Float horas_de_voo, List<Manutencao> manutencoes) {
        Aeronave aeronave = new Aeronave();
        aeronave.setAtivo(true);
        aeronave.setMatricula(matricula);
        aeronave.setModelo(modelo);
        aeronave.setFabricante(fabricante);
        aeronave.setHabilitacao(habilitacao);
        aeronave.setTipo_de_voo(tipo_de_voo);
        aeronave.setHoras_de_voo(horas_de_voo);
        aeronave.setManutencoes(manutencoes);
        AeronaveModel.salvarAeronave(aeronave);
    }

    public static void salvarAeronave(Aeronave aeronave) {
        aeronave.setAtivo(true);
        AeronaveModel.salvarAeronave(aeronave);
    }

    public static List<Aeronave> listarAeronaves() {
        return AeronaveModel.listarAeronaves();
    }

    public static List<Aeronave> listarTodasAeronaves() {
        return AeronaveModel.listarTodasAeronaves();
    }

    public static Aeronave buscarId(String id) {
        return AeronaveModel.buscarAeronave(id);
    }

    public static void deletarAeronave(Aeronave aeronave) {
        aeronave.setAtivo(false);
        AeronaveModel.atualizarAeronave(aeronave);
    }

    public static void atualizarAeronave(Aeronave aeronave) {
        AeronaveModel.atualizarAeronave(aeronave);
    }
}
