package com.sisgea.sisgea.TesteAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Models.InstrutorModel;
import com.sisgea.Entidades.Endereco;
import com.sisgea.Entidades.Instrutor;
import com.sisgea.Entidades.Usuario;

public class TestInstrutor {
    
    @Test
    public void testCreate() {
        Instrutor instrutor = new Instrutor();
        instrutor.setCpf("12345678901");
        instrutor.setCanac(123456);
        instrutor.setNome("Instrutor Teste");
        instrutor.setTelefone("67999999999");
        instrutor.setEmail("instrutor@teste.com");
        instrutor.setHabilitacao("INVA");
        instrutor.setAtivo(true);
        
        Usuario usuario = new Usuario();
        usuario.setUsuario("instrutor_teste");
        usuario.setSenha("123");
        usuario.setPermissao(0);
        instrutor.setUsuario(usuario);
        
        Endereco endereco = new Endereco();
        endereco.setCep("79800000");
        endereco.setCidade("Dourados");
        endereco.setUF("MS");
        endereco.setLogradouro("Rua Teste");
        endereco.setNumero("100");
        endereco.setBairro("Centro");
        endereco.setComplemento("Apto 101");
        instrutor.setEndereco(endereco);
        
        Request request = new Request();
        String retorno = request.requisicao(instrutor, "api/instrutores", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        Instrutor encontrado = null;
        List<Instrutor> instrutores = InstrutorModel.listarTodosInstrutores();
        for (Instrutor i : instrutores) {
            if (i.getCpf().equals("12345678901")) {
                encontrado = i;
                InstrutorModel.excluirInstrutor(i.getCpf());
            }
        }
        
        assertTrue(retorno.contains("\"status\":\"sucesso\""), "Status diferente de sucesso: " + retorno);
        assertNotNull(encontrado, "Instrutor não encontrado após criação.");
    }

    @Test
    public void testRead() {
        Instrutor instrutor = new Instrutor();
        instrutor.setCpf("98765432109");
        instrutor.setCanac(654321);
        instrutor.setNome("Instrutor Leitura");
        instrutor.setTelefone("67988888888");
        instrutor.setEmail("leitura@teste.com");
        instrutor.setHabilitacao("PLA");
        instrutor.setAtivo(true);
        
        Usuario usuario = new Usuario();
        usuario.setUsuario("instrutor_read");
        usuario.setSenha("123");
        usuario.setPermissao(0);
        instrutor.setUsuario(usuario);
        
        Endereco endereco = new Endereco();
        endereco.setCep("79800000");
        endereco.setCidade("Dourados");
        endereco.setUF("MS");
        endereco.setLogradouro("Rua Leitura");
        endereco.setNumero("200");
        endereco.setBairro("Centro");
        endereco.setComplemento("Casa");
        instrutor.setEndereco(endereco);
        
        Request request = new Request();
        String retorno = request.requisicao(instrutor, "api/instrutores", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        Instrutor encontrado = null;
        List<Instrutor> instrutores = InstrutorModel.listarTodosInstrutores();
        for (Instrutor i : instrutores) {
            if (i.getCpf().equals("98765432109")) {
                encontrado = i;
                break;
            }
        }
        
        assertNotNull(encontrado, "Instrutor não encontrado na lista após criação.");
        assertEquals("Instrutor Leitura", encontrado.getNome(), "Nome incorreto: " + encontrado.getNome());
        
        String respostaGet = request.requisicao(null, "api/instrutores/98765432109", "GET");
        assertTrue(respostaGet.contains("Instrutor Leitura"), "GET não retornou o instrutor correto: " + respostaGet);
        
        InstrutorModel.excluirInstrutor(encontrado.getCpf());
    }

    @Test
    public void testUpdate() {
        Instrutor instrutor = new Instrutor();
        instrutor.setCpf("11122233344");
        instrutor.setCanac(111222);
        instrutor.setNome("Instrutor Update");
        instrutor.setTelefone("67977777777");
        instrutor.setEmail("update@teste.com");
        instrutor.setHabilitacao("PC");
        instrutor.setAtivo(true);
        
        Usuario usuario = new Usuario();
        usuario.setUsuario("instrutor_update");
        usuario.setSenha("123");
        usuario.setPermissao(0);
        instrutor.setUsuario(usuario);
        
        Endereco endereco = new Endereco();
        endereco.setCep("79800000");
        endereco.setCidade("Dourados");
        endereco.setUF("MS");
        endereco.setLogradouro("Rua Update");
        endereco.setNumero("300");
        endereco.setBairro("Centro");
        endereco.setComplemento("Bloco A");
        instrutor.setEndereco(endereco);
        
        Request request = new Request();
        String retorno = request.requisicao(instrutor, "api/instrutores", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        Instrutor antigo = null;
        List<Instrutor> instrutores = InstrutorModel.listarTodosInstrutores();
        for (Instrutor i : instrutores) {
            if (i.getCpf().equals("11122233344")) {
                antigo = i;
                break;
            }
        }
        
        assertNotNull(antigo, "Instrutor não encontrado para update.");
        
        antigo.setNome("Instrutor Atualizado");
        antigo.setHabilitacao("INVA");
        String retornoUpdate = request.requisicao(antigo, "api/instrutores/11122233344", "PUT");
        System.out.println("Resposta do servidor no UPDATE: " + retornoUpdate);
        
        assertTrue(retornoUpdate.contains("\"status\":\"sucesso\""), "Falha no update: " + retornoUpdate);
        
        Instrutor novo = null;
        instrutores = InstrutorModel.listarTodosInstrutores();
        for (Instrutor i : instrutores) {
            if (i.getCpf().equals("11122233344")) {
                novo = i;
                break;
            }
        }
        
        assertNotNull(novo, "Instrutor não encontrado após update.");
        assertEquals("Instrutor Atualizado", novo.getNome(), "Nome não foi atualizado: " + novo.getNome());
        assertEquals("INVA", novo.getHabilitacao(), "Habilitação não foi atualizada: " + novo.getHabilitacao());
        
        InstrutorModel.excluirInstrutor(novo.getCpf());
    }

    @Test
    public void testDelete() {
        Instrutor instrutor = new Instrutor();
        instrutor.setCpf("55566677788");
        instrutor.setCanac(555666);
        instrutor.setNome("Instrutor Delete");
        instrutor.setTelefone("67966666666");
        instrutor.setEmail("delete@teste.com");
        instrutor.setHabilitacao("INVA");
        instrutor.setAtivo(true);
        
        Usuario usuario = new Usuario();
        usuario.setUsuario("instrutor_delete");
        usuario.setSenha("123");
        usuario.setPermissao(0);
        instrutor.setUsuario(usuario);
        
        Endereco endereco = new Endereco();
        endereco.setCep("79800000");
        endereco.setCidade("Dourados");
        endereco.setUF("MS");
        endereco.setLogradouro("Rua Delete");
        endereco.setNumero("400");
        endereco.setBairro("Centro");
        endereco.setComplemento("Fundos");
        instrutor.setEndereco(endereco);
        
        Request request = new Request();
        String retorno = request.requisicao(instrutor, "api/instrutores", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        Instrutor encontrado = null;
        List<Instrutor> instrutores = InstrutorModel.listarTodosInstrutores();
        for (Instrutor i : instrutores) {
            if (i.getCpf().equals("55566677788")) {
                encontrado = i;
                break;
            }
        }
        
        assertNotNull(encontrado, "Instrutor não encontrado para delete.");
        
        String respostaDelete = request.requisicao(null, "api/instrutores/55566677788", "DELETE");
        System.out.println("Resposta do servidor no DELETE: " + respostaDelete);
        
        assertTrue(respostaDelete.contains("\"status\":\"deletado\""), "Falha no delete: " + respostaDelete);
        
        Instrutor checar = InstrutorModel.buscarInstrutor("55566677788");
        
        assertTrue(!checar.getAtivo(), "Instrutor ainda está ativo.");

        InstrutorModel.excluirInstrutor(checar.getCpf());
    }

    @Test
    public void testValidacaoUsuarioObrigatorio() {
        Instrutor instrutor = new Instrutor();
        instrutor.setNome("Instrutor Teste");
        instrutor.setCpf("44444444444");
        instrutor.setCanac(444444);
        instrutor.setEmail("teste@instrutor.com");
        
        Request request = new Request();
        String retorno = request.requisicao(instrutor, "api/instrutores", "POST");
        System.out.println("Resposta do servidor: " + retorno);
        
        assertTrue(retorno.contains("error"), "Deveria retornar erro de usuário obrigatório: " + retorno);
        assertTrue(retorno.contains("Usuário") || retorno.contains("senha"), 
                "Mensagem de erro não menciona usuário/senha: " + retorno);
    }

    @Test
    public void testValidacaoUsuarioDuplicado() {
        Instrutor instrutor1 = new Instrutor();
        instrutor1.setCpf("10101010101");
        instrutor1.setCanac(101010);
        instrutor1.setNome("Instrutor Usuario");
        instrutor1.setTelefone("67999999999");
        instrutor1.setEmail("usuario1@teste.com");
        instrutor1.setHabilitacao("INVA");
        instrutor1.setAtivo(true);
        
        Usuario usuario1 = new Usuario();
        usuario1.setUsuario("inst_user_dup");
        usuario1.setSenha("123");
        usuario1.setPermissao(0);
        instrutor1.setUsuario(usuario1);
        
        Endereco endereco1 = new Endereco();
        endereco1.setCep("79800000");
        endereco1.setCidade("Dourados");
        endereco1.setUF("MS");
        endereco1.setLogradouro("Rua Teste");
        endereco1.setNumero("100");
        endereco1.setBairro("Centro");
        endereco1.setComplemento("Apto 1");
        instrutor1.setEndereco(endereco1);
        
        Request request = new Request();
        String retorno1 = request.requisicao(instrutor1, "api/instrutores", "POST");
        System.out.println("Resposta do servidor: " + retorno1);
        
        Instrutor instrutor2 = new Instrutor();
        instrutor2.setCpf("20202020202");
        instrutor2.setCanac(202020);
        instrutor2.setNome("Instrutor Usuario 2");
        instrutor2.setTelefone("67988888888");
        instrutor2.setEmail("usuario2@teste.com");
        instrutor2.setHabilitacao("PLA");
        instrutor2.setAtivo(true);
        
        Usuario usuario2 = new Usuario();
        usuario2.setUsuario("inst_user_dup");
        usuario2.setSenha("123");
        usuario2.setPermissao(0);
        instrutor2.setUsuario(usuario2);
        
        Endereco endereco2 = new Endereco();
        endereco2.setCep("79800000");
        endereco2.setCidade("Dourados");
        endereco2.setUF("MS");
        endereco2.setLogradouro("Rua Teste");
        endereco2.setNumero("200");
        endereco2.setBairro("Centro");
        endereco2.setComplemento("Apto 2");
        instrutor2.setEndereco(endereco2);
        
        String retorno2 = request.requisicao(instrutor2, "api/instrutores", "POST");
        System.out.println("Resposta do servidor (duplicado): " + retorno2);
        
        InstrutorModel.excluirInstrutor("10101010101");
        
        assertTrue(retorno2.contains("error"), "Deveria retornar erro de usuário duplicado: " + retorno2);
        assertTrue(retorno2.contains("usuário"), "Mensagem de erro não menciona usuário: " + retorno2);
    }

    @Test
    public void testValidacaoCpfDuplicado() {
        Instrutor instrutor1 = new Instrutor();
        instrutor1.setCpf("77777777777");
        instrutor1.setCanac(777777);
        instrutor1.setNome("Instrutor Duplicado");
        instrutor1.setTelefone("67999999999");
        instrutor1.setEmail("duplicado@teste.com");
        instrutor1.setHabilitacao("INVA");
        instrutor1.setAtivo(true);
        
        Usuario usuario1 = new Usuario();
        usuario1.setUsuario("inst_dup1");
        usuario1.setSenha("123");
        usuario1.setPermissao(0);
        instrutor1.setUsuario(usuario1);
        
        Endereco endereco1 = new Endereco();
        endereco1.setCep("79800000");
        endereco1.setCidade("Dourados");
        endereco1.setUF("MS");
        endereco1.setLogradouro("Rua Teste");
        endereco1.setNumero("100");
        endereco1.setBairro("Centro");
        endereco1.setComplemento("Apto 1");
        instrutor1.setEndereco(endereco1);
        
        Request request = new Request();
        String retorno1 = request.requisicao(instrutor1, "api/instrutores", "POST");
        System.out.println("Resposta do servidor: " + retorno1);
        
        Instrutor instrutor2 = new Instrutor();
        instrutor2.setCpf("77777777777");
        instrutor2.setCanac(888888);
        instrutor2.setNome("Instrutor Duplicado 2");
        instrutor2.setTelefone("67988888888");
        instrutor2.setEmail("duplicado2@teste.com");
        instrutor2.setHabilitacao("PLA");
        instrutor2.setAtivo(true);
        
        Usuario usuario2 = new Usuario();
        usuario2.setUsuario("inst_dup2");
        usuario2.setSenha("123");
        usuario2.setPermissao(0);
        instrutor2.setUsuario(usuario2);
        
        Endereco endereco2 = new Endereco();
        endereco2.setCep("79800000");
        endereco2.setCidade("Dourados");
        endereco2.setUF("MS");
        endereco2.setLogradouro("Rua Teste");
        endereco2.setNumero("200");
        endereco2.setBairro("Centro");
        endereco2.setComplemento("Apto 2");
        instrutor2.setEndereco(endereco2);
        
        String retorno2 = request.requisicao(instrutor2, "api/instrutores", "POST");
        System.out.println("Resposta do servidor (duplicado): " + retorno2);
        
        InstrutorModel.excluirInstrutor("77777777777");
        
        assertTrue(retorno2.contains("error"), "Deveria retornar erro de CPF duplicado: " + retorno2);
        assertTrue(retorno2.contains("CPF"), "Mensagem de erro não menciona CPF: " + retorno2);
    }

    @Test
    public void testValidacaoCanacDuplicado() {
        Instrutor instrutor1 = new Instrutor();
        instrutor1.setCpf("88888888888");
        instrutor1.setCanac(999999);
        instrutor1.setNome("Instrutor CANAC");
        instrutor1.setTelefone("67999999999");
        instrutor1.setEmail("canac1@teste.com");
        instrutor1.setHabilitacao("INVA");
        instrutor1.setAtivo(true);
        
        Usuario usuario1 = new Usuario();
        usuario1.setUsuario("inst_canac1");
        usuario1.setSenha("123");
        usuario1.setPermissao(0);
        instrutor1.setUsuario(usuario1);
        
        Endereco endereco1 = new Endereco();
        endereco1.setCep("79800000");
        endereco1.setCidade("Dourados");
        endereco1.setUF("MS");
        endereco1.setLogradouro("Rua Teste");
        endereco1.setNumero("100");
        endereco1.setBairro("Centro");
        endereco1.setComplemento("Apto 1");
        instrutor1.setEndereco(endereco1);
        
        Request request = new Request();
        String retorno1 = request.requisicao(instrutor1, "api/instrutores", "POST");
        System.out.println("Resposta do servidor: " + retorno1);
        
        Instrutor instrutor2 = new Instrutor();
        instrutor2.setCpf("99999999999");
        instrutor2.setCanac(999999);
        instrutor2.setNome("Instrutor CANAC 2");
        instrutor2.setTelefone("67988888888");
        instrutor2.setEmail("canac2@teste.com");
        instrutor2.setHabilitacao("PLA");
        instrutor2.setAtivo(true);
        
        Usuario usuario2 = new Usuario();
        usuario2.setUsuario("inst_canac2");
        usuario2.setSenha("123");
        usuario2.setPermissao(0);
        instrutor2.setUsuario(usuario2);
        
        Endereco endereco2 = new Endereco();
        endereco2.setCep("79800000");
        endereco2.setCidade("Dourados");
        endereco2.setUF("MS");
        endereco2.setLogradouro("Rua Teste");
        endereco2.setNumero("200");
        endereco2.setBairro("Centro");
        endereco2.setComplemento("Apto 2");
        instrutor2.setEndereco(endereco2);
        
        String retorno2 = request.requisicao(instrutor2, "api/instrutores", "POST");
        System.out.println("Resposta do servidor (duplicado): " + retorno2);
        
        InstrutorModel.excluirInstrutor("88888888888");
        
        assertTrue(retorno2.contains("error"), "Deveria retornar erro de CANAC duplicado: " + retorno2);
        assertTrue(retorno2.contains("CANAC"), "Mensagem de erro não menciona CANAC: " + retorno2);
    }
}