package com.sisgea.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.util.Date;
import java.util.UUID;

@Entity
public class Discrepancia {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "data")
    private Date data;

    @Column(name = "sistema")
    private String sistema;

    @Column(name = "discrepancia")
    private String discrepancia;

    @Column(name = "correcao")
    private String correcao;

    public Discrepancia(UUID id, Date data, String sistema, String discrepancia, String correcao) {
        this.id = id;
        this.data = data;
        this.sistema = sistema;
        this.discrepancia = discrepancia;
        this.correcao = correcao;
    }

    public Discrepancia() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public String getDiscrepancia() {
        return discrepancia;
    }

    public void setDiscrepancia(String discrepancia) {
        this.discrepancia = discrepancia;
    }

    public String getCorrecao() {
        return correcao;
    }

    public void setCorrecao(String correcao) {
        this.correcao = correcao;
    }
}
