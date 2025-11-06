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

    @Test
    public void testAtualizacaoHorasAeronave() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-AERO");
        aeronave.setModelo("Cessna 152");
        aeronave.setFabricante("Cessna");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-D");
        aeronave.setHoras_de_voo(1000.0f);
        aeronave.setAtivo(true);
        AeronaveModel.salvarAeronave(aeronave);
        
        Float horasAeronaveAntes = aeronave.getHoras_de_voo();
        
        Aluno aluno = new Aluno();
        aluno.setCpf("87878787878");
        aluno.setCanac(878787);
        aluno.setNome("Aluno Aeronave");
        aluno.setTelefone("67999999999");
        aluno.setEmail("aluno@aero.com");
        aluno.setCurso("Piloto Privado");
        aluno.setHoras_compradas(50.0f);
        aluno.setHoras_voadas(10.0f);
        aluno.setAtivo(true);
        Endereco endAluno = new Endereco();
        endAluno.setCep("79800000");
        endAluno.setCidade("Dourados");
        endAluno.setUF("MS");
        endAluno.setLogradouro("Rua Aeronave");
        endAluno.setNumero("100");
        endAluno.setBairro("Centro");
        endAluno.setComplemento("Apto Aero");
        aluno.setEndereco(endAluno);
        AlunoModel.salvarAluno(aluno);
        
        Instrutor instrutor = new Instrutor();
        instrutor.setCpf("76767676767");
        instrutor.setCanac(767676);
        instrutor.setNome("Instrutor Aeronave");
        instrutor.setTelefone("67988888888");
        instrutor.setEmail("instrutor@aero.com");
        instrutor.setHabilitacao("INVA");
        instrutor.setAtivo(true);
        Usuario usuario = new Usuario();
        usuario.setUsuario("inst_aero");
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
        diario.setAeronaveId("PT-AERO");
        diario.setNroDiario(1);
        diario.setData(new Date());
        diario.setAlunoId("87878787878");
        diario.setInstrutorId("76767676767");
        diario.setFuncaoAluno("I1");
        diario.setFuncaoInstrutor("V1");
        diario.setHoraAeronave(1005.0f);
        diario.setDataDecolagem(new Date(System.currentTimeMillis() - 7200000));
        diario.setDataPouso(new Date(System.currentTimeMillis() - 3600000));
        diario.setLocalDecolagem("SBDO");
        diario.setLocalPouso("SBCG");
        diario.setDataCorte(new Date(System.currentTimeMillis() - 3000000));
        diario.setHorasDiu(1.5f);
        diario.setHorasNot(0.5f);
        diario.setHorasVfr(2.0f);
        diario.setHorasIfr(0.0f);
        diario.setHorasIfrC(0.0f);
        diario.setCombustivelUtilizado("50L");
        diario.setCiclos(2);
        diario.setPob(2);
        diario.setCarga("50kg");
        diario.setNat("TN");
        diario.setOcorrencias("Teste de horas aeronave");
        
        Request request = new Request();
        String retorno = request.requisicao(diario, "api/diarios-bordo", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        DiarioBordo encontrado = null;
        List<DiarioBordo> diarios = DiarioBordoModel.listarDiariosBordo();
        for (DiarioBordo d : diarios) {
            if (d.getAeronaveId() != null && d.getAeronaveId().equals("PT-AERO") && 
                d.getNroDiario() != null && d.getNroDiario().equals(1)) {
                encontrado = d;
                break;
            }
        }
        
        Aeronave aeronaveDepois = AeronaveModel.buscarAeronave(aeronave.getMatricula());
        Float horasAeronaveDepois = aeronaveDepois.getHoras_de_voo();
        
        if (encontrado != null) {
            DiarioBordoModel.excluirDiarioBordo(encontrado.getId().toString());
        }
        InstrutorModel.excluirInstrutor(instrutor.getCpf());
        AlunoModel.excluirAluno(aluno);
        AeronaveModel.excluirAeronave(aeronave);
        
        assertTrue(retorno.contains("\"status\":\"sucesso\""), "Status diferente de sucesso: " + retorno);
        assertEquals(horasAeronaveAntes + 4.0f, horasAeronaveDepois, 0.01f, 
                "Horas da aeronave não foram incrementadas corretamente");
    }

    @Test
    public void testListarDiariosBordo() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-LIST");
        aeronave.setModelo("Cessna 152");
        aeronave.setFabricante("Cessna");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-D");
        aeronave.setHoras_de_voo(1000.0f);
        aeronave.setAtivo(true);
        AeronaveModel.salvarAeronave(aeronave);
        
        Aluno aluno = new Aluno();
        aluno.setCpf("65656565656");
        aluno.setCanac(656565);
        aluno.setNome("Aluno Lista");
        aluno.setTelefone("67999999999");
        aluno.setEmail("aluno@list.com");
        aluno.setCurso("Piloto Privado");
        aluno.setHoras_compradas(50.0f);
        aluno.setHoras_voadas(10.0f);
        aluno.setAtivo(true);
        Endereco endAluno = new Endereco();
        endAluno.setCep("79800000");
        endAluno.setCidade("Dourados");
        endAluno.setUF("MS");
        endAluno.setLogradouro("Rua Lista");
        endAluno.setNumero("100");
        endAluno.setBairro("Centro");
        endAluno.setComplemento("Apto List");
        aluno.setEndereco(endAluno);
        AlunoModel.salvarAluno(aluno);
        
        Instrutor instrutor = new Instrutor();
        instrutor.setCpf("54545454545");
        instrutor.setCanac(545454);
        instrutor.setNome("Instrutor Lista");
        instrutor.setTelefone("67988888888");
        instrutor.setEmail("instrutor@list.com");
        instrutor.setHabilitacao("INVA");
        instrutor.setAtivo(true);
        Usuario usuario = new Usuario();
        usuario.setUsuario("inst_list");
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
        diario.setAeronaveId("PT-LIST");
        diario.setNroDiario(1);
        diario.setData(new Date());
        diario.setAlunoId("65656565656");
        diario.setInstrutorId("54545454545");
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
        diario.setOcorrencias("Teste listagem");
        
        Request request = new Request();
        String retorno = request.requisicao(diario, "api/diarios-bordo", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        String respostaLista = request.requisicao(null, "api/diarios-bordo", "GET");
        System.out.println("Resposta GET lista: " + respostaLista);
        
        DiarioBordo encontrado = null;
        List<DiarioBordo> diarios = DiarioBordoModel.listarDiariosBordo();
        for (DiarioBordo d : diarios) {
            if (d.getAeronaveId() != null && d.getAeronaveId().equals("PT-LIST") && 
                d.getNroDiario() != null && d.getNroDiario().equals(1)) {
                encontrado = d;
                break;
            }
        }
        
        if (encontrado != null) {
            DiarioBordoModel.excluirDiarioBordo(encontrado.getId().toString());
        }
        InstrutorModel.excluirInstrutor(instrutor.getCpf());
        AlunoModel.excluirAluno(aluno);
        AeronaveModel.excluirAeronave(aeronave);
        
        assertTrue(respostaLista.contains("PT-LIST"), "Lista não contém o diário criado: " + respostaLista);
        assertTrue(respostaLista.contains("Teste listagem"), "Lista não contém as ocorrências: " + respostaLista);
    }

    @Test
    public void testValidacaoDataPousoAntesDecolagem() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-VAL1");
        aeronave.setModelo("Cessna 152");
        aeronave.setFabricante("Cessna");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-D");
        aeronave.setHoras_de_voo(1000.0f);
        aeronave.setAtivo(true);
        AeronaveModel.salvarAeronave(aeronave);
        
        Aluno aluno = new Aluno();
        aluno.setCpf("11111111111");
        aluno.setCanac(111111);
        aluno.setNome("Aluno Val1");
        aluno.setTelefone("67999999999");
        aluno.setEmail("aluno@val1.com");
        aluno.setCurso("Piloto Privado");
        aluno.setHoras_compradas(50.0f);
        aluno.setHoras_voadas(10.0f);
        aluno.setAtivo(true);
        Endereco endAluno = new Endereco();
        endAluno.setCep("79800000");
        endAluno.setCidade("Dourados");
        endAluno.setUF("MS");
        endAluno.setLogradouro("Rua Val1");
        endAluno.setNumero("100");
        endAluno.setBairro("Centro");
        endAluno.setComplemento("Apto 1");
        aluno.setEndereco(endAluno);
        AlunoModel.salvarAluno(aluno);
        
        Instrutor instrutor = new Instrutor();
        instrutor.setCpf("22222222222");
        instrutor.setCanac(222222);
        instrutor.setNome("Instrutor Val1");
        instrutor.setTelefone("67988888888");
        instrutor.setEmail("instrutor@val1.com");
        instrutor.setHabilitacao("INVA");
        instrutor.setAtivo(true);
        Usuario usuario = new Usuario();
        usuario.setUsuario("inst_val1");
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
        diario.setAeronaveId("PT-VAL1");
        diario.setNroDiario(1);
        diario.setData(new Date());
        diario.setAlunoId("11111111111");
        diario.setInstrutorId("22222222222");
        diario.setFuncaoAluno("I1");
        diario.setFuncaoInstrutor("V1");
        diario.setHoraAeronave(1005.0f);
        diario.setDataDecolagem(new Date(System.currentTimeMillis() - 3600000));
        diario.setDataPouso(new Date(System.currentTimeMillis() - 7200000));
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
        diario.setOcorrencias("Teste validação");
        
        Request request = new Request();
        String retorno = request.requisicao(diario, "api/diarios-bordo", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        InstrutorModel.excluirInstrutor(instrutor.getCpf());
        AlunoModel.excluirAluno(aluno);
        AeronaveModel.excluirAeronave(aeronave);
        
        assertTrue(retorno.contains("error") || retorno.contains("CONFLITO"), 
                "Deveria retornar erro de data de pouso antes da decolagem: " + retorno);
        assertTrue(retorno.contains("decolagem") || retorno.contains("pouso"), 
                "Mensagem de erro não menciona decolagem/pouso: " + retorno);
    }

    @Test
    public void testValidacaoDataCorteAntesPouso() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-VAL2");
        aeronave.setModelo("Cessna 152");
        aeronave.setFabricante("Cessna");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-D");
        aeronave.setHoras_de_voo(1000.0f);
        aeronave.setAtivo(true);
        AeronaveModel.salvarAeronave(aeronave);
        
        Aluno aluno = new Aluno();
        aluno.setCpf("33333333333");
        aluno.setCanac(333333);
        aluno.setNome("Aluno Val2");
        aluno.setTelefone("67999999999");
        aluno.setEmail("aluno@val2.com");
        aluno.setCurso("Piloto Privado");
        aluno.setHoras_compradas(50.0f);
        aluno.setHoras_voadas(10.0f);
        aluno.setAtivo(true);
        Endereco endAluno = new Endereco();
        endAluno.setCep("79800000");
        endAluno.setCidade("Dourados");
        endAluno.setUF("MS");
        endAluno.setLogradouro("Rua Val2");
        endAluno.setNumero("100");
        endAluno.setBairro("Centro");
        endAluno.setComplemento("Apto 2");
        aluno.setEndereco(endAluno);
        AlunoModel.salvarAluno(aluno);
        
        Instrutor instrutor = new Instrutor();
        instrutor.setCpf("44444444444");
        instrutor.setCanac(444444);
        instrutor.setNome("Instrutor Val2");
        instrutor.setTelefone("67988888888");
        instrutor.setEmail("instrutor@val2.com");
        instrutor.setHabilitacao("INVA");
        instrutor.setAtivo(true);
        Usuario usuario = new Usuario();
        usuario.setUsuario("inst_val2");
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
        diario.setAeronaveId("PT-VAL2");
        diario.setNroDiario(1);
        diario.setData(new Date());
        diario.setAlunoId("33333333333");
        diario.setInstrutorId("44444444444");
        diario.setFuncaoAluno("I1");
        diario.setFuncaoInstrutor("V1");
        diario.setHoraAeronave(1005.0f);
        diario.setDataDecolagem(new Date(System.currentTimeMillis() - 7200000));
        diario.setDataPouso(new Date(System.currentTimeMillis() - 3600000));
        diario.setLocalDecolagem("SBDO");
        diario.setLocalPouso("SBCG");
        diario.setDataCorte(new Date(System.currentTimeMillis() - 7200000));
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
        diario.setOcorrencias("Teste validação");
        
        Request request = new Request();
        String retorno = request.requisicao(diario, "api/diarios-bordo", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        InstrutorModel.excluirInstrutor(instrutor.getCpf());
        AlunoModel.excluirAluno(aluno);
        AeronaveModel.excluirAeronave(aeronave);
        
        assertTrue(retorno.contains("error") || retorno.contains("CONFLITO"), 
                "Deveria retornar erro de data de corte antes do pouso: " + retorno);
        assertTrue(retorno.contains("corte") || retorno.contains("pouso"), 
                "Mensagem de erro não menciona corte/pouso: " + retorno);
    }

    @Test
    public void testValidacaoNroDiarioDuplicado() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-VAL3");
        aeronave.setModelo("Cessna 152");
        aeronave.setFabricante("Cessna");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-D");
        aeronave.setHoras_de_voo(1000.0f);
        aeronave.setAtivo(true);
        AeronaveModel.salvarAeronave(aeronave);
        
        Aluno aluno = new Aluno();
        aluno.setCpf("55555555555");
        aluno.setCanac(555555);
        aluno.setNome("Aluno Val3");
        aluno.setTelefone("67999999999");
        aluno.setEmail("aluno@val3.com");
        aluno.setCurso("Piloto Privado");
        aluno.setHoras_compradas(50.0f);
        aluno.setHoras_voadas(10.0f);
        aluno.setAtivo(true);
        Endereco endAluno = new Endereco();
        endAluno.setCep("79800000");
        endAluno.setCidade("Dourados");
        endAluno.setUF("MS");
        endAluno.setLogradouro("Rua Val3");
        endAluno.setNumero("100");
        endAluno.setBairro("Centro");
        endAluno.setComplemento("Apto 3");
        aluno.setEndereco(endAluno);
        AlunoModel.salvarAluno(aluno);
        
        Instrutor instrutor = new Instrutor();
        instrutor.setCpf("66666666666");
        instrutor.setCanac(666666);
        instrutor.setNome("Instrutor Val3");
        instrutor.setTelefone("67988888888");
        instrutor.setEmail("instrutor@val3.com");
        instrutor.setHabilitacao("INVA");
        instrutor.setAtivo(true);
        Usuario usuario = new Usuario();
        usuario.setUsuario("inst_val3");
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
        
        DiarioBordo diario1 = new DiarioBordo();
        diario1.setAeronaveId("PT-VAL3");
        diario1.setNroDiario(1);
        diario1.setData(new Date());
        diario1.setAlunoId("55555555555");
        diario1.setInstrutorId("66666666666");
        diario1.setFuncaoAluno("I1");
        diario1.setFuncaoInstrutor("V1");
        diario1.setHoraAeronave(1005.0f);
        diario1.setDataDecolagem(new Date(System.currentTimeMillis() - 7200000));
        diario1.setDataPouso(new Date(System.currentTimeMillis() - 3600000));
        diario1.setLocalDecolagem("SBDO");
        diario1.setLocalPouso("SBCG");
        diario1.setDataCorte(new Date(System.currentTimeMillis() - 3000000));
        diario1.setHorasDiu(1.0f);
        diario1.setHorasNot(0.0f);
        diario1.setHorasVfr(1.0f);
        diario1.setHorasIfr(0.0f);
        diario1.setHorasIfrC(0.0f);
        diario1.setCombustivelUtilizado("50L");
        diario1.setCiclos(2);
        diario1.setPob(2);
        diario1.setCarga("50kg");
        diario1.setNat("TN");
        diario1.setOcorrencias("Primeiro diário");
        
        Request request = new Request();
        String retorno1 = request.requisicao(diario1, "api/diarios-bordo", "POST");
        System.out.println("Resposta do servidor: " + retorno1);
        
        DiarioBordo encontrado = null;
        List<DiarioBordo> diarios = DiarioBordoModel.listarDiariosBordo();
        for (DiarioBordo d : diarios) {
            if (d.getAeronaveId() != null && d.getAeronaveId().equals("PT-VAL3") && 
                d.getNroDiario() != null && d.getNroDiario().equals(1)) {
                encontrado = d;
                break;
            }
        }
        
        DiarioBordo diario2 = new DiarioBordo();
        diario2.setAeronaveId("PT-VAL3");
        diario2.setNroDiario(1);
        diario2.setData(new Date());
        diario2.setAlunoId("55555555555");
        diario2.setInstrutorId("66666666666");
        diario2.setFuncaoAluno("I1");
        diario2.setFuncaoInstrutor("V1");
        diario2.setHoraAeronave(1006.0f);
        diario2.setDataDecolagem(new Date(System.currentTimeMillis() - 7200000));
        diario2.setDataPouso(new Date(System.currentTimeMillis() - 3600000));
        diario2.setLocalDecolagem("SBCG");
        diario2.setLocalPouso("SBDO");
        diario2.setDataCorte(new Date(System.currentTimeMillis() - 3000000));
        diario2.setHorasDiu(1.0f);
        diario2.setHorasNot(0.0f);
        diario2.setHorasVfr(1.0f);
        diario2.setHorasIfr(0.0f);
        diario2.setHorasIfrC(0.0f);
        diario2.setCombustivelUtilizado("50L");
        diario2.setCiclos(2);
        diario2.setPob(2);
        diario2.setCarga("50kg");
        diario2.setNat("TN");
        diario2.setOcorrencias("Segundo diário");
        
        String retorno2 = request.requisicao(diario2, "api/diarios-bordo", "POST");
        System.out.println("Resposta do servidor (duplicado): " + retorno2);
        
        if (encontrado != null) {
            DiarioBordoModel.excluirDiarioBordo(encontrado.getId().toString());
        }
        InstrutorModel.excluirInstrutor(instrutor.getCpf());
        AlunoModel.excluirAluno(aluno);
        AeronaveModel.excluirAeronave(aeronave);
        
        assertTrue(retorno2.contains("error") || retorno2.contains("CONFLITO"), 
                "Deveria retornar erro de número de diário duplicado: " + retorno2);
        assertTrue(retorno2.contains("diário") || retorno2.contains("aeronave"), 
                "Mensagem de erro não menciona conflito de número: " + retorno2);
    }

    @Test
    public void testValidacaoSemHoras() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-VAL4");
        aeronave.setModelo("Cessna 152");
        aeronave.setFabricante("Cessna");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-D");
        aeronave.setHoras_de_voo(1000.0f);
        aeronave.setAtivo(true);
        AeronaveModel.salvarAeronave(aeronave);
        
        Aluno aluno = new Aluno();
        aluno.setCpf("77777777777");
        aluno.setCanac(777777);
        aluno.setNome("Aluno Val4");
        aluno.setTelefone("67999999999");
        aluno.setEmail("aluno@val4.com");
        aluno.setCurso("Piloto Privado");
        aluno.setHoras_compradas(50.0f);
        aluno.setHoras_voadas(10.0f);
        aluno.setAtivo(true);
        Endereco endAluno = new Endereco();
        endAluno.setCep("79800000");
        endAluno.setCidade("Dourados");
        endAluno.setUF("MS");
        endAluno.setLogradouro("Rua Val4");
        endAluno.setNumero("100");
        endAluno.setBairro("Centro");
        endAluno.setComplemento("Apto 4");
        aluno.setEndereco(endAluno);
        AlunoModel.salvarAluno(aluno);
        
        Instrutor instrutor = new Instrutor();
        instrutor.setCpf("88888888888");
        instrutor.setCanac(888888);
        instrutor.setNome("Instrutor Val4");
        instrutor.setTelefone("67988888888");
        instrutor.setEmail("instrutor@val4.com");
        instrutor.setHabilitacao("INVA");
        instrutor.setAtivo(true);
        Usuario usuario = new Usuario();
        usuario.setUsuario("inst_val4");
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
        diario.setAeronaveId("PT-VAL4");
        diario.setNroDiario(1);
        diario.setData(new Date());
        diario.setAlunoId("77777777777");
        diario.setInstrutorId("88888888888");
        diario.setFuncaoAluno("I1");
        diario.setFuncaoInstrutor("V1");
        diario.setHoraAeronave(1005.0f);
        diario.setDataDecolagem(new Date(System.currentTimeMillis() - 7200000));
        diario.setDataPouso(new Date(System.currentTimeMillis() - 3600000));
        diario.setLocalDecolagem("SBDO");
        diario.setLocalPouso("SBCG");
        diario.setDataCorte(new Date(System.currentTimeMillis() - 3000000));
        diario.setCombustivelUtilizado("50L");
        diario.setCiclos(2);
        diario.setPob(2);
        diario.setCarga("50kg");
        diario.setNat("TN");
        diario.setOcorrencias("Teste sem horas");
        
        Request request = new Request();
        String retorno = request.requisicao(diario, "api/diarios-bordo", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        InstrutorModel.excluirInstrutor(instrutor.getCpf());
        AlunoModel.excluirAluno(aluno);
        AeronaveModel.excluirAeronave(aeronave);
        
        assertTrue(retorno.contains("error") || retorno.contains("CONFLITO"), 
                "Deveria retornar erro quando nenhuma hora é preenchida: " + retorno);
        assertTrue(retorno.contains("hora") || retorno.contains("preenchida"), 
                "Mensagem de erro não menciona necessidade de preencher horas: " + retorno);
    }

    @Test
