package com.sisgea.sisgea.TesteAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Models.AeronaveModel;
import com.sisgea.BancoDados.Models.AlunoModel;
import com.sisgea.BancoDados.Models.DiarioBordoModel;
import com.sisgea.BancoDados.Models.InstrutorModel;
import com.sisgea.Entidades.Aeronave;
import com.sisgea.Entidades.Aluno;
import com.sisgea.Entidades.DiarioBordo;
import com.sisgea.Entidades.Endereco;
import com.sisgea.Entidades.Instrutor;
import com.sisgea.Entidades.Usuario;

public class TestDiarioBordo {
    
    @Test
    public void testCreate() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-DBO");
        aeronave.setModelo("Cessna 152");
        aeronave.setFabricante("Cessna");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-D");
        aeronave.setHoras_de_voo(1000.0f);
        aeronave.setAtivo(true);
        AeronaveModel.salvarAeronave(aeronave);
        
        Aluno aluno = new Aluno();
        aluno.setCpf("10101010101");
        aluno.setCanac(101010);
        aluno.setNome("Aluno Diario");
        aluno.setTelefone("67999999999");
        aluno.setEmail("aluno@diario.com");
        aluno.setCurso("Piloto Privado");
        aluno.setHoras_compradas(50.0f);
        aluno.setHoras_voadas(10.0f);
        aluno.setAtivo(true);
        Endereco endAluno = new Endereco();
        endAluno.setCep("79800000");
        endAluno.setCidade("Dourados");
        endAluno.setUF("MS");
        endAluno.setLogradouro("Rua Diario");
        endAluno.setNumero("100");
        endAluno.setBairro("Centro");
        endAluno.setComplemento("Apto 1");
        aluno.setEndereco(endAluno);
        AlunoModel.salvarAluno(aluno);
        
        Instrutor instrutor = new Instrutor();
        instrutor.setCpf("20202020202");
        instrutor.setCanac(202020);
        instrutor.setNome("Instrutor Diario");
        instrutor.setTelefone("67988888888");
        instrutor.setEmail("instrutor@diario.com");
        instrutor.setHabilitacao("INVA");
        instrutor.setAtivo(true);
        Usuario usuario = new Usuario();
        usuario.setUsuario("inst_diario");
        usuario.setSenha("123");
        usuario.setPermissao(0);
        instrutor.setUsuario(usuario);
        Endereco endInst = new Endereco();
        endInst.setCep("79800000");
        endInst.setCidade("Dourados");
        endInst.setUF("MS");
        endInst.setLogradouro("Rua Instrutor");
        endInst.setNumero("200");
        endInst.setBairro("Centro");
        endInst.setComplemento("Casa");
        instrutor.setEndereco(endInst);
        InstrutorModel.salvarInstrutor(instrutor);
        
        DiarioBordo diario = new DiarioBordo();
        diario.setAeronaveId("PT-DBO");
        diario.setNroDiario(1);
        diario.setData(new Date());
        diario.setAlunoId("10101010101");
        diario.setInstrutorId("20202020202");
        diario.setFuncaoAluno("I1");
        diario.setFuncaoInstrutor("V1");
        diario.setHoraAeronave(1005.0f);
        diario.setDataDecolagem(new Date(System.currentTimeMillis() - 7200000));
        diario.setDataPouso(new Date(System.currentTimeMillis() - 3600000));
        diario.setLocalDecolagem("SBDO");
        diario.setLocalPouso("SBCG");
        diario.setDataCorte(new Date(System.currentTimeMillis() - 3000000));
        diario.setHorasDiu(1.0f);
        diario.setHorasNot(0.0f);
        diario.setHorasVfr(1.0f);
        diario.setHorasIfr(0.0f);
        diario.setHorasIfrC(0.0f);
        diario.setCombustivelUtilizado("50L");
        diario.setCiclos(2);
        diario.setPob(2);
        diario.setCarga("50kg");
        diario.setNat("TN");
        diario.setOcorrencias("Voo sem ocorrências");
        
