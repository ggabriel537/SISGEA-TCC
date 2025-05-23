package com.sisgea.Entidades;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DiarioBordo {
    private UUID id;
    private Integer nroDiario;
    private Date data;
    private String funcaoAluno;
    private String funcaoInstrutor;
    private Float horaAeronave;
    private Date dataDecolagem;
    private Date dataPouso;
    private String localDecolagem;
    private String localPouso;
    private Date dataCorte;
    private Float horasDiu;
    private Float horasNot;
    private Float horasVfr;
    private Float horasIfr;
    private Float horasIfrC;
    private Float combustivelUtilizado;
    private Integer ciclos;
    private Integer pob; // POB -> Pessoas a Bordo
    private Float carga;
    private String nat;  // NAT -> Natureza do voo
    private String ocorrencias;
    private List<Discrepancia> discrepancias;
    private Agendamento agendamento;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getNroDiario() {
        return nroDiario;
    }

    public void setNroDiario(Integer nroDiario) {
        this.nroDiario = nroDiario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getFuncaoAluno() {
        return funcaoAluno;
    }

    public void setFuncaoAluno(String funcaoAluno) {
        this.funcaoAluno = funcaoAluno;
    }

    public String getFuncaoInstrutor() {
        return funcaoInstrutor;
    }

    public void setFuncaoInstrutor(String funcaoInstrutor) {
        this.funcaoInstrutor = funcaoInstrutor;
    }

    public Float getHoraAeronave() {
        return horaAeronave;
    }

    public void setHoraAeronave(Float horaAeronave) {
        this.horaAeronave = horaAeronave;
    }

    public Date getDataDecolagem() {
        return dataDecolagem;
    }

    public void setDataDecolagem(Date dataDecolagem) {
        this.dataDecolagem = dataDecolagem;
    }

    public Date getDataPouso() {
        return dataPouso;
    }

    public void setDataPouso(Date dataPouso) {
        this.dataPouso = dataPouso;
    }

    public String getLocalDecolagem() {
        return localDecolagem;
    }

    public void setLocalDecolagem(String localDecolagem) {
        this.localDecolagem = localDecolagem;
    }

    public String getLocalPouso() {
        return localPouso;
    }

    public void setLocalPouso(String localPouso) {
        this.localPouso = localPouso;
    }

    public Date getDataCorte() {
        return dataCorte;
    }

    public void setDataCorte(Date dataCorte) {
        this.dataCorte = dataCorte;
    }

    public Float getHorasDiu() {
        return horasDiu;
    }

    public void setHorasDiu(Float horasDiu) {
        this.horasDiu = horasDiu;
    }

    public Float getHorasNot() {
        return horasNot;
    }

    public void setHorasNot(Float horasNot) {
        this.horasNot = horasNot;
    }

    public Float getHorasVfr() {
        return horasVfr;
    }

    public void setHorasVfr(Float horasVfr) {
        this.horasVfr = horasVfr;
    }

    public Float getHorasIfr() {
        return horasIfr;
    }

    public void setHorasIfr(Float horasIfr) {
        this.horasIfr = horasIfr;
    }

    public Float getHorasIfrC() {
        return horasIfrC;
    }

    public void setHorasIfrC(Float horasIfrC) {
        this.horasIfrC = horasIfrC;
    }

    public Float getCombustivelUtilizado() {
        return combustivelUtilizado;
    }

    public void setCombustivelUtilizado(Float combustivelUtilizado) {
        this.combustivelUtilizado = combustivelUtilizado;
    }

    public Integer getCiclos() {
        return ciclos;
    }

    public void setCiclos(Integer ciclos) {
        this.ciclos = ciclos;
    }

    public Integer getPob() {
        return pob;
    }

    public void setPob(Integer pob) {
        this.pob = pob;
    }

    public Float getCarga() {
        return carga;
    }

    public void setCarga(Float carga) {
        this.carga = carga;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    public String getOcorrencias() {
        return ocorrencias;
    }

    public void setOcorrencias(String ocorrencias) {
        this.ocorrencias = ocorrencias;
    }

    public List<Discrepancia> getDiscrepancias() {
        return discrepancias;
    }

    public void setDiscrepancias(List<Discrepancia> discrepancias) {
        this.discrepancias = discrepancias;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }
}