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
}