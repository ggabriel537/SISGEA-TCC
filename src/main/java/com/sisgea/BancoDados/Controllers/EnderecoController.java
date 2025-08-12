package com.sisgea.BancoDados.Controllers;

import java.util.List;

import com.sisgea.BancoDados.Models.EnderecoModel;
import com.sisgea.Entidades.Endereco;

public class EnderecoController {
    public static void salvarEndereco(String cep, String rua, String bairro, String complemento, String numero, String cidade, String estado) {
        Endereco endereco = new Endereco();
        endereco.setCep(cep);
        endereco.setLogradouro(rua);
        endereco.setBairro(bairro);
        endereco.setComplemento(complemento);
        endereco.setNumero(numero);
        endereco.setCidade(cidade);
        endereco.setUF(estado);
        EnderecoModel.salvarEndereco(endereco);
    }

    public static List<Endereco> listarEnderecos() {
        return EnderecoModel.listarEnderecos();
    }

    public void deletarEndereco(Endereco endereco) {
        EnderecoModel.excluirEndereco(endereco);
    }   

    public void atualizarEndereco(Endereco endereco) {
        EnderecoModel.atualizarEndereco(endereco);
    }
}