        Request request = new Request();
        String retorno = request.requisicao(diario, "api/diarios-bordo", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        DiarioBordo encontrado = null;
        List<DiarioBordo> diarios = DiarioBordoModel.listarDiariosBordo();
        for (DiarioBordo d : diarios) {
            if (d.getAeronaveId() != null && d.getAeronaveId().equals("PT-DBO") && 
                d.getNroDiario() != null && d.getNroDiario().equals(1)) {
                encontrado = d;
                DiarioBordoModel.excluirDiarioBordo(d.getId().toString());
            }
        }
        
        InstrutorModel.excluirInstrutor(instrutor.getCpf());
        AlunoModel.excluirAluno(aluno);
        AeronaveModel.excluirAeronave(aeronave);
        
        assertTrue(retorno.contains("\"status\":\"sucesso\""), "Status diferente de sucesso: " + retorno);
        assertNotNull(encontrado, "Diário de bordo não encontrado após criação.");
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
        
        Aluno aluno = new Aluno();
        aluno.setCpf("30303030303");
        aluno.setCanac(303030);
        aluno.setNome("Aluno Leitura");
        aluno.setTelefone("67977777777");
        aluno.setEmail("aluno@read.com");
        aluno.setCurso("Piloto Comercial");
        aluno.setHoras_compradas(100.0f);
        aluno.setHoras_voadas(20.0f);
        aluno.setAtivo(true);
        Endereco endAluno = new Endereco();
        endAluno.setCep("79800000");
        endAluno.setCidade("Dourados");
        endAluno.setUF("MS");
        endAluno.setLogradouro("Rua Leitura");
        endAluno.setNumero("300");
        endAluno.setBairro("Centro");
        endAluno.setComplemento("Bloco A");
        aluno.setEndereco(endAluno);
        AlunoModel.salvarAluno(aluno);
        
        Instrutor instrutor = new Instrutor();
        instrutor.setCpf("40404040404");
        instrutor.setCanac(404040);
        instrutor.setNome("Instrutor Leitura");
        instrutor.setTelefone("67966666666");
        instrutor.setEmail("instrutor@read.com");
        instrutor.setHabilitacao("PLA");
        instrutor.setAtivo(true);
        Usuario usuario = new Usuario();
        usuario.setUsuario("inst_read_db");
        usuario.setSenha("123");
        usuario.setPermissao(0);
        instrutor.setUsuario(usuario);
        Endereco endInst = new Endereco();
        endInst.setCep("79800000");
        endInst.setCidade("Dourados");
        endInst.setUF("MS");
        endInst.setLogradouro("Rua Instrutor Leitura");
        endInst.setNumero("400");
        endInst.setBairro("Centro");
        endInst.setComplemento("Fundos");
        instrutor.setEndereco(endInst);
        InstrutorModel.salvarInstrutor(instrutor);
        
        DiarioBordo diario = new DiarioBordo();
        diario.setAeronaveId("PT-RED");
        diario.setNroDiario(1);
        diario.setData(new Date());
        diario.setAlunoId("30303030303");
        diario.setInstrutorId("40404040404");
        diario.setFuncaoAluno("I1");
        diario.setFuncaoInstrutor("V1");
        diario.setHoraAeronave(502.0f);
        diario.setDataDecolagem(new Date(System.currentTimeMillis() - 7200000));
        diario.setDataPouso(new Date(System.currentTimeMillis() - 3600000));
        diario.setLocalDecolagem("SBCG");
        diario.setLocalPouso("SBDO");
        diario.setDataCorte(new Date(System.currentTimeMillis() - 3000000));
        diario.setHorasDiu(2.0f);
        diario.setHorasNot(0.0f);
        diario.setHorasVfr(2.0f);
        diario.setHorasIfr(0.0f);
        diario.setHorasIfrC(0.0f);
        diario.setCombustivelUtilizado("70L");
        diario.setCiclos(2);
        diario.setPob(2);
        diario.setCarga("60kg");
        diario.setNat("TN");
        diario.setOcorrencias("Voo de instrução");
        
        Request request = new Request();
        String retorno = request.requisicao(diario, "api/diarios-bordo", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        DiarioBordo encontrado = null;
        List<DiarioBordo> diarios = DiarioBordoModel.listarDiariosBordo();
        for (DiarioBordo d : diarios) {
            if (d.getAeronaveId() != null && d.getAeronaveId().equals("PT-RED") && 
                d.getNroDiario() != null && d.getNroDiario().equals(1)) {
                encontrado = d;
                break;
            }
        }
        
        assertNotNull(encontrado, "Diário de bordo não encontrado na lista após criação.");
        assertEquals("SBCG", encontrado.getLocalDecolagem(), "Local de decolagem incorreto: " + encontrado.getLocalDecolagem());
        
        String respostaGet = request.requisicao(null, "api/diarios-bordo/" + encontrado.getId().toString(), "GET");
        assertTrue(respostaGet.contains("SBCG"), "GET não retornou o diário de bordo correto: " + respostaGet);
        
        DiarioBordoModel.excluirDiarioBordo(encontrado.getId().toString());
        InstrutorModel.excluirInstrutor(instrutor.getCpf());
        AlunoModel.excluirAluno(aluno);
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
        
        Aluno aluno = new Aluno();
        aluno.setCpf("50505050505");
        aluno.setCanac(505050);
        aluno.setNome("Aluno Update");
        aluno.setTelefone("67955555555");
        aluno.setEmail("aluno@upd.com");
        aluno.setCurso("IFR");
        aluno.setHoras_compradas(80.0f);
        aluno.setHoras_voadas(30.0f);
        aluno.setAtivo(true);
        Endereco endAluno = new Endereco();
        endAluno.setCep("79800000");
        endAluno.setCidade("Dourados");
        endAluno.setUF("MS");
        endAluno.setLogradouro("Rua Update");
        endAluno.setNumero("500");
        endAluno.setBairro("Centro");
        endAluno.setComplemento("Sala 1");
        aluno.setEndereco(endAluno);
        AlunoModel.salvarAluno(aluno);
        
        Instrutor instrutor = new Instrutor();
        instrutor.setCpf("60606060606");
        instrutor.setCanac(606060);
        instrutor.setNome("Instrutor Update");
        instrutor.setTelefone("67944444444");
        instrutor.setEmail("instrutor@upd.com");
        instrutor.setHabilitacao("PC");
        instrutor.setAtivo(true);
        Usuario usuario = new Usuario();
        usuario.setUsuario("inst_upd_db");
        usuario.setSenha("123");
        usuario.setPermissao(0);
        instrutor.setUsuario(usuario);
        Endereco endInst = new Endereco();
        endInst.setCep("79800000");
        endInst.setCidade("Dourados");
        endInst.setUF("MS");
        endInst.setLogradouro("Rua Instrutor Update");
        endInst.setNumero("600");
        endInst.setBairro("Centro");
        endInst.setComplemento("Apto 2");
        instrutor.setEndereco(endInst);
        InstrutorModel.salvarInstrutor(instrutor);
        
        DiarioBordo diario = new DiarioBordo();
        diario.setAeronaveId("PT-UPD");
        diario.setNroDiario(1);
        diario.setData(new Date());
        diario.setAlunoId("50505050505");
        diario.setInstrutorId("60606060606");
        diario.setFuncaoAluno("I1");
        diario.setFuncaoInstrutor("V1");
        diario.setHoraAeronave(803.0f);
        diario.setDataDecolagem(new Date(System.currentTimeMillis() - 7200000));
        diario.setDataPouso(new Date(System.currentTimeMillis() - 3600000));
        diario.setLocalDecolagem("SBSP");
        diario.setLocalPouso("SBRJ");
        diario.setDataCorte(new Date(System.currentTimeMillis() - 3000000));
        diario.setHorasDiu(3.0f);
        diario.setHorasNot(0.0f);
        diario.setHorasVfr(3.0f);
        diario.setHorasIfr(0.0f);
        diario.setHorasIfrC(0.0f);
        diario.setCombustivelUtilizado("90L");
        diario.setCiclos(2);
        diario.setPob(2);
        diario.setCarga("70kg");
        diario.setNat("TN");
        diario.setOcorrencias("Voo de treinamento");
        
        Request request = new Request();
        String retorno = request.requisicao(diario, "api/diarios-bordo", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        DiarioBordo antigo = null;
        List<DiarioBordo> diarios = DiarioBordoModel.listarDiariosBordo();
        for (DiarioBordo d : diarios) {
            if (d.getAeronaveId() != null && d.getAeronaveId().equals("PT-UPD") && 
                d.getNroDiario() != null && d.getNroDiario().equals(1)) {
                antigo = d;
                break;
            }
        }
        
        assertNotNull(antigo, "Diário de bordo não encontrado para update.");
        
        antigo.setOcorrencias("Voo atualizado");
        antigo.setCombustivelUtilizado("100L");
        String retornoUpdate = request.requisicao(antigo, "api/diarios-bordo/" + antigo.getId().toString(), "PUT");
        System.out.println("Resposta do servidor no UPDATE: " + retornoUpdate);
        
        assertTrue(retornoUpdate.contains("\"status\":\"sucesso\""), "Falha no update: " + retornoUpdate);
        
        DiarioBordo novo = null;
        diarios = DiarioBordoModel.listarDiariosBordo();
        for (DiarioBordo d : diarios) {
            if (d.getId().equals(antigo.getId())) {
                novo = d;
                break;
            }
        }
        
        assertNotNull(novo, "Diário de bordo não encontrado após update.");
        assertEquals("Voo atualizado", novo.getOcorrencias(), "Ocorrências não foram atualizadas: " + novo.getOcorrencias());
        assertEquals("100L", novo.getCombustivelUtilizado(), "Combustível não foi atualizado: " + novo.getCombustivelUtilizado());
        
        DiarioBordoModel.excluirDiarioBordo(novo.getId().toString());
        InstrutorModel.excluirInstrutor(instrutor.getCpf());
        AlunoModel.excluirAluno(aluno);
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
        
        Aluno aluno = new Aluno();
        aluno.setCpf("70707070707");
        aluno.setCanac(707070);
        aluno.setNome("Aluno Delete");
        aluno.setTelefone("67933333333");
        aluno.setEmail("aluno@del.com");
        aluno.setCurso("Piloto Comercial");
        aluno.setHoras_compradas(120.0f);
        aluno.setHoras_voadas(50.0f);
        aluno.setAtivo(true);
        Endereco endAluno = new Endereco();
        endAluno.setCep("79800000");
        endAluno.setCidade("Dourados");
        endAluno.setUF("MS");
        endAluno.setLogradouro("Rua Delete");
        endAluno.setNumero("700");
        endAluno.setBairro("Centro");
        endAluno.setComplemento("Casa 3");
        aluno.setEndereco(endAluno);
        AlunoModel.salvarAluno(aluno);
        
        Instrutor instrutor = new Instrutor();
        instrutor.setCpf("80808080808");
        instrutor.setCanac(808080);
        instrutor.setNome("Instrutor Delete");
        instrutor.setTelefone("67922222222");
        instrutor.setEmail("instrutor@del.com");
        instrutor.setHabilitacao("INVA");
        instrutor.setAtivo(true);
        Usuario usuario = new Usuario();
        usuario.setUsuario("inst_del_db");
        usuario.setSenha("123");
        usuario.setPermissao(0);
        instrutor.setUsuario(usuario);
        Endereco endInst = new Endereco();
        endInst.setCep("79800000");
        endInst.setCidade("Dourados");
        endInst.setUF("MS");
        endInst.setLogradouro("Rua Instrutor Delete");
        endInst.setNumero("800");
        endInst.setBairro("Centro");
        endInst.setComplemento("Bloco B");
        instrutor.setEndereco(endInst);
        InstrutorModel.salvarInstrutor(instrutor);
        
        DiarioBordo diario = new DiarioBordo();
        diario.setAeronaveId("PT-DEL");
        diario.setNroDiario(1);
        diario.setData(new Date());
        diario.setAlunoId("70707070707");
        diario.setInstrutorId("80808080808");
        diario.setFuncaoAluno("I1");
        diario.setFuncaoInstrutor("V1");
        diario.setHoraAeronave(1504.0f);
        diario.setDataDecolagem(new Date(System.currentTimeMillis() - 7200000));
        diario.setDataPouso(new Date(System.currentTimeMillis() - 3600000));
        diario.setLocalDecolagem("SBGR");
        diario.setLocalPouso("SBKP");
        diario.setDataCorte(new Date(System.currentTimeMillis() - 3000000));
        diario.setHorasDiu(4.0f);
        diario.setHorasNot(0.0f);
        diario.setHorasVfr(0.0f);
        diario.setHorasIfr(4.0f);
        diario.setHorasIfrC(0.0f);
        diario.setCombustivelUtilizado("120L");
        diario.setCiclos(2);
        diario.setPob(2);
        diario.setCarga("80kg");
        diario.setNat("TN");
        diario.setOcorrencias("Voo IFR");
        
        Request request = new Request();
        String retorno = request.requisicao(diario, "api/diarios-bordo", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        DiarioBordo encontrado = null;
        List<DiarioBordo> diarios = DiarioBordoModel.listarDiariosBordo();
        for (DiarioBordo d : diarios) {
            if (d.getAeronaveId() != null && d.getAeronaveId().equals("PT-DEL") && 
                d.getNroDiario() != null && d.getNroDiario().equals(1)) {
                encontrado = d;
                break;
            }
        }
        
        assertNotNull(encontrado, "Diário de bordo não encontrado para delete.");
        
        String respostaDelete = request.requisicao(null, "api/diarios-bordo/" + encontrado.getId().toString(), "DELETE");
        System.out.println("Resposta do servidor no DELETE: " + respostaDelete);
        
        DiarioBordo checar = DiarioBordoModel.buscarDiarioBordo(encontrado.getId().toString());
        
        assertTrue(checar == null, "Diário de bordo ainda existe após DELETE.");
        
        InstrutorModel.excluirInstrutor(instrutor.getCpf());
        AlunoModel.excluirAluno(aluno);
        AeronaveModel.excluirAeronave(aeronave);
    }
}