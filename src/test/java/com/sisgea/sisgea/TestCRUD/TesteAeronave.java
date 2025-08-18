package com.sisgea.sisgea.TestCRUD;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Controllers.AeronaveController;
import com.sisgea.Entidades.Aeronave;
import com.sisgea.sisgea.Uteis;

class TesteAeronave {
    @Test
    void testSalvarAeronave() {
        String matricula = Uteis.gerarMatriculaAleatoria();
        AeronaveController.salvarAeronave(matricula, "Cessna", "Cessna Co", "PPA", "VFR", 100f, new ArrayList<>());
        List<Aeronave> lista = AeronaveController.listarAeronaves();
        assertFalse(lista.isEmpty(), "Nenhuma aeronave salva.");
        Aeronave aeronave = null;
        for (Aeronave a : lista) {
            if (matricula.equals(a.getMatricula())) {
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
        String matricula = Uteis.gerarMatriculaAleatoria();
        AeronaveController.salvarAeronave(matricula, "Cessna", "Cessna Co", "PPA", "VFR", 100f, new ArrayList<>());
        Aeronave aeronave = null;
        for (Aeronave a : AeronaveController.listarAeronaves()) {
            if (matricula.equals(a.getMatricula())) {
                aeronave = a;
                break;
            }
        }
        assertNotNull(aeronave, "Aeronave para update não encontrada.");
        aeronave.setModelo("Atualizado");
        AeronaveController.atualizarAeronave(aeronave);
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
    String matricula = Uteis.gerarMatriculaAleatoria();
    AeronaveController.salvarAeronave(matricula, "Cessna", "Cessna Co", "PPA", "VFR", 100f, new ArrayList<>());

    Aeronave aeronave = null;
    for (Aeronave a : AeronaveController.listarAeronaves()) {
        if (matricula.equals(a.getMatricula())) {
            aeronave = a;
            break;
        }
    }

    assertNotNull(aeronave, "Aeronave para deletar não encontrada.");
    AeronaveController.deletarAeronave(aeronave);

    Aeronave deletada = null;
    for (Aeronave a : AeronaveController.listarAeronaves()) {
        if (matricula.equals(a.getMatricula())) {
            deletada = a;
            break;
        }
    }
    assertNull(deletada, "Aeronave não foi deletada.");
}

}
