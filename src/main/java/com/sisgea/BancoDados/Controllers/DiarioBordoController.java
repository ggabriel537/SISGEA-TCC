package com.sisgea.BancoDados.Controllers;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.sisgea.BancoDados.Models.DiarioBordoModel;
import com.sisgea.Entidades.Agendamento;
import com.sisgea.Entidades.DiarioBordo;
import com.sisgea.Entidades.Discrepancia;

public class DiarioBordoController {
    public static void salvarDiarioBordo(
            UUID id,
            String aeronaveId,
            Integer nroDiario,
            Date data,
            String funcaoAluno,
            String funcaoInstrutor,
            Float horaAeronave,
            Date dataDecolagem,
            Date dataPouso,
            String localDecolagem,
            String localPouso,
            Date dataCorte,
            Float horasDiu,
            Float horasNot,
            Float horasVfr,
            Float horasIfr,
            Float horasIfrC,
            Float combustivelUtilizado,
            Integer ciclos,
            Integer pob,
            Float carga,
            String nat,
            String ocorrencias,
            List<Discrepancia> discrepancias,
            Agendamento agendamento) {
        DiarioBordo diario = new DiarioBordo();
        diario.setId(id);
        diario.setAeronaveId(aeronaveId);
        diario.setNroDiario(nroDiario);
        diario.setData(data);
        diario.setFuncaoAluno(funcaoAluno);
        diario.setFuncaoInstrutor(funcaoInstrutor);
        diario.setHoraAeronave(horaAeronave);
        diario.setDataDecolagem(dataDecolagem);
        diario.setDataPouso(dataPouso);
        diario.setLocalDecolagem(localDecolagem);
        diario.setLocalPouso(localPouso);
        diario.setDataCorte(dataCorte);
        diario.setHorasDiu(horasDiu);
        diario.setHorasNot(horasNot);
        diario.setHorasVfr(horasVfr);
        diario.setHorasIfr(horasIfr);
        diario.setHorasIfrC(horasIfrC);
        diario.setCombustivelUtilizado(combustivelUtilizado);
        diario.setCiclos(ciclos);
        diario.setPob(pob);
        diario.setCarga(carga);
        diario.setNat(nat);
        diario.setOcorrencias(ocorrencias);
        diario.setDiscrepancias(discrepancias);

        DiarioBordoModel.salvarDiarioBordo(diario);
    }

    public static void salvarDiarioBordo(DiarioBordo diarioBordo) {
        DiarioBordoModel.salvarDiarioBordo(diarioBordo);
    }

    public static List<DiarioBordo> listarDiariosBordo() {
        return DiarioBordoModel.listarDiariosBordo();
    }

    public static DiarioBordo buscarId(String id) {
        return DiarioBordoModel.buscarDiarioBordo(id);
    }

    public static void deletarDiarioBordo(DiarioBordo diarioBordo) {
        DiarioBordoModel.excluirDiarioBordo(diarioBordo);
    }

    public static void atualizarDiarioBordo(DiarioBordo diarioBordo) {
        DiarioBordoModel.atualizarDiarioBordo(diarioBordo);
    }
}
