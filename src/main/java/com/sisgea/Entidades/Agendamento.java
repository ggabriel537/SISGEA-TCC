package com.sisgea.Entidades;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class Agendamento {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "aeronave_id", referencedColumnName = "matricula")
    private Aeronave aeronave;

    @ManyToOne
    @JoinColumn(name = "aluno_id", referencedColumnName = "cpf")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "instrutor_id", referencedColumnName = "cpf")
    private Instrutor instrutor;

    @Column(name = "partida")
    private String partida;

    @Column(name = "destino")
    private String destino;

    @Column(name = "tipo_voo")
    private String tipo_voo;

    @Column(name = "status")
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_agendamento")
    private Date data_agendamento;

    public Agendamento() {
        this.id = UUID.randomUUID();
    }

    public Agendamento(Aeronave aeronave, Aluno aluno, Instrutor instrutor, String partida, String destino,
            String tipo_voo, String status, Date data_agendamento) {
        this();
        this.aeronave = aeronave;
        this.aluno = aluno;
        this.instrutor = instrutor;
        this.partida = partida;
        this.destino = destino;
        this.tipo_voo = tipo_voo;
        this.status = status;
        this.data_agendamento = data_agendamento;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Aeronave getAeronave() {
        return aeronave;
    }

    public void setAeronave(Aeronave aeronave) {
        this.aeronave = aeronave;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(Instrutor instrutor) {
        this.instrutor = instrutor;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getTipo_voo() {
        return tipo_voo;
    }

    public void setTipo_voo(String tipo_voo) {
        this.tipo_voo = tipo_voo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getData_agendamento() {
        return data_agendamento;
    }

    public void setData_agendamento(Date data_agendamento) {
        this.data_agendamento = data_agendamento;
    }
}
