package com.sisgea.sisgea.TesteAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Models.AeronaveModel;
import com.sisgea.BancoDados.Models.AgendamentoModel;
import com.sisgea.BancoDados.Models.AlunoModel;
import com.sisgea.BancoDados.Models.InstrutorModel;
import com.sisgea.BancoDados.Models.ManutencaoModel;
import com.sisgea.Entidades.Aeronave;
import com.sisgea.Entidades.Agendamento;
import com.sisgea.Entidades.Aluno;
import com.sisgea.Entidades.Endereco;
import com.sisgea.Entidades.Instrutor;
import com.sisgea.Entidades.Manutencao;
import com.sisgea.Entidades.Usuario;

public class TestAgendamento {

    @Test
    public void testCreate() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-AGD");
        aeronave.setModelo("Cessna 152");
        aeronave.setFabricante("Cessna");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-N");
        aeronave.setHoras_de_voo(1000.0f);
        aeronave.setAtivo(true);
        AeronaveModel.salvarAeronave(aeronave);

        Aluno aluno = new Aluno();
        aluno.setCpf("11111111111");
        aluno.setCanac(111111);
        aluno.setNome("Aluno Agendamento");
        aluno.setTelefone("67999999999");
        aluno.setEmail("aluno@agd.com");
        aluno.setCurso("Piloto Privado");
        aluno.setHoras_compradas(50.0f);
        aluno.setHoras_voadas(10.0f);
        aluno.setAtivo(true);
        Endereco endAluno = new Endereco();
        endAluno.setCep("79800000");
        endAluno.setCidade("Dourados");
        endAluno.setUF("MS");
        endAluno.setLogradouro("Rua Aluno");
        endAluno.setNumero("100");
        endAluno.setBairro("Centro");
        endAluno.setComplemento("Apto 1");
        aluno.setEndereco(endAluno);
        AlunoModel.salvarAluno(aluno);

        Instrutor instrutor = new Instrutor();
        instrutor.setCpf("22222222222");
        instrutor.setCanac(222222);
        instrutor.setNome("Instrutor Agendamento");
        instrutor.setTelefone("67988888888");
        instrutor.setEmail("instrutor@agd.com");
        instrutor.setHabilitacao("INVA");
        instrutor.setAtivo(true);
        Usuario usuario = new Usuario();
        usuario.setUsuario("inst_agd");
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

        Agendamento agendamento = new Agendamento();
        agendamento.setAeronave(aeronave);
        agendamento.setAluno(aluno);
        agendamento.setInstrutor(instrutor);
        agendamento.setPartida("SBDO");
        agendamento.setDestino("SBCG");
        agendamento.setTipo_voo("VFR-N");
        agendamento.setStatus("Agendado");
        agendamento.setHorario_partida(new Date(System.currentTimeMillis() + 86400000));
        agendamento.setHorario_retorno(new Date(System.currentTimeMillis() + 90000000));

        Request request = new Request();
        String retorno = request.requisicao(agendamento, "api/agendamentos", "POST");
        System.out.println("Resposta do servidor: " + retorno);

        Agendamento encontrado = null;
        List<Agendamento> agendamentos = AgendamentoModel.listarAgendamentos();
        for (Agendamento a : agendamentos) {
            if (a.getPartida() != null && a.getPartida().equals("SBDO") &&
                    a.getDestino() != null && a.getDestino().equals("SBCG")) {
                encontrado = a;
                AgendamentoModel.excluirAgendamento(a.getId().toString());
            }
        }

        InstrutorModel.excluirInstrutor(instrutor.getCpf());
        AlunoModel.excluirAluno(aluno);
        AeronaveModel.excluirAeronave(aeronave);

