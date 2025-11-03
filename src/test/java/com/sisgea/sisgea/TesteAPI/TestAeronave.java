package com.sisgea.sisgea.TesteAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Models.AeronaveModel;
import com.sisgea.Entidades.Aeronave;

public class TestAeronave {
    
    @Test
    public void testCreate() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-TST");
        aeronave.setModelo("Cessna 152");
        aeronave.setFabricante("Cessna");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-D");
        aeronave.setHoras_de_voo(1000.0f);
        aeronave.setAtivo(true);
        
        Request request = new Request();
        String retorno = request.requisicao(aeronave, "api/aeronaves", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        Aeronave encontrado = null;
        List<Aeronave> aeronaves = AeronaveModel.listarTodasAeronaves();
        for (Aeronave a : aeronaves) {
            if (a.getMatricula().equals("PT-TST")) {
                encontrado = a;
                AeronaveModel.excluirAeronave(a);
            }
        }
        
        assertTrue(retorno.contains("\"status\":\"sucesso\""), "Status diferente de sucesso: " + retorno);
        assertNotNull(encontrado, "Aeronave não encontrada após criação.");
    }

    @Test
    public void testRead() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-RED");
        aeronave.setModelo("Piper PA-28");
        aeronave.setFabricante("Piper");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-D");
        aeronave.setHoras_de_voo(500.0f);
        aeronave.setAtivo(true);
        
        Request request = new Request();
        String retorno = request.requisicao(aeronave, "api/aeronaves", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        Aeronave encontrado = null;
        List<Aeronave> aeronaves = AeronaveModel.listarTodasAeronaves();
        for (Aeronave a : aeronaves) {
            if (a.getMatricula().equals("PT-RED")) {
                encontrado = a;
                break;
            }
        }
        
        assertNotNull(encontrado, "Aeronave não encontrada na lista após criação.");
        assertEquals("Piper PA-28", encontrado.getModelo(), "Modelo incorreto: " + encontrado.getModelo());
        
        String respostaGet = request.requisicao(null, "api/aeronaves/PT-RED", "GET");
        assertTrue(respostaGet.contains("Piper PA-28"), "GET não retornou a aeronave correta: " + respostaGet);
        
        AeronaveModel.excluirAeronave(encontrado);
    }

    @Test
    public void testUpdate() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-UPD");
        aeronave.setModelo("Cessna 172");
        aeronave.setFabricante("Cessna");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-D");
        aeronave.setHoras_de_voo(800.0f);
        aeronave.setAtivo(true);
        
        Request request = new Request();
        String retorno = request.requisicao(aeronave, "api/aeronaves", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        Aeronave antigo = null;
        List<Aeronave> aeronaves = AeronaveModel.listarTodasAeronaves();
        for (Aeronave a : aeronaves) {
            if (a.getMatricula().equals("PT-UPD")) {
                antigo = a;
                break;
            }
        }
        
        assertNotNull(antigo, "Aeronave não encontrada para update.");
        
        antigo.setModelo("Cessna 172 Skyhawk");
        antigo.setHoras_de_voo(850.0f);
        String retornoUpdate = request.requisicao(antigo, "api/aeronaves/PT-UPD", "PUT");
        System.out.println("Resposta do servidor no UPDATE: " + retornoUpdate);
        
        assertTrue(retornoUpdate.contains("\"status\":\"sucesso\""), "Falha no update: " + retornoUpdate);
        
        Aeronave novo = null;
        aeronaves = AeronaveModel.listarTodasAeronaves();
        for (Aeronave a : aeronaves) {
            if (a.getMatricula().equals("PT-UPD")) {
                novo = a;
                break;
            }
        }
        
        assertNotNull(novo, "Aeronave não encontrada após update.");
        assertEquals("Cessna 172 Skyhawk", novo.getModelo(), "Modelo não foi atualizado: " + novo.getModelo());
        assertEquals(850.0f, novo.getHoras_de_voo(), "Horas de voo não foram atualizadas: " + novo.getHoras_de_voo());
        
        AeronaveModel.excluirAeronave(novo);
    }

    @Test
    public void testDelete() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-DEL");
        aeronave.setModelo("Beechcraft Baron");
        aeronave.setFabricante("Beechcraft");
        aeronave.setHabilitacao("MLTE");
        aeronave.setTipo_de_voo("IFR");
        aeronave.setHoras_de_voo(1500.0f);
        aeronave.setAtivo(true);
        
        Request request = new Request();
        String retorno = request.requisicao(aeronave, "api/aeronaves", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        Aeronave encontrado = null;
        List<Aeronave> aeronaves = AeronaveModel.listarTodasAeronaves();
        for (Aeronave a : aeronaves) {
            if (a.getMatricula().equals("PT-DEL")) {
                encontrado = a;
                break;
            }
        }
        
        assertNotNull(encontrado, "Aeronave não encontrada para delete.");
        
        String respostaDelete = request.requisicao(null, "api/aeronaves/PT-DEL", "DELETE");
        System.out.println("Resposta do servidor no DELETE: " + respostaDelete);
        
        Aeronave checar = AeronaveModel.buscarAeronave("PT-DEL");
        
        assertTrue(checar == null || !checar.getAtivo(), "Aeronave ainda está ativa após DELETE.");
        
        if (checar != null) {
            AeronaveModel.excluirAeronave(checar);
        }
    }

