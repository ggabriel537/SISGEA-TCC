package com.sisgea.sisgea.TestCRUD;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.sisgea.BancoDados.Controllers.AeronaveController;
import com.sisgea.BancoDados.Controllers.AlunoController;
import com.sisgea.BancoDados.Controllers.AgendamentoController;
import com.sisgea.BancoDados.Controllers.InstrutorController;
import com.sisgea.Entidades.Aeronave;
import com.sisgea.Entidades.Agendamento;
import com.sisgea.Entidades.Aluno;
import com.sisgea.Entidades.Instrutor;
import com.sisgea.Entidades.Usuario;
import com.sisgea.sisgea.Uteis;

class TesteAgendamento {

    // ---------- HELPERS ----------

    private Aeronave getOrCreateAeronave(String modelo, String fabricante, String habilitacao, String tipoVoo) {
        List<Aeronave> aeronaves = AeronaveController.listarAeronaves();
        Aeronave aeronave;

        if (aeronaves.isEmpty()) {
            aeronave = new Aeronave(Uteis.gerarMatriculaAleatoria(), modelo, fabricante, habilitacao, tipoVoo);
            AeronaveController.salvarAeronave(aeronave);
        } else {
            aeronave = aeronaves.get(0);
        }

        return aeronave;
    }

    private Aluno getOrCreateAluno(String nome, String cpf, String telefone, String email, String curso) {
        List<Aluno> alunos = AlunoController.listarAlunos();
        Aluno aluno;

        if (alunos.isEmpty()) {
            aluno = new Aluno(nome, cpf, telefone, email, curso);
            AlunoController.salvarAluno(aluno);
        } else {
            aluno = alunos.get(0);
        }

        return aluno;
    }

    private Instrutor getOrCreateInstrutor(String nome, String cpf, String telefone, String email, int categoria, String habilitacao, String usuario, String senha) {
        List<Instrutor> instrutores = InstrutorController.listarInstrutores();
        Instrutor instrutor;

        if (instrutores.isEmpty()) {
            Usuario usuarioInstrutor = new Usuario(usuario, senha, categoria);
            instrutor = new Instrutor(usuarioInstrutor, nome, cpf, telefone, email, categoria, habilitacao);
            InstrutorController.salvarInstrutor(instrutor);
        } else {
            instrutor = instrutores.get(0);
        }

        return instrutor;
    }

    // ---------- TESTES ----------

    @Test
    void testSalvarAgendamento() {
        Aeronave aeronave = getOrCreateAeronave("Cessna 172", "Cessna", "VFR", "Treinamento");
        Aluno aluno = getOrCreateAluno("João Silva", "12345678901", "99999-9999", "joao@email.com", "Aviação");
        Instrutor instrutor = getOrCreateInstrutor("Carlos Souza", "98765432100", "98888-8888", "carlos@email.com", 2, "IFR", "instrutor1", "senha");

        java.sql.Date dataAgendamentoSql = new java.sql.Date(new Date().getTime());

        AgendamentoController.salvarAgendamento(aeronave, aluno, instrutor, "SBSP", "SBGL", "Duplo", "Agendado", dataAgendamentoSql);

        List<Agendamento> lista = AgendamentoController.listarAgendamentos();
        assertFalse(lista.isEmpty(), "Lista vazia! Não foi salvo o agendamento.");

        Agendamento agendamentoSalvo = lista.stream()
                .filter(a -> "SBSP".equals(a.getPartida()) && "SBGL".equals(a.getDestino()))
                .findFirst()
                .orElse(null);

        assertNotNull(agendamentoSalvo, "Agendamento salvo não encontrado.");
        assertEquals("Duplo", agendamentoSalvo.getTipo_voo());
        assertEquals("Agendado", agendamentoSalvo.getStatus());
    }

    @Test
    void testAtualizarAgendamento() {
        Aeronave aeronave = getOrCreateAeronave("Piper PA-28", "Piper", "IFR", "Treinamento");
        Aluno aluno = getOrCreateAluno("Maria Oliveira", "11122233344", "97777-7777", "maria@email.com", "Aviação");
        Instrutor instrutor = getOrCreateInstrutor("Paulo Lima", "99988877766", "96666-6666", "paulo@email.com", 2, "IFR", "instrutor2", "senha2");

        java.sql.Date dataAgendamentoSql = new java.sql.Date(new Date().getTime());

        AgendamentoController.salvarAgendamento(aeronave, aluno, instrutor, "SBGR", "SBKP", "Solo", "Pendente", dataAgendamentoSql);

        Agendamento agendamentoParaUpdate = AgendamentoController.listarAgendamentos().stream()
                .filter(a -> "SBGR".equals(a.getPartida()) && "SBKP".equals(a.getDestino()))
                .findFirst()
                .orElse(null);

        assertNotNull(agendamentoParaUpdate, "Agendamento para update não encontrado.");

        agendamentoParaUpdate.setStatus("Confirmado");
        AgendamentoController.atualizarAgendamento(agendamentoParaUpdate);

        Agendamento atualizado = AgendamentoController.listarAgendamentos().stream()
                .filter(a -> a.getId().equals(agendamentoParaUpdate.getId()))
                .findFirst()
                .orElse(null);

        assertNotNull(atualizado, "Agendamento atualizado não encontrado.");
        assertEquals("Confirmado", atualizado.getStatus(), "Status não foi atualizado.");
    }

    @Test
    void testDeletarAgendamento() {
        Aeronave aeronave = getOrCreateAeronave("Embraer 190", "Embraer", "IFR", "Comercial");
        Aluno aluno = getOrCreateAluno("Pedro Santos", "22233344455", "95555-5555", "pedro@email.com", "Aviação");
        Instrutor instrutor = getOrCreateInstrutor("Lucas Costa", "77766655544", "94444-4444", "lucas@email.com", 2, "IFR", "instrutor3", "senha3");

        java.sql.Date dataAgendamentoSql = new java.sql.Date(new Date().getTime());

        AgendamentoController.salvarAgendamento(aeronave, aluno, instrutor, "SBCT", "SBPJ", "Duplo", "Agendado", dataAgendamentoSql);

        Agendamento agendamentoParaDeletar = AgendamentoController.listarAgendamentos().stream()
                .filter(a -> "SBCT".equals(a.getPartida()) && "SBPJ".equals(a.getDestino()))
                .findFirst()
                .orElse(null);

        assertNotNull(agendamentoParaDeletar, "Agendamento para deletar não encontrado.");

        AgendamentoController.deletarAgendamento(agendamentoParaDeletar.getId().toString());

        Agendamento deletado = AgendamentoController.listarAgendamentos().stream()
                .filter(a -> a.getId().equals(agendamentoParaDeletar.getId()))
                .findFirst()
                .orElse(null);

        assertNull(deletado, "Agendamento não foi deletado.");
    }

    @Test
    void testListarAgendamentos() {
        List<Agendamento> lista = AgendamentoController.listarAgendamentos();
        assertNotNull(lista, "Lista retornou null.");
    }
}
