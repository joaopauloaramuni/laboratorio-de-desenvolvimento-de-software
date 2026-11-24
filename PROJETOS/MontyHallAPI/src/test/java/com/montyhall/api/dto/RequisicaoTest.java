package com.montyhall.api.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EscolhaRequisicao e DecisaoRequisicao")
class RequisicaoTest {

    @Test
    @DisplayName("EscolhaRequisicao: construtor vazio, setter e atalho daPorta")
    void escolha() {
        EscolhaRequisicao vazia = new EscolhaRequisicao();
        assertNull(vazia.getPorta());

        vazia.setPorta(3);
        assertEquals(3, vazia.getPorta());

        assertEquals(1, EscolhaRequisicao.daPorta(1).getPorta());
        assertNull(EscolhaRequisicao.daPorta(null).getPorta());
    }

    @Test
    @DisplayName("DecisaoRequisicao: construtor vazio, setter e atalho comTrocar")
    void decisao() {
        DecisaoRequisicao vazia = new DecisaoRequisicao();
        assertNull(vazia.getTrocar());

        vazia.setTrocar(false);
        assertFalse(vazia.getTrocar());

        assertTrue(DecisaoRequisicao.comTrocar(true).getTrocar());
        assertNull(DecisaoRequisicao.comTrocar(null).getTrocar());
    }
}