    @Test
    public void testMatriculaDuplicada() {
        Aeronave aeronave1 = new Aeronave();
        aeronave1.setMatricula("PT-DUP");
        aeronave1.setModelo("Cessna 152");
        aeronave1.setFabricante("Cessna");
        aeronave1.setHabilitacao("MNTE");
        aeronave1.setTipo_de_voo("VFR-D");
        aeronave1.setHoras_de_voo(1000.0f);
        aeronave1.setAtivo(true);
        
        Request request = new Request();
        String retorno1 = request.requisicao(aeronave1, "api/aeronaves", "POST");
        System.out.println("Primeira aeronave: " + retorno1);
        
        Aeronave aeronave2 = new Aeronave();
        aeronave2.setMatricula("PT-DUP");
        aeronave2.setModelo("Piper PA-28");
        aeronave2.setFabricante("Piper");
        aeronave2.setHabilitacao("MNTE");
        aeronave2.setTipo_de_voo("VFR-D");
        aeronave2.setHoras_de_voo(500.0f);
        aeronave2.setAtivo(true);
        
        String retorno2 = request.requisicao(aeronave2, "api/aeronaves", "POST");
        System.out.println("Segunda aeronave (matrícula duplicada): " + retorno2);
        
        List<Aeronave> aeronaves = AeronaveModel.listarTodasAeronaves();
        for (Aeronave a : aeronaves) {
            if (a.getMatricula().equals("PT-DUP")) {
                AeronaveModel.excluirAeronave(a);
            }
        }
        
        assertTrue(retorno1.contains("\"status\":\"sucesso\""), "Primeira aeronave deveria ter sucesso: " + retorno1);
        assertTrue(retorno2.contains("existe uma aeronave com esta matrícula") || retorno2.contains("error") || retorno2.contains("CONFLITO"), 
                  "Segunda aeronave deveria gerar conflito de matrícula: " + retorno2);
    }

    @Test
    public void testMatriculaInvalida() {
        Aeronave aeronave1 = new Aeronave();
        aeronave1.setMatricula("XX-ABC");
        aeronave1.setModelo("Cessna 152");
        aeronave1.setFabricante("Cessna");
        aeronave1.setHabilitacao("MNTE");
        aeronave1.setTipo_de_voo("VFR-D");
        aeronave1.setHoras_de_voo(1000.0f);
        aeronave1.setAtivo(true);
        
        Request request = new Request();
        String retorno1 = request.requisicao(aeronave1, "api/aeronaves", "POST");
        System.out.println("Aeronave com prefixo inválido: " + retorno1);
        
        Aeronave aeronave2 = new Aeronave();
        aeronave2.setMatricula("PT-ABCD");
        aeronave2.setModelo("Piper PA-28");
        aeronave2.setFabricante("Piper");
        aeronave2.setHabilitacao("MNTE");
        aeronave2.setTipo_de_voo("VFR-D");
        aeronave2.setHoras_de_voo(500.0f);
        aeronave2.setAtivo(true);
        
        String retorno2 = request.requisicao(aeronave2, "api/aeronaves", "POST");
        System.out.println("Aeronave com formato inválido: " + retorno2);
        
        List<Aeronave> aeronaves = AeronaveModel.listarTodasAeronaves();
        for (Aeronave a : aeronaves) {
            if (a.getMatricula().equals("XX-ABC") || a.getMatricula().equals("PT-ABCD")) {
                AeronaveModel.excluirAeronave(a);
            }
        }
        
        assertTrue(retorno1.contains("Matrícula inválida") || retorno1.contains("error") || retorno1.contains("CONFLITO"), 
                  "Deveria rejeitar matrícula com prefixo inválido: " + retorno1);
        assertTrue(retorno2.contains("Matrícula inválida") || retorno2.contains("error") || retorno2.contains("CONFLITO"), 
                  "Deveria rejeitar matrícula com formato inválido: " + retorno2);
    }

    @Test
    public void testCamposObrigatorios() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-OBG");
        aeronave.setModelo("");
        aeronave.setFabricante("");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-D");
        aeronave.setHoras_de_voo(1000.0f);
        aeronave.setAtivo(true);
        
        Request request = new Request();
        String retorno = request.requisicao(aeronave, "api/aeronaves", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        List<Aeronave> aeronaves = AeronaveModel.listarTodasAeronaves();
        for (Aeronave a : aeronaves) {
            if (a.getMatricula().equals("PT-OBG")) {
                AeronaveModel.excluirAeronave(a);
            }
        }
        
        assertTrue(retorno.contains("Modelo é obrigatório") || retorno.contains("Fabricante é obrigatório") || retorno.contains("error"), 
                  "Deveria rejeitar aeronave sem campos obrigatórios: " + retorno);
    }
}