public void testAtualizacaoHorasAluno() {
    Aeronave aeronave = new Aeronave();
    aeronave.setMatricula("PT-HRS");
    aeronave.setModelo("Cessna 152");
    aeronave.setFabricante("Cessna");
    aeronave.setHabilitacao("MNTE");
    aeronave.setTipo_de_voo("VFR-D");
    aeronave.setHoras_de_voo(1000.0f);
    aeronave.setAtivo(true);
    AeronaveModel.salvarAeronave(aeronave);
    
    Aluno aluno = new Aluno();
    aluno.setCpf("99999999999");
    aluno.setCanac(999999);
    aluno.setNome("Aluno Horas");
    aluno.setTelefone("67999999999");
    aluno.setEmail("aluno@horas.com");
    aluno.setCurso("Piloto Privado");
    aluno.setHoras_compradas(50.0f);
    aluno.setHoras_voadas(10.0f);
    aluno.setAtivo(true);
    Endereco endAluno = new Endereco();
    endAluno.setCep("79800000");
    endAluno.setCidade("Dourados");
    endAluno.setUF("MS");
    endAluno.setLogradouro("Rua Horas");
    endAluno.setNumero("100");
    endAluno.setBairro("Centro");
    endAluno.setComplemento("Apto Horas");
    aluno.setEndereco(endAluno);
    AlunoModel.salvarAluno(aluno);
    
    Instrutor instrutor = new Instrutor();
    instrutor.setCpf("98989898989");
    instrutor.setCanac(989898);
    instrutor.setNome("Instrutor Horas");
    instrutor.setTelefone("67988888888");
    instrutor.setEmail("instrutor@horas.com");
    instrutor.setHabilitacao("INVA");
    instrutor.setAtivo(true);
    Usuario usuario = new Usuario();
    usuario.setUsuario("inst_horas");
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
    
    Float horasCompradasAntes = aluno.getHoras_compradas();
    Float horasVoadasAntes = aluno.getHoras_voadas();
    
    DiarioBordo diario = new DiarioBordo();
    diario.setAeronaveId("PT-HRS");
    diario.setNroDiario(1);
    diario.setData(new Date());
    diario.setAlunoId("99999999999");
    diario.setInstrutorId("98989898989");
    diario.setFuncaoAluno("I1");
    diario.setFuncaoInstrutor("V1");
    diario.setHoraAeronave(1005.0f);
    diario.setDataDecolagem(new Date(System.currentTimeMillis() - 7200000));
    diario.setDataPouso(new Date(System.currentTimeMillis() - 3600000));
    diario.setLocalDecolagem("SBDO");
    diario.setLocalPouso("SBCG");
    diario.setDataCorte(new Date(System.currentTimeMillis() - 3000000));
    diario.setHorasDiu(2.0f);
    diario.setHorasNot(1.0f);
    diario.setHorasVfr(3.0f);
    diario.setHorasIfr(0.0f);
    diario.setHorasIfrC(0.0f);
    diario.setCombustivelUtilizado("50L");
    diario.setCiclos(2);
    diario.setPob(2);
    diario.setCarga("50kg");
    diario.setNat("TN");
    diario.setOcorrencias("Teste de horas");
    
    Request request = new Request();
    String retorno = request.requisicao(diario, "api/diarios-bordo", "POST");
    System.out.println("Resposta do servidor: " + retorno);
    
    DiarioBordo encontrado = null;
    List<DiarioBordo> diarios = DiarioBordoModel.listarDiariosBordo();
    for (DiarioBordo d : diarios) {
        if (d.getAeronaveId() != null && d.getAeronaveId().equals("PT-HRS") && 
            d.getNroDiario() != null && d.getNroDiario().equals(1)) {
            encontrado = d;
            break;
        }
    }
    
    Aluno alunoDepois = AlunoModel.buscarAluno(aluno.getCpf());
    Float horasCompradasDepois = alunoDepois.getHoras_compradas();
    Float horasVoadasDepois = alunoDepois.getHoras_voadas();
    
    if (encontrado != null) {
        DiarioBordoModel.excluirDiarioBordo(encontrado.getId().toString());
    }
    InstrutorModel.excluirInstrutor(instrutor.getCpf());
    AlunoModel.excluirAluno(aluno);
    AeronaveModel.excluirAeronave(aeronave);
    
    assertTrue(retorno.contains("\"status\":\"sucesso\""), "Status diferente de sucesso: " + retorno);
    assertEquals(horasCompradasAntes - 6.0f, horasCompradasDepois, 0.01f, 
            "Horas compradas não foram descontadas corretamente");
    assertEquals(horasVoadasAntes + 6.0f, horasVoadasDepois, 0.01f, 
            "Horas voadas não foram incrementadas corretamente");
}
}   