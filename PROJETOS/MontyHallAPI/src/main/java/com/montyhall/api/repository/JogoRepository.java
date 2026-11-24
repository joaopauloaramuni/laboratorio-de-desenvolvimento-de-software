package com.montyhall.api.repository;

import com.montyhall.api.model.Jogo;
import com.montyhall.api.model.StatusJogo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogoRepository extends MongoRepository<Jogo, String> {
    // Métodos de CRUD já estão disponíveis:
    // findAll, findById, save, deleteById, existsById, count

    // O Spring Data monta a consulta a partir do nome do método:

    // todas as partidas, das mais novas para as mais antigas
    List<Jogo> findAllByOrderByCriadoEmDesc();

    // partidas de uma etapa (ex.: só as FINALIZADO)
    List<Jogo> findByStatusOrderByCriadoEmDesc(StatusJogo status);

    // quantas partidas terminaram trocando (true) ou mantendo (false) a porta.
    // "trocou" só é preenchido quando a partida termina, então só conta as finalizadas.
    long countByTrocou(Boolean trocou);

    // quantas dessas partidas foram vencidas (venceu = true)
    long countByTrocouAndVenceu(Boolean trocou, Boolean venceu);
}
