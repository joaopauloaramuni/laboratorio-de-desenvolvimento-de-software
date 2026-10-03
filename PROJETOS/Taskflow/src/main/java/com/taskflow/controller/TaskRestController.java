package com.taskflow.controller;

import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;
import com.taskflow.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Camada de Controller: recebe as requisicoes HTTP referentes ao recurso
 * REST "tasks" e delega todo o processamento para a camada de Service.
 *
 * Endpoints expostos (seguindo o padrao REST):
 *   GET    /api/tasks              -> lista todas as tarefas (ou filtra por status)
 *   GET    /api/tasks/{id}         -> busca uma tarefa especifica
 *   POST   /api/tasks              -> cria uma nova tarefa
 *   PUT    /api/tasks/{id}         -> atualiza uma tarefa existente
 *   PATCH  /api/tasks/{id}/status  -> atualiza somente o status da tarefa
 *   DELETE /api/tasks/{id}         -> remove uma tarefa
 *   GET    /api/tasks/report       -> relatorio simples de produtividade
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskRestController {

    private final TaskService taskService;

    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> listTasks(
            @RequestParam(required = false) TaskStatus status) {
        List<Task> tasks = (status != null)
                ? taskService.listByStatus(status)
                : taskService.listAll();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/report")
    public ResponseEntity<Map<String, Object>> productivityReport() {
        return ResponseEntity.ok(taskService.productivityReport());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task created = taskService.create(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task task) {
        return ResponseEntity.ok(taskService.update(id, task));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        TaskStatus newStatus = TaskStatus.valueOf(body.get("status"));
        return ResponseEntity.ok(taskService.updateStatus(id, newStatus));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
