package com.montyhall.api.dto;

/**
 * Corpo de POST /api/jogos/{id}/escolha
 *
 * { "porta": 1 }
 */
public class EscolhaRequisicao {

    private Integer porta;

    // Construtor vazio: usado pelo Jackson para montar o objeto a partir do JSON
    public EscolhaRequisicao() {
    }

    /** Atalho para os testes: EscolhaRequisicao.daPorta(1) */
    public static EscolhaRequisicao daPorta(Integer porta) {
        EscolhaRequisicao requisicao = new EscolhaRequisicao();
        requisicao.setPorta(porta);
        return requisicao;
    }

    public Integer getPorta() {
        return porta;
    }

    public void setPorta(Integer porta) {
        this.porta = porta;
    }
}
