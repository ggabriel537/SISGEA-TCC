package com.sisgea.Entidades;

public class Aluno {
    private Integer canac;
    private String cpf;
    private String nome;
    private String telefone;
    private String email;
    private String curso;
    private Endereco endereco;
    private Float horas_compradas;
    private Float horas_voadas;

    public Aluno(String nome, String cpf, String telefone, String email, String curso) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.curso = curso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCanac() {
        return canac;
    }

    public void setCanac(Integer canac) {
        this.canac = canac;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Float getHoras_compradas() {
        return horas_compradas;
    }

    public void setHoras_compradas(Float horas_compradas) {
        this.horas_compradas = horas_compradas;
    }

    public Float getHoras_voadas() {
        return horas_voadas;
    }

    public void setHoras_voadas(Float horas_voadas) {
        this.horas_voadas = horas_voadas;
    }
}
