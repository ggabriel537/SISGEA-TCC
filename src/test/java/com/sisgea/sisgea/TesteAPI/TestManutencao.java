package com.sisgea.sisgea.TesteAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Models.AeronaveModel;
import com.sisgea.BancoDados.Models.ManutencaoModel;
import com.sisgea.Entidades.Aeronave;
import com.sisgea.Entidades.Manutencao;

public class TestManutencao {
    
    @Test
    public void testCreate() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-MAN");
        aeronave.setModelo("Cessna 152");
        aeronave.setFabricante("Cessna");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-D");
        aeronave.setHoras_de_voo(1000.0f);
        aeronave.setAtivo(true);
        AeronaveModel.salvarAeronave(aeronave);
        
        Manutencao manutencao = new Manutencao();
        manutencao.setDescricao("Manutenção preventiva teste");
        manutencao.setData_est_man(new Date(System.currentTimeMillis() + 86400000));
        manutencao.setStatus("Pendente");
        manutencao.setAeronave(aeronave);
        
        Request request = new Request();
        String retorno = request.requisicao(manutencao, "api/manutencoes", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        Manutencao encontrado = null;
        List<Manutencao> manutencoes = ManutencaoModel.listarManutencoes();
        for (Manutencao m : manutencoes) {
            if (m.getDescricao().equals("Manutenção preventiva teste")) {
                encontrado = m;
                ManutencaoModel.excluirManutencao(m.getId().toString());
            }
        }
        
        AeronaveModel.excluirAeronave(aeronave);
        
        assertTrue(retorno.contains("SUCESSO"), "Status diferente de sucesso: " + retorno);
        assertNotNull(encontrado, "Manutenção não encontrada após criação.");
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
        AeronaveModel.salvarAeronave(aeronave);
        
        Manutencao manutencao = new Manutencao();
        manutencao.setDescricao("Manutenção para leitura");
        manutencao.setData_est_man(new Date(System.currentTimeMillis() + 86400000));
        manutencao.setStatus("Em andamento");
        manutencao.setAeronave(aeronave);
        
        Request request = new Request();
        String retorno = request.requisicao(manutencao, "api/manutencoes", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        Manutencao encontrado = null;
        List<Manutencao> manutencoes = ManutencaoModel.listarManutencoes();
        for (Manutencao m : manutencoes) {
            if (m.getDescricao().equals("Manutenção para leitura")) {
                encontrado = m;
                break;
            }
        }
        
        assertNotNull(encontrado, "Manutenção não encontrada na lista após criação.");
        assertEquals("Manutenção para leitura", encontrado.getDescricao(), "Descrição incorreta: " + encontrado.getDescricao());
        
        String respostaGet = request.requisicao(null, "api/manutencoes/" + encontrado.getId().toString(), "GET");
        assertTrue(respostaGet.contains("Manutenção para leitura"), "GET não retornou a manutenção correta: " + respostaGet);
        
        ManutencaoModel.excluirManutencao(encontrado.getId().toString());
        AeronaveModel.excluirAeronave(aeronave);
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
        AeronaveModel.salvarAeronave(aeronave);
        
        Manutencao manutencao = new Manutencao();
        manutencao.setDescricao("Manutenção para update");
        manutencao.setData_est_man(new Date(System.currentTimeMillis() + 86400000));
        manutencao.setStatus("Pendente");
        manutencao.setAeronave(aeronave);
        
        Request request = new Request();
        String retorno = request.requisicao(manutencao, "api/manutencoes", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        Manutencao antigo = null;
        List<Manutencao> manutencoes = ManutencaoModel.listarManutencoes();
        for (Manutencao m : manutencoes) {
            if (m.getDescricao().equals("Manutenção para update")) {
                antigo = m;
                break;
            }
        }
        
        assertNotNull(antigo, "Manutenção não encontrada para update.");
        
        antigo.setDescricao("Manutenção atualizada");
        antigo.setStatus("Concluída");
        String retornoUpdate = request.requisicao(antigo, "api/manutencoes/" + antigo.getId().toString(), "PUT");
        System.out.println("Resposta do servidor no UPDATE: " + retornoUpdate);
        
        assertTrue(retornoUpdate.contains("SUCESSO"), "Falha no update: " + retornoUpdate);
        
        Manutencao novo = null;
        manutencoes = ManutencaoModel.listarManutencoes();
        for (Manutencao m : manutencoes) {
            if (m.getId().equals(antigo.getId())) {
                novo = m;
                break;
            }
        }
        
        assertNotNull(novo, "Manutenção não encontrada após update.");
        assertEquals("Manutenção atualizada", novo.getDescricao(), "Descrição não foi atualizada: " + novo.getDescricao());
        assertEquals("Concluída", novo.getStatus(), "Status não foi atualizado: " + novo.getStatus());
        
        ManutencaoModel.excluirManutencao(novo.getId().toString());
        AeronaveModel.excluirAeronave(aeronave);
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
        AeronaveModel.salvarAeronave(aeronave);
        
        Manutencao manutencao = new Manutencao();
        manutencao.setDescricao("Manutenção para delete");
        manutencao.setData_est_man(new Date(System.currentTimeMillis() + 86400000));
        manutencao.setStatus("Pendente");
        manutencao.setAeronave(aeronave);
        
        Request request = new Request();
        String retorno = request.requisicao(manutencao, "api/manutencoes", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        Manutencao encontrado = null;
        List<Manutencao> manutencoes = ManutencaoModel.listarManutencoes();
        for (Manutencao m : manutencoes) {
            if (m.getDescricao().equals("Manutenção para delete")) {
                encontrado = m;
                break;
            }
        }
        
        assertNotNull(encontrado, "Manutenção não encontrada para delete.");
        
        String respostaDelete = request.requisicao(null, "api/manutencoes/" + encontrado.getId().toString(), "DELETE");
        System.out.println("Resposta do servidor no DELETE: " + respostaDelete);
        
        assertTrue(respostaDelete.contains("SUCESSO"), "Falha no delete: " + respostaDelete);
        
        Manutencao checar = ManutencaoModel.buscarId(encontrado.getId().toString());
        
        assertTrue(checar == null, "Manutenção ainda existe após DELETE.");
        
        AeronaveModel.excluirAeronave(aeronave);
    }

    @Test
    public void testValidacaoDescricaoObrigatoria() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-VAL1");
        aeronave.setModelo("Cessna 152");
        aeronave.setFabricante("Cessna");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-D");
        aeronave.setHoras_de_voo(1000.0f);
        aeronave.setAtivo(true);
        AeronaveModel.salvarAeronave(aeronave);
        
        Manutencao manutencao = new Manutencao();
        manutencao.setData_est_man(new Date(System.currentTimeMillis() + 86400000));
        manutencao.setStatus("Pendente");
        manutencao.setAeronave(aeronave);
        
        Request request = new Request();
        String retorno = request.requisicao(manutencao, "api/manutencoes", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        AeronaveModel.excluirAeronave(aeronave);
        
        assertTrue(retorno.contains("ERRO"), "Deveria retornar erro de descrição obrigatória: " + retorno);
        assertTrue(retorno.contains("Descrição") || retorno.contains("obrigatória"), 
                "Mensagem de erro não menciona descrição: " + retorno);
    }

    @Test
    public void testValidacaoDataEstimadaObrigatoria() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-VAL2");
        aeronave.setModelo("Cessna 152");
        aeronave.setFabricante("Cessna");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-D");
        aeronave.setHoras_de_voo(1000.0f);
        aeronave.setAtivo(true);
        AeronaveModel.salvarAeronave(aeronave);
        
        Manutencao manutencao = new Manutencao();
        manutencao.setDescricao("Teste sem data");
        manutencao.setStatus("Pendente");
        manutencao.setAeronave(aeronave);
        
        Request request = new Request();
        String retorno = request.requisicao(manutencao, "api/manutencoes", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        AeronaveModel.excluirAeronave(aeronave);
        
        assertTrue(retorno.contains("ERRO"), "Deveria retornar erro de data estimada obrigatória: " + retorno);
        assertTrue(retorno.contains("Data estimada") || retorno.contains("obrigatória"), 
                "Mensagem de erro não menciona data estimada: " + retorno);
    }

    @Test
    public void testValidacaoStatusObrigatorio() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-VAL3");
        aeronave.setModelo("Cessna 152");
        aeronave.setFabricante("Cessna");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-D");
        aeronave.setHoras_de_voo(1000.0f);
        aeronave.setAtivo(true);
        AeronaveModel.salvarAeronave(aeronave);
        
        Manutencao manutencao = new Manutencao();
        manutencao.setDescricao("Teste sem status");
        manutencao.setData_est_man(new Date(System.currentTimeMillis() + 86400000));
        manutencao.setAeronave(aeronave);
        
        Request request = new Request();
        String retorno = request.requisicao(manutencao, "api/manutencoes", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        AeronaveModel.excluirAeronave(aeronave);
        
        assertTrue(retorno.contains("ERRO"), "Deveria retornar erro de status obrigatório: " + retorno);
        assertTrue(retorno.contains("Status") || retorno.contains("obrigatório"), 
                "Mensagem de erro não menciona status: " + retorno);
    }

    @Test
    public void testValidacaoAeronaveObrigatoria() {
        Manutencao manutencao = new Manutencao();
        manutencao.setDescricao("Teste sem aeronave");
        manutencao.setData_est_man(new Date(System.currentTimeMillis() + 86400000));
        manutencao.setStatus("Pendente");
        
        Request request = new Request();
        String retorno = request.requisicao(manutencao, "api/manutencoes", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        assertTrue(retorno.contains("ERRO"), "Deveria retornar erro de aeronave obrigatória: " + retorno);
        assertTrue(retorno.contains("Aeronave") || retorno.contains("obrigatória"), 
                "Mensagem de erro não menciona aeronave: " + retorno);
    }

    @Test
    public void testValidacaoAeronaveInexistente() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-INEX");
        
        Manutencao manutencao = new Manutencao();
        manutencao.setDescricao("Teste aeronave inexistente");
        manutencao.setData_est_man(new Date(System.currentTimeMillis() + 86400000));
        manutencao.setStatus("Pendente");
        manutencao.setAeronave(aeronave);
        
        Request request = new Request();
        String retorno = request.requisicao(manutencao, "api/manutencoes", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        assertTrue(retorno.contains("ERRO"), "Deveria retornar erro de aeronave não encontrada: " + retorno);
        assertTrue(retorno.contains("Aeronave") && retorno.contains("encontrada"), 
                "Mensagem de erro não menciona aeronave não encontrada: " + retorno);
    }

    @Test
    public void testWarningDataNoPassado() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-WARN");
        aeronave.setModelo("Cessna 152");
        aeronave.setFabricante("Cessna");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-D");
        aeronave.setHoras_de_voo(1000.0f);
        aeronave.setAtivo(true);
        AeronaveModel.salvarAeronave(aeronave);
        
        Manutencao manutencao = new Manutencao();
        manutencao.setDescricao("Teste warning data passado");
        manutencao.setData_est_man(new Date(System.currentTimeMillis() - 86400000));
        manutencao.setStatus("Pendente");
        manutencao.setAeronave(aeronave);
        
        Request request = new Request();
        String retorno = request.requisicao(manutencao, "api/manutencoes", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        AeronaveModel.excluirAeronave(aeronave);
        
        assertTrue(retorno.contains("AVISO"), "Deveria retornar aviso de data no passado: " + retorno);
        assertTrue(retorno.contains("passado"), "Mensagem de aviso não menciona data no passado: " + retorno);
    }
}