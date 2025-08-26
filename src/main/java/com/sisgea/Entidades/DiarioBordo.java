package com.sisgea.Entidades;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class DiarioBordo {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "aeronave_id", nullable = false)
    private String aeronaveId;

    @Column(name = "nro_diario", nullable = false)
    private Integer nroDiario;

    @Temporal(TemporalType.DATE)
    @Column(name = "data", nullable = false)
    private Date data;

    @Column(name = "funcao_aluno")
    private String funcaoAluno;

    @Column(name = "funcao_instrutor")
    private String funcaoInstrutor;

    @Column(name = "hora_aeronave")
    private Float horaAeronave;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_decolagem")
    private Date dataDecolagem;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_pouso")
    private Date dataPouso;

    @Column(name = "local_decolagem")
    private String localDecolagem;

    @Column(name = "local_pouso")
    private String localPouso;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_corte")
    private Date dataCorte;

    @Column(name = "horas_diu")
    private Float horasDiu;

    @Column(name = "horas_not")
    private Float horasNot;

    @Column(name = "horas_vfr")
    private Float horasVfr;

    @Column(name = "horas_ifr")
    private Float horasIfr;

    @Column(name = "horas_ifr_c")
    private Float horasIfrC;

    @Column(name = "combustivel_utilizado")
    private Float combustivelUtilizado;

    @Column(name = "ciclos")
    private Integer ciclos;

    @Column(name = "pob")
    private Integer pob;

    @Column(name = "carga")
    private Float carga;

    @Column(name = "nat")
    private String nat;

    @Column(name = "ocorrencias", length = 2000)
    private String ocorrencias;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "diario_bordo_id")
    private List<Discrepancia> discrepancias;

    public DiarioBordo() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAeronaveId() {
        return aeronaveId;
    }

    public void setAeronaveId(String aeronaveId) {
        this.aeronaveId = aeronaveId;
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
}
