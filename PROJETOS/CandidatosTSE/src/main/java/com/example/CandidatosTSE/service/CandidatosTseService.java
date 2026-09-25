package com.example.CandidatosTSE.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.CandidatosTSE.model.Candidato;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import jakarta.annotation.PostConstruct;

/**
 * Lê o CSV do TSE (consulta_cand_2026_MG.csv) uma única vez na inicialização
 * e mantém a lista de candidatos em memória para filtragem simples.
 *
 * O arquivo do TSE:
 * - separador: ";"
 * - aspas: "\""
 * - charset: ISO-8859-1 (Latin-1)
 */
@Service
public class CandidatosTseService {

    private static final String CAMINHO_CSV = "data/candidatos/consulta_cand_2026_MG.csv";

    // índices das colunas no CSV (posição 0-based no header)
    private static final int COL_SG_UF = 10;
    private static final int COL_NM_UE = 12;
    private static final int COL_DS_CARGO = 14;
    private static final int COL_SQ_CANDIDATO = 15;
    private static final int COL_NR_CANDIDATO = 16;
    private static final int COL_NM_CANDIDATO = 17;
    private static final int COL_NM_URNA_CANDIDATO = 18;
    private static final int COL_DS_SITUACAO_CANDIDATURA = 23;
    private static final int COL_SG_PARTIDO = 26;
    private static final int COL_NM_PARTIDO = 27;
    private static final int COL_DT_NASCIMENTO = 36;
    private static final int COL_DS_GENERO = 39;
    private static final int COL_DS_GRAU_INSTRUCAO = 41;
    private static final int COL_DS_OCUPACAO = 47;
    private static final int COL_NR_CPF_CANDIDATO = 20;

    private static final int MIN_COLUNAS = 48;

    /**
     * Pasta onde ficam as fotos, dentro de src/main/resources/static (raiz do
     * classpath é "static/...").
     */
    private static final String PASTA_IMAGENS_CANDIDATOS = "static/images/candidatos/";

    private List<Candidato> candidatos = new ArrayList<>();

