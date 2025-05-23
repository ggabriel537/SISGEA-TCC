package com.sisgea.Entidades;

import java.sql.Date;
import java.util.UUID;

public class Discrepancia {
    private UUID id;
    private Date data;
    private String sistema;
    private String discrepancia;
    private String correcao;

    public Discrepancia(UUID id, Date data, String sistema, String discrepancia, String correcao) {
        this.id = id;
        this.data = data;
        this.sistema = sistema;
        this.discrepancia = discrepancia;
        this.correcao = correcao;
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
