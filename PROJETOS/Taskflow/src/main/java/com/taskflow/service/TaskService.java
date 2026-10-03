package com.taskflow.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.taskflow.exception.ResourceNotFoundException;
import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;
import com.taskflow.repository.TaskRepository;

/**
 * Camada de Service: concentra as regras de negocio do TaskFlow.
 *
 * O Controller NAO deve conter logica de negocio - ele apenas recebe a
 * requisicao HTTP e delega para esta camada, que por sua vez usa o
 * Repository apenas para persistencia.
 */
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> listAll() {
        return taskRepository.findAllByOrderByDeadlineAsc();
    }

    public List<Task> listByStatus(TaskStatus status) {
        return taskRepository.findByStatusOrderByDeadlineAsc(status);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa nao encontrada com id: " + id));
    }

    public Task create(Task task) {
        validateDeadline(task.getDeadline());
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.PENDENTE);
        }
        return taskRepository.save(task);
    }

    public Task update(Long id, Task taskDetails) {
        Task existing = findById(id);
        validateDeadline(taskDetails.getDeadline());

        existing.setTitle(taskDetails.getTitle());
        existing.setDescription(taskDetails.getDescription());
        existing.setDeadline(taskDetails.getDeadline());
        if (taskDetails.getStatus() != null) {
            existing.setStatus(taskDetails.getStatus());
        }
        return taskRepository.save(existing);
    }

    /**
     * Regra de negocio: mudanca de status segue um fluxo simples
     * (PENDENTE -> EM_ANDAMENTO -> CONCLUIDA), mas permite tambem reabrir
     * uma tarefa concluida caso necessario.
     */
    public Task updateStatus(Long id, TaskStatus newStatus) {
        Task task = findById(id);
        if (newStatus == null) {
            throw new IllegalArgumentException("O novo status e obrigatorio");
        }
        task.setStatus(newStatus);
        return taskRepository.save(task);
    }

    public void delete(Long id) {
        Task task = findById(id);
        taskRepository.delete(task);
    }

    /**
     * Regra de negocio: valida que o prazo informado nao esta no passado.
     */
    private void validateDeadline(LocalDate deadline) {
        if (deadline == null) {
            throw new IllegalArgumentException("O prazo (deadline) e obrigatorio");
        }
        if (deadline.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("O prazo (deadline) nao pode estar no passado");
        }
    }

    /**
     * Regra de negocio: gera um relatorio simples de produtividade,
     * contando tarefas por status e calculando o percentual concluido.
     */
    public Map<String, Object> productivityReport() {
        List<Task> all = taskRepository.findAll();
        long total = all.size();

        Map<TaskStatus, Long> countByStatus = all.stream()
                .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));

        long concluidas = countByStatus.getOrDefault(TaskStatus.CONCLUIDA, 0L);
        double percentualConcluido = total == 0 ? 0.0 : (concluidas * 100.0) / total;

        long atrasadas = all.stream()
                .filter(t -> t.getStatus() != TaskStatus.CONCLUIDA)
                .filter(t -> t.getDeadline() != null && t.getDeadline().isBefore(LocalDate.now()))
                .count();

        return Map.of(
                "totalTarefas", total,
                "pendentes", countByStatus.getOrDefault(TaskStatus.PENDENTE, 0L),
                "emAndamento", countByStatus.getOrDefault(TaskStatus.EM_ANDAMENTO, 0L),
                "concluidas", concluidas,
                "atrasadas", atrasadas,
                "percentualConcluido", Math.round(percentualConcluido * 100.0) / 100.0
        );
    }
}
