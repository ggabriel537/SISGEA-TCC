package com.sisgea.sisgea.TesteAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Models.AlunoModel;
import com.sisgea.Entidades.Aluno;
import com.sisgea.Entidades.Endereco;

public class TestAluno {
    
    @Test
    public void testCreate() {
        Aluno aluno = new Aluno();
        aluno.setCpf("12345678901");
        aluno.setCanac(123456);
        aluno.setNome("Aluno Teste");
        aluno.setTelefone("67999999999");
        aluno.setEmail("aluno@teste.com");
        aluno.setCurso("Piloto Privado");
        aluno.setHoras_compradas(50.0f);
        aluno.setHoras_voadas(10.0f);
        aluno.setAtivo(true);
        
        Endereco endereco = new Endereco();
        endereco.setCep("79800000");
        endereco.setCidade("Dourados");
        endereco.setUF("MS");
        endereco.setLogradouro("Rua Teste");
        endereco.setNumero("100");
        endereco.setBairro("Centro");
        endereco.setComplemento("Apto 101");
        aluno.setEndereco(endereco);
        
        Request request = new Request();
        String retorno = request.requisicao(aluno, "api/alunos", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        Aluno encontrado = null;
        List<Aluno> alunos = AlunoModel.listarTodosAlunos();
        for (Aluno a : alunos) {
            if (a.getCpf().equals("12345678901")) {
                encontrado = a;
                AlunoModel.excluirAluno(a);
            }
        }
        
        assertTrue(retorno.contains("\"status\":\"sucesso\""), "Status diferente de sucesso: " + retorno);
        assertNotNull(encontrado, "Aluno não encontrado após criação.");
    }

    @Test
    public void testRead() {
        Aluno aluno = new Aluno();
        aluno.setCpf("98765432109");
        aluno.setCanac(654321);
        aluno.setNome("Aluno Leitura");
        aluno.setTelefone("67988888888");
        aluno.setEmail("leitura@teste.com");
        aluno.setCurso("Piloto Comercial");
        aluno.setHoras_compradas(100.0f);
        aluno.setHoras_voadas(20.0f);
        aluno.setAtivo(true);
        
        Endereco endereco = new Endereco();
        endereco.setCep("79800000");
        endereco.setCidade("Dourados");
        endereco.setUF("MS");
        endereco.setLogradouro("Rua Leitura");
        endereco.setNumero("200");
        endereco.setBairro("Centro");
        endereco.setComplemento("Casa");
        aluno.setEndereco(endereco);
        
        Request request = new Request();
        String retorno = request.requisicao(aluno, "api/alunos", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        Aluno encontrado = null;
        List<Aluno> alunos = AlunoModel.listarTodosAlunos();
        for (Aluno a : alunos) {
            if (a.getCpf().equals("98765432109")) {
                encontrado = a;
                break;
            }
        }
        
        assertNotNull(encontrado, "Aluno não encontrado na lista após criação.");
        assertEquals("Aluno Leitura", encontrado.getNome(), "Nome incorreto: " + encontrado.getNome());
        
        String respostaGet = request.requisicao(null, "api/alunos/98765432109", "GET");
        assertTrue(respostaGet.contains("Aluno Leitura"), "GET não retornou o aluno correto: " + respostaGet);
        
        AlunoModel.excluirAluno(encontrado);
    }

    @Test
    public void testUpdate() {
        Aluno aluno = new Aluno();
        aluno.setCpf("11122233344");
        aluno.setCanac(111222);
        aluno.setNome("Aluno Update");
        aluno.setTelefone("67977777777");
        aluno.setEmail("update@teste.com");
        aluno.setCurso("Piloto Privado");
        aluno.setHoras_compradas(30.0f);
        aluno.setHoras_voadas(5.0f);
        aluno.setAtivo(true);
        
        Endereco endereco = new Endereco();
        endereco.setCep("79800000");
        endereco.setCidade("Dourados");
        endereco.setUF("MS");
        endereco.setLogradouro("Rua Update");
        endereco.setNumero("300");
        endereco.setBairro("Centro");
        endereco.setComplemento("Bloco A");
        aluno.setEndereco(endereco);
        
        Request request = new Request();
        String retorno = request.requisicao(aluno, "api/alunos", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        Aluno antigo = null;
        List<Aluno> alunos = AlunoModel.listarTodosAlunos();
        for (Aluno a : alunos) {
            if (a.getCpf().equals("11122233344")) {
                antigo = a;
                break;
            }
        }
        
        assertNotNull(antigo, "Aluno não encontrado para update.");
        
        antigo.setNome("Aluno Atualizado");
        antigo.setHoras_voadas(15.0f);
        String retornoUpdate = request.requisicao(antigo, "api/alunos/11122233344", "PUT");
        System.out.println("Resposta do servidor no UPDATE: " + retornoUpdate);
        
        assertTrue(retornoUpdate.contains("\"status\":\"sucesso\""), "Falha no update: " + retornoUpdate);
        
        Aluno novo = null;
        alunos = AlunoModel.listarTodosAlunos();
        for (Aluno a : alunos) {
            if (a.getCpf().equals("11122233344")) {
                novo = a;
                break;
            }
        }
        
        assertNotNull(novo, "Aluno não encontrado após update.");
        assertEquals("Aluno Atualizado", novo.getNome(), "Nome não foi atualizado: " + novo.getNome());
        assertEquals(15.0f, novo.getHoras_voadas(), "Horas voadas não foram atualizadas: " + novo.getHoras_voadas());
        
        AlunoModel.excluirAluno(novo);
    }

    @Test
    public void testDelete() {
        Aluno aluno = new Aluno();
        aluno.setCpf("55566677788");
        aluno.setCanac(555666);
        aluno.setNome("Aluno Delete");
        aluno.setTelefone("67966666666");
        aluno.setEmail("delete@teste.com");
        aluno.setCurso("IFR");
        aluno.setHoras_compradas(80.0f);
        aluno.setHoras_voadas(40.0f);
        aluno.setAtivo(true);
        
        Endereco endereco = new Endereco();
        endereco.setCep("79800000");
        endereco.setCidade("Dourados");
        endereco.setUF("MS");
        endereco.setLogradouro("Rua Delete");
        endereco.setNumero("400");
        endereco.setBairro("Centro");
        endereco.setComplemento("Fundos");
        aluno.setEndereco(endereco);
        
        Request request = new Request();
        String retorno = request.requisicao(aluno, "api/alunos", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        Aluno encontrado = null;
        List<Aluno> alunos = AlunoModel.listarTodosAlunos();
        for (Aluno a : alunos) {
            if (a.getCpf().equals("55566677788")) {
                encontrado = a;
                break;
            }
        }
        
        assertNotNull(encontrado, "Aluno não encontrado para delete.");
        
        String respostaDelete = request.requisicao(null, "api/alunos/55566677788", "DELETE");
        System.out.println("Resposta do servidor no DELETE: " + respostaDelete);
        
        Aluno checar = AlunoModel.buscarAluno("55566677788");
        
        assertTrue(checar == null || !checar.getAtivo(), "Aluno ainda está ativo após DELETE.");
        
        if (checar != null) {
            AlunoModel.excluirAluno(checar);
        }
    }

    @Test
    public void testCpfDuplicado() {
        Aluno aluno1 = new Aluno();
        aluno1.setCpf("11111111111");
        aluno1.setCanac(111111);
        aluno1.setNome("Aluno CPF 1");
        aluno1.setTelefone("67918181818");
        aluno1.setEmail("aluno1@cpf.com");
        aluno1.setCurso("Piloto Privado");
        aluno1.setHoras_compradas(50.0f);
        aluno1.setHoras_voadas(10.0f);
        aluno1.setAtivo(true);
        Endereco endereco1 = new Endereco();
        endereco1.setCep("79800000");
        endereco1.setCidade("Dourados");
        endereco1.setUF("MS");
        endereco1.setLogradouro("Rua CPF 1");
        endereco1.setNumero("1100");
        endereco1.setBairro("Centro");
        endereco1.setComplemento("Casa 1");
        aluno1.setEndereco(endereco1);
        
        Request request = new Request();
        String retorno1 = request.requisicao(aluno1, "api/alunos", "POST");
        System.out.println("Primeiro aluno: " + retorno1);
        
        Aluno aluno2 = new Aluno();
        aluno2.setCpf("11111111111");
        aluno2.setCanac(222222);
        aluno2.setNome("Aluno CPF 2");
        aluno2.setTelefone("67919191919");
        aluno2.setEmail("aluno2@cpf.com");
        aluno2.setCurso("Piloto Comercial");
        aluno2.setHoras_compradas(100.0f);
        aluno2.setHoras_voadas(20.0f);
        aluno2.setAtivo(true);
        Endereco endereco2 = new Endereco();
        endereco2.setCep("79800000");
        endereco2.setCidade("Dourados");
        endereco2.setUF("MS");
        endereco2.setLogradouro("Rua CPF 2");
        endereco2.setNumero("1101");
        endereco2.setBairro("Centro");
        endereco2.setComplemento("Casa 2");
        aluno2.setEndereco(endereco2);
        
        String retorno2 = request.requisicao(aluno2, "api/alunos", "POST");
        System.out.println("Segundo aluno (CPF duplicado): " + retorno2);
        
        List<Aluno> alunos = AlunoModel.listarTodosAlunos();
        for (Aluno a : alunos) {
            if (a.getCpf().equals("11111111111")) {
                AlunoModel.excluirAluno(a);
            }
        }
        
        assertTrue(retorno1.contains("\"status\":\"sucesso\""), "Primeiro aluno deveria ter sucesso: " + retorno1);
        assertTrue(retorno2.contains("existe um aluno com este CPF") || retorno2.contains("error"), 
                  "Segundo aluno deveria gerar conflito de CPF: " + retorno2);
    }

    @Test
    public void testCanacDuplicado() {
        Aluno aluno1 = new Aluno();
        aluno1.setCpf("22222222222");
        aluno1.setCanac(123456);
        aluno1.setNome("Aluno CANAC 1");
        aluno1.setTelefone("67920202020");
        aluno1.setEmail("aluno1@canac.com");
        aluno1.setCurso("Piloto Privado");
        aluno1.setHoras_compradas(50.0f);
        aluno1.setHoras_voadas(10.0f);
        aluno1.setAtivo(true);
        Endereco endereco1 = new Endereco();
        endereco1.setCep("79800000");
        endereco1.setCidade("Dourados");
        endereco1.setUF("MS");
        endereco1.setLogradouro("Rua CANAC 1");
        endereco1.setNumero("1102");
        endereco1.setBairro("Centro");
        endereco1.setComplemento("Casa 3");
        aluno1.setEndereco(endereco1);
        
        Request request = new Request();
        String retorno1 = request.requisicao(aluno1, "api/alunos", "POST");
        System.out.println("Primeiro aluno: " + retorno1);
        
        Aluno aluno2 = new Aluno();
        aluno2.setCpf("33333333333");
        aluno2.setCanac(123456);
        aluno2.setNome("Aluno CANAC 2");
        aluno2.setTelefone("67921212121");
        aluno2.setEmail("aluno2@canac.com");
        aluno2.setCurso("Piloto Comercial");
        aluno2.setHoras_compradas(100.0f);
        aluno2.setHoras_voadas(20.0f);
        aluno2.setAtivo(true);
        Endereco endereco2 = new Endereco();
        endereco2.setCep("79800000");
        endereco2.setCidade("Dourados");
        endereco2.setUF("MS");
        endereco2.setLogradouro("Rua CANAC 2");
        endereco2.setNumero("1103");
        endereco2.setBairro("Centro");
        endereco2.setComplemento("Casa 4");
        aluno2.setEndereco(endereco2);
        
        String retorno2 = request.requisicao(aluno2, "api/alunos", "POST");
        System.out.println("Segundo aluno (CANAC duplicado): " + retorno2);
        
        List<Aluno> alunos = AlunoModel.listarTodosAlunos();
        for (Aluno a : alunos) {
            if (a.getCpf().equals("22222222222") || a.getCpf().equals("33333333333")) {
                AlunoModel.excluirAluno(a);
            }
        }
        
        assertTrue(retorno1.contains("\"status\":\"sucesso\""), "Primeiro aluno deveria ter sucesso: " + retorno1);
        assertTrue(retorno2.contains("existe um aluno com este CANAC") || retorno2.contains("error"), 
                  "Segundo aluno deveria gerar conflito de CANAC: " + retorno2);
    }

    @Test
    public void testEmailInvalido() {
        Aluno aluno = new Aluno();
        aluno.setCpf("44444444444");
        aluno.setCanac(444444);
        aluno.setNome("Aluno Email Invalido");
        aluno.setTelefone("67922222222");
        aluno.setEmail("emailinvalido.com");
        aluno.setCurso("Piloto Privado");
        aluno.setHoras_compradas(50.0f);
        aluno.setHoras_voadas(10.0f);
        aluno.setAtivo(true);
        Endereco endereco = new Endereco();
        endereco.setCep("79800000");
        endereco.setCidade("Dourados");
        endereco.setUF("MS");
        endereco.setLogradouro("Rua Email");
        endereco.setNumero("1104");
        endereco.setBairro("Centro");
        endereco.setComplemento("Casa 5");
        aluno.setEndereco(endereco);
        
        Request request = new Request();
        String retorno = request.requisicao(aluno, "api/alunos", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        List<Aluno> alunos = AlunoModel.listarTodosAlunos();
        for (Aluno a : alunos) {
            if (a.getCpf().equals("44444444444")) {
                AlunoModel.excluirAluno(a);
            }
        }
        
        assertTrue(retorno.contains("Email pode estar incorreto") || retorno.contains("warn"), 
                  "Deveria gerar aviso de email inválido: " + retorno);
    }
}