package com.example.CandidatosTSE.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Representa um candidato lido do arquivo consulta_cand_2026_MG.csv (TSE).
 * Somente os campos usados na tela estão mapeados; o CSV tem muitas outras colunas.
 */
public class Candidato {

    private String sqCandidato;      // SQ_CANDIDATO -> usado para achar a foto: FMG<SQ_CANDIDATO>_div.jpg
    private String nrCandidato;      // NR_CANDIDATO
    private String nomeCandidato;    // NM_CANDIDATO
    private String nomeUrna;         // NM_URNA_CANDIDATO
    private String cargo;            // DS_CARGO
    private String siglaPartido;     // SG_PARTIDO
    private String nomePartido;      // NM_PARTIDO
    private String uf;               // SG_UF
    private String municipio;        // NM_UE
    private String situacaoCandidatura; // DS_SITUACAO_CANDIDATURA
    private String genero;           // DS_GENERO
    private String grauInstrucao;    // DS_GRAU_INSTRUCAO
    private String ocupacao;         // DS_OCUPACAO
    private String dtNascimento;     // DT_NASCIMENTO (formato dd/MM/yyyy)
    private String nrCpfCandidato;   // NR_CPF_CANDIDATO -> usado para agrupar candidaturas da mesma pessoa (titular/suplente)

    /**
     * Normalmente igual a sqCandidato. Só é diferente quando esta candidatura
     * específica não tem foto própria, mas outra candidatura da mesma pessoa
     * (mesmo CPF, ex.: titular/suplente de Senador) tem — nesse caso apontamos
     * para a foto dela. Ver CandidatosTseService.resolverFotosPorCpf().
     */
    private String sqCandidatoParaFoto;

    public Candidato() {
    }

    public String getSqCandidato() {
        return sqCandidato;
    }

    public void setSqCandidato(String sqCandidato) {
        this.sqCandidato = sqCandidato;
    }

    public String getNrCandidato() {
        return nrCandidato;
    }

    public void setNrCandidato(String nrCandidato) {
        this.nrCandidato = nrCandidato;
    }

    public String getNomeCandidato() {
        return nomeCandidato;
    }

    public void setNomeCandidato(String nomeCandidato) {
        this.nomeCandidato = nomeCandidato;
    }

    public String getNomeUrna() {
        return nomeUrna;
    }

    public void setNomeUrna(String nomeUrna) {
        this.nomeUrna = nomeUrna;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSiglaPartido() {
        return siglaPartido;
    }

    public void setSiglaPartido(String siglaPartido) {
        this.siglaPartido = siglaPartido;
    }

    public String getNomePartido() {
        return nomePartido;
    }

    public void setNomePartido(String nomePartido) {
        this.nomePartido = nomePartido;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getSituacaoCandidatura() {
        return situacaoCandidatura;
    }

    public void setSituacaoCandidatura(String situacaoCandidatura) {
        this.situacaoCandidatura = situacaoCandidatura;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getGrauInstrucao() {
        return grauInstrucao;
    }

    public void setGrauInstrucao(String grauInstrucao) {
        this.grauInstrucao = grauInstrucao;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public String getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(String dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getNrCpfCandidato() {
        return nrCpfCandidato;
    }

    public void setNrCpfCandidato(String nrCpfCandidato) {
        this.nrCpfCandidato = nrCpfCandidato;
    }

    public void setSqCandidatoParaFoto(String sqCandidatoParaFoto) {
        this.sqCandidatoParaFoto = sqCandidatoParaFoto;
    }

    /**
     * Nome do arquivo de foto conforme o padrão do TSE: FMG<SQ_CANDIDATO>_div.jpg
     * Ex.: SQ_CANDIDATO = 130002538303 -> FMG130002538303_div.jpg
     *
     * Usa sqCandidatoParaFoto quando definido (foto emprestada de outra
     * candidatura da mesma pessoa); caso contrário, usa o próprio sqCandidato.
     */
    public String getNomeArquivoFoto() {
        String sq = (sqCandidatoParaFoto != null) ? sqCandidatoParaFoto : sqCandidato;
        return "FMG" + sq + "_div.jpg";
    }

    private static final DateTimeFormatter FORMATO_DATA_TSE = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Idade calculada a partir de DT_NASCIMENTO. Retorna -1 se a data vier
     * ausente/inválida (ex.: "#NULO"), para que a tela simplesmente não exiba nada.
     */
    public int getIdade() {
        if (dtNascimento == null || dtNascimento.isBlank()) {
            return -1;
        }
        try {
            LocalDate nascimento = LocalDate.parse(dtNascimento, FORMATO_DATA_TSE);
            return Period.between(nascimento, LocalDate.now()).getYears();
        } catch (DateTimeParseException e) {
            return -1;
        }
    }
}