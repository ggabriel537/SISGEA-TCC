package com.sisgea.sisgea;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Controllers.AeronaveController;
import com.sisgea.Entidades.Aeronave;

class TesteAeronave {
    @Test
    void testSalvarAeronave() {
        AeronaveController.salvarAeronave("PT-AAA", "Cessna", "Cessna Co", "PPA", "VFR", 100f, new ArrayList<>());
        List<Aeronave> lista = AeronaveController.listarAeronaves();
        assertFalse(lista.isEmpty(), "Nenhuma aeronave salva.");
        Aeronave aeronave = null;
        for (Aeronave a : lista) {
            if ("PT-AAA".equals(a.getMatricula())) {
                aeronave = a;
                break;
            }
        }
        assertNotNull(aeronave, "Aeronave não encontrada.");
    }

    @Test
    void testListarAeronaves() {
        List<Aeronave> lista = AeronaveController.listarAeronaves();
        assertNotNull(lista, "Lista null.");
    }

    @Test
    void testAtualizarAeronave() {
        AeronaveController.salvarAeronave("PT-BBB", "Cessna", "Cessna Co", "PPA", "VFR", 100f, new ArrayList<>());
        Aeronave aeronave = null;
        for (Aeronave a : AeronaveController.listarAeronaves()) {
            if ("PT-BBB".equals(a.getMatricula())) {
                aeronave = a;
                break;
            }
        }
        assertNotNull(aeronave, "Aeronave para update não encontrada.");
        aeronave.setModelo("Atualizado");
        new AeronaveController().atualizarAeronave(aeronave);
        Aeronave atualizado = null;
        for (Aeronave a : AeronaveController.listarAeronaves()) {
            if ("Atualizado".equals(a.getModelo())) {
                atualizado = a;
                break;
            }
        }
        assertNotNull(atualizado, "Aeronave não foi atualizada.");
    }

    @Test
    void testDeletarAeronave() {
        AeronaveController.salvarAeronave("PT-CCC", "Cessna", "Cessna Co", "PPA", "VFR", 100f, new ArrayList<>());
        Aeronave aeronave = null;
        for (Aeronave a : AeronaveController.listarAeronaves()) {
            if ("PT-CCC".equals(a.getMatricula())) {
                aeronave = a;
                break;
            }
        }
        assertNotNull(aeronave, "Aeronave para deletar não encontrada.");
        new AeronaveController().deletarAeronave(aeronave);
        Aeronave deletada = null;
        for (Aeronave a : AeronaveController.listarAeronaves()) {
            if ("PT-CCC".equals(a.getMatricula())) {
                deletada = a;
                break;
            }
        }
        assertNull(deletada, "Aeronave não foi deletada.");
    }
}
