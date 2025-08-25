package com.sisgea.Entidades;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Aeronave {

    @Id
    @Column(name = "matricula")
    private String matricula;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "fabricante")
    private String fabricante;

    @Column(name = "habilitacao")
    private String habilitacao;

    @Column(name = "tipo_de_voo")
    private String tipo_de_voo;

    @Column(name = "horas_de_voo")
    private Float horas_de_voo;

    @OneToMany(mappedBy = "aeronave", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Manutencao> manutencoes;

    public Aeronave() {}

    public Aeronave(String matricula, String modelo, String fabricante, String habilitacao, String tipo_de_voo) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.habilitacao = habilitacao;
        this.tipo_de_voo = tipo_de_voo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getHabilitacao() {
        return habilitacao;
    }

    public void setHabilitacao(String habilitacao) {
        this.habilitacao = habilitacao;
    }

    public String getTipo_de_voo() {
        return tipo_de_voo;
    }

    public void setTipo_de_voo(String tipo_de_voo) {
        this.tipo_de_voo = tipo_de_voo;
    }

    public Float getHoras_de_voo() {
        return horas_de_voo;
    }

    public void setHoras_de_voo(Float horas_de_voo) {
        this.horas_de_voo = horas_de_voo;
    }

    public List<Manutencao> getManutencoes() {
        return manutencoes;
    }

    public void setManutencoes(List<Manutencao> manutencoes) {
        this.manutencoes = manutencoes;
    }
}
