package com.taskflow.repository;

import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Camada de Repository: responsavel exclusivamente pela comunicacao com o
 * banco de dados. Nao contem regra de negocio - apenas acesso a dados.
 *
 * O Spring Data JPA gera a implementacao automaticamente em tempo de execucao.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByStatusOrderByDeadlineAsc(TaskStatus status);

    List<Task> findAllByOrderByDeadlineAsc();
}
