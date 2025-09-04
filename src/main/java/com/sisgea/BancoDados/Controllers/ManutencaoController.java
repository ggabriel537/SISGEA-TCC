package com.sisgea.BancoDados.Controllers;

import java.util.List;
import java.util.Date;
import com.sisgea.BancoDados.Models.ManutencaoModel;
import com.sisgea.Entidades.Manutencao;

public class ManutencaoController {
    public static void salvarManutencao(String descricao, Date data_est_man, String status) {
        Manutencao manutencao = new Manutencao();
        manutencao.setDescricao(descricao);
        manutencao.setData_est_man(data_est_man);
        manutencao.setStatus(status);
        ManutencaoModel.salvarManutencao(manutencao);
    }

    public static void salvarManutencao(Manutencao manutencao) {
        ManutencaoModel.salvarManutencao(manutencao);
    }

    public static List<Manutencao> listarManutencoes() {
        return ManutencaoModel.listarManutencoes();
    }

    public static Manutencao buscarId(String id) {
        return ManutencaoModel.buscarId(id);
    }

    public static void deletarManutencao(String id) {
        ManutencaoModel.excluirManutencao(id);
    }

    public static void atualizarManutencao(Manutencao manutencao) {
        ManutencaoModel.atualizarManutencao(manutencao);
    }
}
