package com.montyhall.api.dto;

/**
 * Corpo de POST /api/jogos/{id}/decisao
 *
 * { "trocar": true }   → troca para a outra porta fechada
 * { "trocar": false }  → fica com a porta escolhida no início
 */
public class DecisaoRequisicao {

    private Boolean trocar;

    // Construtor vazio: usado pelo Jackson para montar o objeto a partir do JSON
    public DecisaoRequisicao() {
    }

    /** Atalho para os testes: DecisaoRequisicao.comTrocar(true) */
    public static DecisaoRequisicao comTrocar(Boolean trocar) {
        DecisaoRequisicao requisicao = new DecisaoRequisicao();
        requisicao.setTrocar(trocar);
        return requisicao;
    }

    public Boolean getTrocar() {
        return trocar;
    }

    public void setTrocar(Boolean trocar) {
        this.trocar = trocar;
    }
}
