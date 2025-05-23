package com.sisgea.Entidades;

import java.util.Date;
import java.util.UUID;

public class Manutencao {
    private UUID id;
    private String descricao;
    private Date data_est_man;
    private String status;
    private Aeronave fk_aeronave;
    
    public Manutencao(UUID id, String descricao, Date data_est_man, String status, Aeronave fk_aeronave) {
        this.id = id;
        this.descricao = descricao;
        this.data_est_man = data_est_man;
        this.status = status;
        this.fk_aeronave = fk_aeronave;
    }
    
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Date getData_est_man() {
        return data_est_man;
    }
    public void setData_est_man(Date data_est_man) {
        this.data_est_man = data_est_man;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Aeronave getFk_aeronave() {
        return fk_aeronave;
    }

    public void setFk_aeronave(Aeronave fk_aeronave) {
        this.fk_aeronave = fk_aeronave;
    }
}