    @PostConstruct
    public void carregarCsv() {
        List<Candidato> lista = new ArrayList<>();

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .withQuoteChar('"')
                .build();

        try (Reader reader = new InputStreamReader(
                new ClassPathResource(CAMINHO_CSV).getInputStream(), StandardCharsets.ISO_8859_1);
                CSVReader csvReader = new CSVReaderBuilder(reader)
                        .withCSVParser(parser)
                        .withSkipLines(1) // pula o cabeçalho
                        .build()) {

            String[] linha;
            while ((linha = csvReader.readNext()) != null) {
                if (linha.length < MIN_COLUNAS) {
                    continue;
                }
                Candidato c = new Candidato();
                c.setUf(valor(linha, COL_SG_UF));
                c.setMunicipio(valor(linha, COL_NM_UE));
                c.setCargo(valor(linha, COL_DS_CARGO));
                c.setSqCandidato(valor(linha, COL_SQ_CANDIDATO));
                c.setNrCandidato(valor(linha, COL_NR_CANDIDATO));
                c.setNomeCandidato(valor(linha, COL_NM_CANDIDATO));
                c.setNomeUrna(valor(linha, COL_NM_URNA_CANDIDATO));
                c.setSituacaoCandidatura(valor(linha, COL_DS_SITUACAO_CANDIDATURA));
                c.setSiglaPartido(valor(linha, COL_SG_PARTIDO));
                c.setNomePartido(valor(linha, COL_NM_PARTIDO));
                c.setDtNascimento(valor(linha, COL_DT_NASCIMENTO));
                c.setGenero(valor(linha, COL_DS_GENERO));
                c.setGrauInstrucao(valor(linha, COL_DS_GRAU_INSTRUCAO));
                c.setOcupacao(valor(linha, COL_DS_OCUPACAO));
                c.setNrCpfCandidato(valor(linha, COL_NR_CPF_CANDIDATO));

                lista.add(c);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Erro ao ler o CSV de candidatos: " + CAMINHO_CSV, e);
        }

        // Quando uma candidatura não tem foto própria, tenta usar a foto de
        // outra candidatura da mesma pessoa (mesmo nome + número) — comum em
        // Senador, onde titular e suplentes são registros separados no TSE.
        resolverFotosPorCpf(lista);

        // ordena por nome de urna, só para ficar mais agradável na tela
        lista.sort(Comparator.comparing(Candidato::getNomeUrna, Comparator.nullsLast(String::compareTo)));

        this.candidatos = lista;
    }

    /**
     * Agrupa os candidatos por "nome civil + número de candidato" e, para quem
     * não tem foto própria disponível no disco, aponta para a foto de outra
     * candidatura da mesma pessoa (se existir).
     *
     * OBS: usamos nome+número em vez do CPF porque o TSE mascara o CPF (vira "-4")
     * em algumas candidaturas — tipicamente a linha do "titular" de Senador —
     * enquanto as linhas de suplente mantêm o CPF real. Nome civil completo e
     * número do candidato continuam preenchidos e iguais entre titular/suplentes.
     */
    private void resolverFotosPorCpf(List<Candidato> lista) {
        Map<String, List<Candidato>> porPessoa = lista.stream()
                .filter(c -> c.getNomeCandidato() != null && !c.getNomeCandidato().isBlank())
                .filter(c -> c.getNrCandidato() != null && !c.getNrCandidato().isBlank())
                .collect(Collectors.groupingBy(
                        c -> c.getNomeCandidato().trim().toUpperCase(Locale.forLanguageTag("pt-BR"))
                                + "|" + c.getNrCandidato().trim()));

        for (List<Candidato> grupo : porPessoa.values()) {
            if (grupo.size() < 2) {
                continue; // só uma candidatura para essa pessoa, nada a fazer
            }

            String sqComFotoDisponivel = grupo.stream()
                    .filter(c -> fotoExisteNoDisco(c.getSqCandidato()))
                    .map(Candidato::getSqCandidato)
                    .findFirst()
                    .orElse(null);

            if (sqComFotoDisponivel == null) {
                continue; // ninguém do grupo tem foto disponível, não há o que emprestar
            }

            for (Candidato c : grupo) {
                if (!fotoExisteNoDisco(c.getSqCandidato())) {
                    c.setSqCandidatoParaFoto(sqComFotoDisponivel);
                }
            }
        }
    }

    /**
     * Verifica se o arquivo FMG<sqCandidato>_div.jpg realmente existe no classpath.
     */
    private boolean fotoExisteNoDisco(String sqCandidato) {
        String nomeArquivo = "FMG" + sqCandidato + "_div.jpg";
        return new ClassPathResource(PASTA_IMAGENS_CANDIDATOS + nomeArquivo).exists();
    }

    private String valor(String[] linha, int indice) {
        if (indice >= linha.length) {
            return "";
        }
        String v = linha[indice];
        return v == null ? "" : v.trim();
    }

    public List<Candidato> listarTodos() {
        return candidatos;
    }

    /**
     * Filtro simples: qualquer parâmetro nulo/vazio é ignorado.
     * A busca textual (texto) procura em nome, nome de urna e número do candidato.
     */
    public List<Candidato> filtrar(String cargo, String partido, String texto) {
        String textoBusca = normalizar(texto);

        return candidatos.stream()
                .filter(c -> vazioOuIgual(cargo, c.getCargo()))
                .filter(c -> vazioOuIgual(partido, c.getSiglaPartido()))
                .filter(c -> textoBusca.isEmpty() || contemTexto(c, textoBusca))
                .collect(Collectors.toList());
    }

    private boolean vazioOuIgual(String filtro, String valorCandidato) {
        return filtro == null || filtro.isBlank() || filtro.equalsIgnoreCase(valorCandidato);
    }

    private boolean contemTexto(Candidato c, String textoBusca) {
        return normalizar(c.getNomeCandidato()).contains(textoBusca)
                || normalizar(c.getNomeUrna()).contains(textoBusca)
                || normalizar(c.getNrCandidato()).contains(textoBusca);
    }

    private String normalizar(String s) {
        return s == null ? "" : s.trim().toLowerCase(Locale.forLanguageTag("pt-BR"));
    }

    /** Lista de cargos distintos (ordenada) para popular o <select> do filtro. */
    public List<String> listarCargos() {
        return candidatos.stream()
                .map(Candidato::getCargo)
                .filter(Objects::nonNull)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toCollection(TreeSet::new))
                .stream().toList();
    }

    /** Lista de partidos distintos (siglas) para popular o <select> do filtro. */
    public List<String> listarPartidos() {
        return candidatos.stream()
                .map(Candidato::getSiglaPartido)
                .filter(Objects::nonNull)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toCollection(TreeSet::new))
                .stream().toList();
    }
}