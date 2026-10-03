package com.taskflow;

import com.taskflow.model.Task;
import com.taskflow.model.TaskStatus;
import com.taskflow.repository.TaskRepository;
import com.taskflow.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Teste simples demonstrando como a camada de Service pode ser testada de
 * forma isolada, "mockando" o Repository (sem precisar de banco real).
 */
class TaskServiceTest {

    private TaskRepository taskRepository;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        taskService = new TaskService(taskRepository);
    }

    @Test
    void deveLancarErroQuandoPrazoEstaNoPassado() {
        Task task = new Task("Tarefa teste", "desc", LocalDate.now().minusDays(1), TaskStatus.PENDENTE);

        assertThrows(IllegalArgumentException.class, () -> taskService.create(task));
        verify(taskRepository, never()).save(any());
    }

    @Test
    void deveCriarTarefaComPrazoValido() {
        Task task = new Task("Tarefa teste", "desc", LocalDate.now().plusDays(3), null);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task created = taskService.create(task);

        assertEquals(TaskStatus.PENDENTE, created.getStatus());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void deveLancarErroQuandoTarefaNaoExiste() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taskService.findById(99L));
    }
}
