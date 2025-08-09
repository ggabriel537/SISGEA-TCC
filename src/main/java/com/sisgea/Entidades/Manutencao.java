package com.sisgea.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import java.util.Date;
import java.util.UUID;

@Entity
public class Manutencao {
    
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_est_man")
    private Date data_est_man;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "matricula_aeronave")
    private Aeronave aeronave;
    
    public Manutencao(UUID id, String descricao, Date data_est_man, String status) {
        this.id = id;
        this.descricao = descricao;
        this.data_est_man = data_est_man;
        this.status = status;
    }

    public Manutencao() {
        this.id = UUID.randomUUID();
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
}
