package com.sisgea.BancoDados.Controllers;

import java.util.Date;
import java.util.List;

import com.sisgea.BancoDados.Models.DiscrepanciaModel;
import com.sisgea.Entidades.Discrepancia;

public class DiscrepanciaController {
    public static void salvarDiscrepancia(Date data, String sistema, String discrepancia, String correcao) {
        Discrepancia discrepanciaObj = new Discrepancia();
        discrepanciaObj.setData(data);
        discrepanciaObj.setSistema(sistema);
        discrepanciaObj.setDiscrepancia(discrepancia);
        discrepanciaObj.setCorrecao(correcao);
        DiscrepanciaModel.salvarDiscrepancia(discrepanciaObj);
    }

    public static List<Discrepancia> listarDiscrepancias() {
        return DiscrepanciaModel.listarDiscrepancias();
    }

    public void deletarDiscrepancia(Discrepancia discrepancia) {
        DiscrepanciaModel.excluirDiscrepancia(discrepancia);
    }

    public void atualizarDiscrepancia(Discrepancia discrepancia) {
        DiscrepanciaModel.atualizarDiscrepancia(discrepancia);
    }
}
