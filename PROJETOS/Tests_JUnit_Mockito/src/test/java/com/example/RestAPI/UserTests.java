package com.example.RestAPI;

import com.example.RestAPI.controller.UserController;
import com.example.RestAPI.model.UserEntity;
import com.example.RestAPI.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes={com.example.RestAPI.application.RestApiApplication.class})
class UserTests {

    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @Test
    void testObterTodos() {
        // Simular dados de usuário
        List<UserEntity> userList = Arrays.asList(
                new UserEntity("1", "User1", "user1@example.com"),
                new UserEntity("2", "User2", "user2@example.com")
        );

        // Simular comportamento do serviço
        when(userService.obterTodos()).thenReturn(userList);

        // Chamar o método do controlador
        List<UserEntity> result = userController.obterTodos();

        // Verificar se o resultado é o esperado
        assertEquals(userList, result);
    }

    @Test
    void testObterPorId() {
        // Simular dados de usuário
        UserEntity user = new UserEntity("1", "User1",
                "user1@example.com");

        // Simular comportamento do serviço
        when(userService.obterPorId("1")).thenReturn(user);

        // Chamar o método do controlador
        UserEntity result = userController.obterPorId("1");

        // Verificar se o resultado é o esperado
        assertEquals(user, result);
    }

    @Test
    void testInserir() {
        // Simular dados de usuário
        UserEntity newUser = new UserEntity("1", "User1",
                "user1@example.com");

        // Simular comportamento do serviço
        when(userService.inserir(newUser)).thenReturn(newUser);

        // Chamar o método do controlador
        UserEntity result = userController.inserir(newUser);

        // Verificar se o resultado é o esperado
        assertEquals(newUser, result);
    }

    @Test
    void testAtualizar() {
        // Simular dados de usuário
        UserEntity updatedUser = new UserEntity("1", "UpdatedUser",
                "updateduser@example.com");

        // Simular comportamento do serviço
        when(userService.atualizar("1", updatedUser)).thenReturn(updatedUser);

        // Chamar o método do controlador
        UserEntity result = userController.atualizar("1", updatedUser);

        // Verificar se o resultado é o esperado
        assertEquals(updatedUser, result);
    }

    @Test
    void testExcluir() {

        // Simular comportamento do serviço
        doNothing().when(userService).excluir("1");

        // Chamar o método do controlador
        userController.excluir("1");

        // Verificar se o método de serviço foi chamado
        verify(userService, times(1)).excluir("1");
    }

    @Test
    void testSimularErro(){
        // Simula o comportamento do método add para retornar 5 quando chamado com argumentos 2 e 3
        when(userService.somar(2, 3)).thenReturn(5);

        // Chama o método que deveria retornar 5
        int result = userService.somar(2, 3);

        // Aserção que verifica se o resultado é 5
        assertEquals(5, result, "O resultado da adição não é o esperado.");
    }
}







