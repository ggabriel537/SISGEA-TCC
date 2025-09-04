package com.sisgea.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.util.UUID;

@Entity
public class Endereco {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "cep")
    private String cep;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "numero")
    private String numero;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "uf")
    private String UF;

    public Endereco(String cep, String logradouro, String bairro, String complemento, String numero, String cidade, String UF) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.complemento = complemento;
        this.numero = numero;
        this.cidade = cidade;
        this.UF = UF;
    }

    public Endereco() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }
}
