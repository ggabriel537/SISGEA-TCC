package com.sisgea.BancoDados.Controllers;

import java.util.List;

import com.sisgea.BancoDados.Models.AeronaveModel;
import com.sisgea.Entidades.Aeronave;
import com.sisgea.Entidades.Manutencao;

public class AeronaveController {
    public static void salvarAeronave(
        String matricula, String modelo, String fabricante, String habilitacao, String tipo_de_voo, Float horas_de_voo, List<Manutencao> manutencoes) {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula(matricula);
        aeronave.setModelo(modelo);
        aeronave.setFabricante(fabricante);
        aeronave.setHabilitacao(habilitacao);
        aeronave.setTipo_de_voo(tipo_de_voo);
        aeronave.setHoras_de_voo(horas_de_voo);
        aeronave.setManutencoes(manutencoes);
        AeronaveModel.salvarAeronave(aeronave);
    }

    public static List<Aeronave> listarAeronaves() {
        return AeronaveModel.listarAeronaves();
    }

    public void deletarAeronave(Aeronave aeronave) {
        AeronaveModel.excluirAeronave(aeronave);
    }

    public void atualizarAeronave(Aeronave aeronave) {
        AeronaveModel.atualizarAeronave(aeronave);
    }
}
