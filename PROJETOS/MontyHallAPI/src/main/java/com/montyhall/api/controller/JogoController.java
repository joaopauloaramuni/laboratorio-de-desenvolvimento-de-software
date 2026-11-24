package com.montyhall.api.controller;

import com.montyhall.api.dto.DecisaoRequisicao;
import com.montyhall.api.dto.EscolhaRequisicao;
import com.montyhall.api.dto.EstatisticasResposta;
import com.montyhall.api.dto.JogoResposta;
import com.montyhall.api.service.JogoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints de uma partida do Monty Hall.
 *
 * | Método | Endpoint                         | O que faz                                | Resposta        |
 * | ------ | -------------------------------- | ---------------------------------------- | --------------- |
 * | POST   | /api/jogos                       | Começa uma partida (esconde o carro)     | 201             |
 * | GET    | /api/jogos                       | Lista as partidas                        | 200             |
 * | GET    | /api/jogos?status=FINALIZADO     | Filtra pela etapa                        | 200 / 400       |
 * | GET    | /api/jogos/estatisticas          | Placar: trocar x manter                  | 200             |
 * | GET    | /api/jogos/{id}                  | Busca uma partida                        | 200 / 404       |
 * | POST   | /api/jogos/{id}/escolha          | Escolhe a porta; o apresentador abre 1   | 200 / 400 / 404 / 409 |
 * | POST   | /api/jogos/{id}/decisao          | Troca ou mantém; revela o resultado      | 200 / 400 / 404 / 409 |
 * | DELETE | /api/jogos/{id}                  | Exclui a partida                         | 204 / 404       |
 */
@RestController
@RequestMapping("/api/jogos")
public class JogoController {

    private final JogoService jogoService;

    public JogoController(JogoService jogoService) {
        this.jogoService = jogoService;
    }

    // POST http://localhost:8080/api/jogos
    @PostMapping
    public ResponseEntity<JogoResposta> novoJogo() {
        JogoResposta criado = JogoResposta.de(jogoService.novoJogo());
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    // GET http://localhost:8080/api/jogos
    // GET http://localhost:8080/api/jogos?status=FINALIZADO
    @GetMapping
    public List<JogoResposta> listar(@RequestParam(required = false) String status) {
        return jogoService.listar(status).stream()
                .map(JogoResposta::de)
                .toList();
    }

    // Caminho fixo ("/estatisticas") tem prioridade sobre "/{id}"
    @GetMapping("/estatisticas")
    public EstatisticasResposta estatisticas() {
        return jogoService.estatisticas();
    }

    @GetMapping("/{id}")
    public JogoResposta buscarPorId(@PathVariable String id) {
        return JogoResposta.de(jogoService.buscarPorId(id));
    }

    // Corpo: { "porta": 1 }
    @PostMapping("/{id}/escolha")
    public JogoResposta escolherPorta(@PathVariable String id, @RequestBody EscolhaRequisicao requisicao) {
        return JogoResposta.de(jogoService.escolherPorta(id, requisicao));
    }

    // Corpo: { "trocar": true }
    @PostMapping("/{id}/decisao")
    public JogoResposta decidir(@PathVariable String id, @RequestBody DecisaoRequisicao requisicao) {
        return JogoResposta.de(jogoService.decidir(id, requisicao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        jogoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