        assertTrue(retorno.contains("\"status\":\"sucesso\""), "Status diferente de sucesso: " + retorno);
        assertNotNull(encontrado, "Agendamento não encontrado após criação.");
    }

    @Test
    public void testRead() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-RED");
        aeronave.setModelo("Piper PA-28");
        aeronave.setFabricante("Piper");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-N");
        aeronave.setHoras_de_voo(500.0f);
        aeronave.setAtivo(true);
        AeronaveModel.salvarAeronave(aeronave);

        Aluno aluno = new Aluno();
        aluno.setCpf("33333333333");
        aluno.setCanac(333333);
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
        instrutor.setCpf("44444444444");
        instrutor.setCanac(444444);
        instrutor.setNome("Instrutor Leitura");
        instrutor.setTelefone("67966666666");
        instrutor.setEmail("instrutor@read.com");
        instrutor.setHabilitacao("PLA");
        instrutor.setAtivo(true);
        Usuario usuario = new Usuario();
        usuario.setUsuario("inst_read");
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

        Agendamento agendamento = new Agendamento();
        agendamento.setAeronave(aeronave);
        agendamento.setAluno(aluno);
        agendamento.setInstrutor(instrutor);
        agendamento.setPartida("SBCG");
        agendamento.setDestino("SBDO");
        agendamento.setTipo_voo("VFR-N");
        agendamento.setStatus("Agendado");
        agendamento.setHorario_partida(new Date(System.currentTimeMillis() + 86400000));
        agendamento.setHorario_retorno(new Date(System.currentTimeMillis() + 90000000));

        Request request = new Request();
        String retorno = request.requisicao(agendamento, "api/agendamentos", "POST");
        System.out.println("Resposta do servidor: " + retorno);

        Agendamento encontrado = null;
        List<Agendamento> agendamentos = AgendamentoModel.listarAgendamentos();
        for (Agendamento a : agendamentos) {
            if (a.getPartida() != null && a.getPartida().equals("SBCG") &&
                    a.getDestino() != null && a.getDestino().equals("SBDO")) {
                encontrado = a;
                break;
            }
        }

        assertNotNull(encontrado, "Agendamento não encontrado na lista após criação.");
        assertEquals("SBCG", encontrado.getPartida(), "Partida incorreta: " + encontrado.getPartida());

        String respostaGet = request.requisicao(null, "api/agendamentos/" + encontrado.getId().toString(), "GET");
        assertTrue(respostaGet.contains("SBCG"), "GET não retornou o agendamento correto: " + respostaGet);

        AgendamentoModel.excluirAgendamento(encontrado.getId().toString());
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
        aeronave.setTipo_de_voo("VFR-N");
        aeronave.setHoras_de_voo(800.0f);
        aeronave.setAtivo(true);
        AeronaveModel.salvarAeronave(aeronave);

        Aluno aluno = new Aluno();
        aluno.setCpf("55555555555");
        aluno.setCanac(555555);
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
        instrutor.setCpf("66666666666");
        instrutor.setCanac(666666);
        instrutor.setNome("Instrutor Update");
        instrutor.setTelefone("67944444444");
        instrutor.setEmail("instrutor@upd.com");
        instrutor.setHabilitacao("PC");
        instrutor.setAtivo(true);
        Usuario usuario = new Usuario();
        usuario.setUsuario("inst_upd");
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

        Agendamento agendamento = new Agendamento();
        agendamento.setAeronave(aeronave);
        agendamento.setAluno(aluno);
        agendamento.setInstrutor(instrutor);
        agendamento.setPartida("SBSP");
        agendamento.setDestino("SBRJ");
        agendamento.setTipo_voo("IFR");
        agendamento.setStatus("Agendado");
        agendamento.setHorario_partida(new Date(System.currentTimeMillis() + 86400000));
        agendamento.setHorario_retorno(new Date(System.currentTimeMillis() + 90000000));

        Request request = new Request();
        String retorno = request.requisicao(agendamento, "api/agendamentos", "POST");
        System.out.println("Resposta do servidor: " + retorno);

        Agendamento antigo = null;
        List<Agendamento> agendamentos = AgendamentoModel.listarAgendamentos();
        for (Agendamento a : agendamentos) {
            if (a.getPartida() != null && a.getPartida().equals("SBSP") &&
                    a.getDestino() != null && a.getDestino().equals("SBRJ")) {
                antigo = a;
                break;
            }
        }

        assertNotNull(antigo, "Agendamento não encontrado para update.");

        antigo.setStatus("Confirmado");
        antigo.setPartida("SBDO");
        String retornoUpdate = request.requisicao(antigo, "api/agendamentos/" + antigo.getId().toString(), "PUT");
        System.out.println("Resposta do servidor no UPDATE: " + retornoUpdate);

        assertTrue(retornoUpdate.contains("\"status\":\"sucesso\""), "Falha no update: " + retornoUpdate);

        Agendamento novo = null;
        agendamentos = AgendamentoModel.listarAgendamentos();
        for (Agendamento a : agendamentos) {
            if (a.getId().equals(antigo.getId())) {
                novo = a;
                break;
            }
        }

        assertNotNull(novo, "Agendamento não encontrado após update.");
        assertEquals("Confirmado", novo.getStatus(), "Status não foi atualizado: " + novo.getStatus());
        assertEquals("SBDO", novo.getPartida(), "Partida não foi atualizada: " + novo.getPartida());

        AgendamentoModel.excluirAgendamento(novo.getId().toString());
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
        aluno.setCpf("77777777777");
        aluno.setCanac(777777);
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
        instrutor.setCpf("88888888888");
        instrutor.setCanac(888888);
        instrutor.setNome("Instrutor Delete");
        instrutor.setTelefone("67922222222");
        instrutor.setEmail("instrutor@del.com");
        instrutor.setHabilitacao("INVA");
        instrutor.setAtivo(true);
        Usuario usuario = new Usuario();
        usuario.setUsuario("inst_del");
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

        Agendamento agendamento = new Agendamento();
        agendamento.setAeronave(aeronave);
        agendamento.setAluno(aluno);
        agendamento.setInstrutor(instrutor);
        agendamento.setPartida("SBGR");
        agendamento.setDestino("SBKP");
        agendamento.setTipo_voo("IFR");
        agendamento.setStatus("Agendado");
        agendamento.setHorario_partida(new Date(System.currentTimeMillis() + 86400000));
        agendamento.setHorario_retorno(new Date(System.currentTimeMillis() + 90000000));

        Request request = new Request();
        String retorno = request.requisicao(agendamento, "api/agendamentos", "POST");
        System.out.println("Resposta do servidor: " + retorno);

        Agendamento encontrado = null;
        List<Agendamento> agendamentos = AgendamentoModel.listarAgendamentos();
        for (Agendamento a : agendamentos) {
            if (a.getPartida() != null && a.getPartida().equals("SBGR") &&
                    a.getDestino() != null && a.getDestino().equals("SBKP")) {
                encontrado = a;
                break;
            }
        }

        assertNotNull(encontrado, "Agendamento não encontrado para delete.");

        String respostaDelete = request.requisicao(null, "api/agendamentos/" + encontrado.getId().toString(), "DELETE");
        System.out.println("Resposta do servidor no DELETE: " + respostaDelete);

        Agendamento checar = AgendamentoModel.buscarAgendamento(encontrado.getId().toString());

        assertTrue(checar == null, "Agendamento ainda existe após DELETE.");

        InstrutorModel.excluirInstrutor(instrutor.getCpf());
        AlunoModel.excluirAluno(aluno);
        AeronaveModel.excluirAeronave(aeronave);
    }

    @Test
    public void testHorarioPassado() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-PAS");
        aeronave.setModelo("Cessna 152");
        aeronave.setFabricante("Cessna");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-N");
        aeronave.setHoras_de_voo(1000.0f);
        aeronave.setAtivo(true);
        AeronaveModel.salvarAeronave(aeronave);

        Aluno aluno = new Aluno();
        aluno.setCpf("99999999999");
        aluno.setCanac(999999);
        aluno.setNome("Aluno Passado");
        aluno.setTelefone("67911111111");
        aluno.setEmail("aluno@passado.com");
        aluno.setCurso("Piloto Privado");
        aluno.setHoras_compradas(50.0f);
        aluno.setHoras_voadas(10.0f);
        aluno.setAtivo(true);
        Endereco endAluno = new Endereco();
        endAluno.setCep("79800000");
        endAluno.setCidade("Dourados");
        endAluno.setUF("MS");
        endAluno.setLogradouro("Rua Passado");
        endAluno.setNumero("900");
        endAluno.setBairro("Centro");
        endAluno.setComplemento("Casa 4");
        aluno.setEndereco(endAluno);
        AlunoModel.salvarAluno(aluno);

        Instrutor instrutor = new Instrutor();
        instrutor.setCpf("99988877766");
        instrutor.setCanac(998887);
        instrutor.setNome("Instrutor Passado");
        instrutor.setTelefone("67910101010");
        instrutor.setEmail("instrutor@passado.com");
        instrutor.setHabilitacao("INVA");
        instrutor.setAtivo(true);
        Usuario usuario = new Usuario();
        usuario.setUsuario("inst_passado");
        usuario.setSenha("123");
        usuario.setPermissao(0);
        instrutor.setUsuario(usuario);
        Endereco endInst = new Endereco();
        endInst.setCep("79800000");
        endInst.setCidade("Dourados");
        endInst.setUF("MS");
        endInst.setLogradouro("Rua Instrutor Passado");
        endInst.setNumero("901");
        endInst.setBairro("Centro");
        endInst.setComplemento("Apto 5");
        instrutor.setEndereco(endInst);
        InstrutorModel.salvarInstrutor(instrutor);

        Agendamento agendamento = new Agendamento();
        agendamento.setAeronave(aeronave);
        agendamento.setAluno(aluno);
        agendamento.setInstrutor(instrutor);
        agendamento.setPartida("SBDO");
        agendamento.setDestino("SBCG");
        agendamento.setTipo_voo("VFR-N");
        agendamento.setStatus("Agendado");
        agendamento.setHorario_partida(new Date(System.currentTimeMillis() - 86400000));
        agendamento.setHorario_retorno(new Date(System.currentTimeMillis() - 82800000));

        Request request = new Request();
        String retorno = request.requisicao(agendamento, "api/agendamentos", "POST");
        System.out.println("Resposta do servidor: " + retorno);

        InstrutorModel.excluirInstrutor(instrutor.getCpf());
        AlunoModel.excluirAluno(aluno);
        AeronaveModel.excluirAeronave(aeronave);

        assertTrue(retorno.contains("horário de partida não pode ser no passado") || retorno.contains("error"),
                "Deveria rejeitar agendamento com horário no passado: " + retorno);
    }

    @Test
    public void testConflitoAeronave() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-CNF");
        aeronave.setModelo("Cessna 152");
        aeronave.setFabricante("Cessna");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-N");
        aeronave.setHoras_de_voo(1000.0f);
        aeronave.setAtivo(true);
        AeronaveModel.salvarAeronave(aeronave);

        Aluno aluno1 = new Aluno();
        aluno1.setCpf("11122233344");
        aluno1.setCanac(112233);
        aluno1.setNome("Aluno Conflito 1");
        aluno1.setTelefone("67912121212");
        aluno1.setEmail("aluno1@conflito.com");
        aluno1.setCurso("Piloto Privado");
        aluno1.setHoras_compradas(50.0f);
        aluno1.setHoras_voadas(10.0f);
        aluno1.setAtivo(true);
        Endereco endAluno1 = new Endereco();
        endAluno1.setCep("79800000");
        endAluno1.setCidade("Dourados");
        endAluno1.setUF("MS");
        endAluno1.setLogradouro("Rua Conflito 1");
        endAluno1.setNumero("1001");
        endAluno1.setBairro("Centro");
        endAluno1.setComplemento("Casa 1");
        aluno1.setEndereco(endAluno1);
        AlunoModel.salvarAluno(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setCpf("55566677788");
        aluno2.setCanac(556677);
        aluno2.setNome("Aluno Conflito 2");
        aluno2.setTelefone("67913131313");
        aluno2.setEmail("aluno2@conflito.com");
        aluno2.setCurso("Piloto Privado");
        aluno2.setHoras_compradas(50.0f);
        aluno2.setHoras_voadas(10.0f);
        aluno2.setAtivo(true);
        Endereco endAluno2 = new Endereco();
        endAluno2.setCep("79800000");
        endAluno2.setCidade("Dourados");
        endAluno2.setUF("MS");
        endAluno2.setLogradouro("Rua Conflito 2");
        endAluno2.setNumero("1002");
        endAluno2.setBairro("Centro");
        endAluno2.setComplemento("Casa 2");
        aluno2.setEndereco(endAluno2);
        AlunoModel.salvarAluno(aluno2);

        Instrutor instrutor1 = new Instrutor();
        instrutor1.setCpf("11100011100");
        instrutor1.setCanac(111000);
        instrutor1.setNome("Instrutor Conflito 1");
        instrutor1.setTelefone("67914141414");
        instrutor1.setEmail("instrutor1@conflito.com");
        instrutor1.setHabilitacao("INVA");
        instrutor1.setAtivo(true);
        Usuario usuario1 = new Usuario();
        usuario1.setUsuario("inst_conf1");
        usuario1.setSenha("123");
        usuario1.setPermissao(0);
        instrutor1.setUsuario(usuario1);
        Endereco endInst1 = new Endereco();
        endInst1.setCep("79800000");
        endInst1.setCidade("Dourados");
        endInst1.setUF("MS");
        endInst1.setLogradouro("Rua Instrutor Conflito 1");
        endInst1.setNumero("1003");
        endInst1.setBairro("Centro");
        endInst1.setComplemento("Apto 1");
        instrutor1.setEndereco(endInst1);
        InstrutorModel.salvarInstrutor(instrutor1);

        Instrutor instrutor2 = new Instrutor();
        instrutor2.setCpf("22200022200");
        instrutor2.setCanac(222000);
        instrutor2.setNome("Instrutor Conflito 2");
        instrutor2.setTelefone("67915151515");
        instrutor2.setEmail("instrutor2@conflito.com");
        instrutor2.setHabilitacao("INVA");
        instrutor2.setAtivo(true);
        Usuario usuario2 = new Usuario();
        usuario2.setUsuario("inst_conf2");
        usuario2.setSenha("123");
        usuario2.setPermissao(0);
        instrutor2.setUsuario(usuario2);
        Endereco endInst2 = new Endereco();
        endInst2.setCep("79800000");
        endInst2.setCidade("Dourados");
        endInst2.setUF("MS");
        endInst2.setLogradouro("Rua Instrutor Conflito 2");
        endInst2.setNumero("1004");
        endInst2.setBairro("Centro");
        endInst2.setComplemento("Apto 2");
        instrutor2.setEndereco(endInst2);
        InstrutorModel.salvarInstrutor(instrutor2);

        Agendamento agendamento1 = new Agendamento();
        agendamento1.setAeronave(aeronave);
        agendamento1.setAluno(aluno1);
        agendamento1.setInstrutor(instrutor1);
        agendamento1.setPartida("SBDO");
        agendamento1.setDestino("SBCG");
        agendamento1.setTipo_voo("VFR-N");
        agendamento1.setStatus("Agendado");
        agendamento1.setHorario_partida(new Date(System.currentTimeMillis() + 86400000));
        agendamento1.setHorario_retorno(new Date(System.currentTimeMillis() + 90000000));

        Request request = new Request();
        String retorno1 = request.requisicao(agendamento1, "api/agendamentos", "POST");
        System.out.println("Primeiro agendamento: " + retorno1);

        Agendamento agendamento2 = new Agendamento();
        agendamento2.setAeronave(aeronave);
        agendamento2.setAluno(aluno2);
        agendamento2.setInstrutor(instrutor2);
        agendamento2.setPartida("SBCG");
        agendamento2.setDestino("SBDO");
        agendamento2.setTipo_voo("VFR-N");
        agendamento2.setStatus("Agendado");
        agendamento2.setHorario_partida(new Date(System.currentTimeMillis() + 87000000));
        agendamento2.setHorario_retorno(new Date(System.currentTimeMillis() + 91000000));

        String retorno2 = request.requisicao(agendamento2, "api/agendamentos", "POST");
        System.out.println("Segundo agendamento (conflito): " + retorno2);

        List<Agendamento> agendamentos = AgendamentoModel.listarAgendamentos();
        for (Agendamento a : agendamentos) {
            if (a.getAeronave() != null && a.getAeronave().getMatricula().equals("PT-CNF")) {
                AgendamentoModel.excluirAgendamento(a.getId().toString());
            }
        }

        InstrutorModel.excluirInstrutor(instrutor1.getCpf());
        InstrutorModel.excluirInstrutor(instrutor2.getCpf());
        AlunoModel.excluirAluno(aluno1);
        AlunoModel.excluirAluno(aluno2);
        AeronaveModel.excluirAeronave(aeronave);

        assertTrue(retorno1.contains("\"status\":\"sucesso\""),
                "Primeiro agendamento deveria ter sucesso: " + retorno1);
        assertTrue(retorno2.contains("mesma aeronave") || retorno2.contains("error") || retorno2.contains("CONFLITO"),
                "Segundo agendamento deveria gerar conflito de aeronave: " + retorno2);
    }

    @Test
    public void testAlunoSemHoras() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-SHR");
        aeronave.setModelo("Cessna 152");
        aeronave.setFabricante("Cessna");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-N");
        aeronave.setHoras_de_voo(1000.0f);
        aeronave.setAtivo(true);
        AeronaveModel.salvarAeronave(aeronave);

        Aluno aluno = new Aluno();
        aluno.setCpf("00000000000");
        aluno.setCanac(100);
        aluno.setNome("Aluno Sem Horas");
        aluno.setTelefone("67916161616");
        aluno.setEmail("aluno@semhoras.com");
        aluno.setCurso("Piloto Privado");
        aluno.setHoras_compradas(0.0f);
        aluno.setHoras_voadas(0.0f);
        aluno.setAtivo(true);
        Endereco endAluno = new Endereco();
        endAluno.setCep("79800000");
        endAluno.setCidade("Dourados");
        endAluno.setUF("MS");
        endAluno.setLogradouro("Rua Sem Horas");
        endAluno.setNumero("1005");
        endAluno.setBairro("Centro");
        endAluno.setComplemento("Casa 5");
        aluno.setEndereco(endAluno);
        AlunoModel.salvarAluno(aluno);

        Instrutor instrutor = new Instrutor();
        instrutor.setCpf("33300033300");
        instrutor.setCanac(333000);
        instrutor.setNome("Instrutor Sem Horas");
        instrutor.setTelefone("67917171717");
        instrutor.setEmail("instrutor@semhoras.com");
        instrutor.setHabilitacao("INVA");
        instrutor.setAtivo(true);
        Usuario usuario = new Usuario();
        usuario.setUsuario("inst_semhoras");
        usuario.setSenha("123");
        usuario.setPermissao(0);
        instrutor.setUsuario(usuario);
        Endereco endInst = new Endereco();
        endInst.setCep("79800000");
        endInst.setCidade("Dourados");
        endInst.setUF("MS");
        endInst.setLogradouro("Rua Instrutor Sem Horas");
        endInst.setNumero("1006");
        endInst.setBairro("Centro");
        endInst.setComplemento("Apto 6");
        instrutor.setEndereco(endInst);
        InstrutorModel.salvarInstrutor(instrutor);

        Agendamento agendamento = new Agendamento();
        agendamento.setAeronave(aeronave);
        agendamento.setAluno(aluno);
        agendamento.setInstrutor(instrutor);
        agendamento.setPartida("SBDO");
        agendamento.setDestino("SBCG");
        agendamento.setTipo_voo("VFR-N");
        agendamento.setStatus("Agendado");
        agendamento.setHorario_partida(new Date(System.currentTimeMillis() + 86400000));
        agendamento.setHorario_retorno(new Date(System.currentTimeMillis() + 90000000));

        Request request = new Request();
        String retorno = request.requisicao(agendamento, "api/agendamentos", "POST");
        System.out.println("Resposta do servidor: " + retorno);

        InstrutorModel.excluirInstrutor(instrutor.getCpf());
        AlunoModel.excluirAluno(aluno);
        AeronaveModel.excluirAeronave(aeronave);

        assertTrue(retorno.contains("não possui horas") || retorno.contains("error") || retorno.contains("CONFLITO"),
                "Deveria rejeitar agendamento de aluno sem horas: " + retorno);
    }

    @Test
    public void testAeronaveEmManutencao() {
        Aeronave aeronave = new Aeronave();
        aeronave.setMatricula("PT-MAN");
        aeronave.setModelo("Cessna 152");
        aeronave.setFabricante("Cessna");
        aeronave.setHabilitacao("MNTE");
        aeronave.setTipo_de_voo("VFR-N");
        aeronave.setHoras_de_voo(1000.0f);
        aeronave.setAtivo(true);
        AeronaveModel.salvarAeronave(aeronave);

        Manutencao manutencao = new Manutencao();
        manutencao.setDescricao("Manutenção preventiva");
        manutencao.setData_est_man(new Date(System.currentTimeMillis() + 172800000));
        manutencao.setStatus("Pendente");
        manutencao.setAeronave(aeronave);
        ManutencaoModel.salvarManutencao(manutencao);

        Aluno aluno = new Aluno();
        aluno.setCpf("12312312312");
        aluno.setCanac(123123);
        aluno.setNome("Aluno Manutenção");
        aluno.setTelefone("67918181818");
        aluno.setEmail("aluno@manutencao.com");
        aluno.setCurso("Piloto Privado");
        aluno.setHoras_compradas(50.0f);
        aluno.setHoras_voadas(10.0f);
        aluno.setAtivo(true);
        Endereco endAluno = new Endereco();
        endAluno.setCep("79800000");
        endAluno.setCidade("Dourados");
        endAluno.setUF("MS");
        endAluno.setLogradouro("Rua Manutenção");
        endAluno.setNumero("1007");
        endAluno.setBairro("Centro");
        endAluno.setComplemento("Casa 7");
        aluno.setEndereco(endAluno);
        AlunoModel.salvarAluno(aluno);

        Instrutor instrutor = new Instrutor();
        instrutor.setCpf("44400044400");
        instrutor.setCanac(444000);
        instrutor.setNome("Instrutor Manutenção");
        instrutor.setTelefone("67919191919");
        instrutor.setEmail("instrutor@manutencao.com");
        instrutor.setHabilitacao("INVA");
        instrutor.setAtivo(true);
        Usuario usuario = new Usuario();
        usuario.setUsuario("inst_man");
        usuario.setSenha("123");
        usuario.setPermissao(0);
        instrutor.setUsuario(usuario);
        Endereco endInst = new Endereco();
        endInst.setCep("79800000");
        endInst.setCidade("Dourados");
        endInst.setUF("MS");
        endInst.setLogradouro("Rua Instrutor Manutenção");
        endInst.setNumero("1008");
        endInst.setBairro("Centro");
        endInst.setComplemento("Apto 7");
        instrutor.setEndereco(endInst);
        InstrutorModel.salvarInstrutor(instrutor);

        Agendamento agendamento = new Agendamento();
        agendamento.setAeronave(aeronave);
        agendamento.setAluno(aluno);
        agendamento.setInstrutor(instrutor);
        agendamento.setPartida("SBDO");
        agendamento.setDestino("SBCG");
        agendamento.setTipo_voo("VFR-N");
        agendamento.setStatus("Agendado");
        agendamento.setHorario_partida(new Date(System.currentTimeMillis() + 86400000));
        agendamento.setHorario_retorno(new Date(System.currentTimeMillis() + 90000000));

        Request request = new Request();
        String retorno = request.requisicao(agendamento, "api/agendamentos", "POST");
        System.out.println("Resposta do servidor: " + retorno);

        ManutencaoModel.excluirManutencao(manutencao.getId().toString());
        InstrutorModel.excluirInstrutor(instrutor.getCpf());
        AlunoModel.excluirAluno(aluno);
        AeronaveModel.excluirAeronave(aeronave);

        assertTrue(retorno.contains("manutenção") || retorno.contains("error") || retorno.contains("CONFLITO"),
                "Deveria rejeitar agendamento de aeronave em manutenção: " + retorno);
    }
}