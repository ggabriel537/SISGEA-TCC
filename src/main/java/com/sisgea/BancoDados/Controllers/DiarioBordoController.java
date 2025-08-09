package com.sisgea.BancoDados.Controllers;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.sisgea.BancoDados.Models.DiarioBordoModel;
import com.sisgea.Entidades.Agendamento;
import com.sisgea.Entidades.DiarioBordo;
import com.sisgea.Entidades.Discrepancia;

public class DiarioBordoController {
    public DiarioBordoController(UUID id, String aeronaveId, Integer nroDiario, Date data, String funcaoAluno, String funcaoInstrutor,
                                Float horaAeronave, Date dataDecolagem, Date dataPouso, String localDecolagem,
                                String localPouso, Date dataCorte, Float horasDiu, Float horasNot, Float horasVfr,
                                Float horasIfr, Float horasIfrC, Float combustivelUtilizado, Integer ciclos,
                                Integer pob, Float carga, String nat, String ocorrencias, List<Discrepancia> discrepancias,
    Agendamento agendamento) {
        DiarioBordo diarioBordo = new DiarioBordo();
        diarioBordo.setId(id);
        diarioBordo.setAeronaveId(aeronaveId);
        diarioBordo.setNroDiario(nroDiario);
        diarioBordo.setData(data);
        diarioBordo.setFuncaoAluno(funcaoAluno);
        diarioBordo.setFuncaoInstrutor(funcaoInstrutor);
        diarioBordo.setHoraAeronave(horaAeronave);
        diarioBordo.setDataDecolagem(dataDecolagem);
        diarioBordo.setDataPouso(dataPouso);
        diarioBordo.setLocalDecolagem(localDecolagem);
        diarioBordo.setLocalPouso(localPouso);
        diarioBordo.setDataCorte(dataCorte);
        diarioBordo.setHorasDiu(horasDiu);
        diarioBordo.setHorasNot(horasNot);
        diarioBordo.setHorasVfr(horasVfr);
        diarioBordo.setHorasIfr(horasIfr);
        diarioBordo.setHorasIfrC(horasIfrC);
        diarioBordo.setCombustivelUtilizado(combustivelUtilizado);
        diarioBordo.setCiclos(ciclos);
        diarioBordo.setPob(pob);
        diarioBordo.setCarga(carga);
        diarioBordo.setNat(nat);
        diarioBordo.setOcorrencias(ocorrencias);
        diarioBordo.setDiscrepancias(discrepancias);
        diarioBordo.setAgendamento(agendamento);
        DiarioBordoModel.salvarDiarioBordo(diarioBordo);
    }

    public static List<DiarioBordo> listarDiariosBordo() {
        return DiarioBordoModel.listarDiariosBordo();
    }

    public void deletarDiarioBordo(DiarioBordo diarioBordo) {
        DiarioBordoModel.excluirDiarioBordo(diarioBordo);
    }

    public void atualizarDiarioBordo(DiarioBordo diarioBordo) {
        DiarioBordoModel.atualizarDiarioBordo(diarioBordo);
    }
}
