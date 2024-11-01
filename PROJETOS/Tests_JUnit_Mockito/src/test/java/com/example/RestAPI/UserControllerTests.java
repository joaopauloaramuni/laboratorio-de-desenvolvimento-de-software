package com.example.RestAPI;

import com.example.RestAPI.controller.UserController;
import com.example.RestAPI.model.UserEntity;
import com.example.RestAPI.service.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    // Métodos CRUD

    /**
     * Testes de operações CRUD para o controlador de usuários.
     *
     * Este grupo de testes verifica se as operações de criar, ler, atualizar e excluir
     * usuários no controlador funcionam corretamente. Os testes incluem:
     * 1. Obtenção de todos os usuários.
     * 2. Obtenção de um usuário por ID.
     * 3. Inserção de um novo usuário.
     * 4. Atualização de um usuário existente.
     * 5. Exclusão de um usuário.
     */
    @Test
    void testObterTodos() {
        // Simular dados de usuário
        List<UserEntity> userList = Arrays.asList(
                new UserEntity("1", "User1", "user1@example.com", 30, new Date()),
                new UserEntity("2", "User2", "user2@example.com", 25, new Date())
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
        UserEntity user = new UserEntity("1", "User1", "user1@example.com", 30, new Date());

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
        UserEntity newUser = new UserEntity("1", "User1", "user1@example.com", 30, new Date());

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
        UserEntity updatedUser = new UserEntity("1", "UpdatedUser", "updateduser@example.com", 31, new Date());

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

    // Métodos utilizando consultas personalizadas

    /**
     * Testes de consulta personalizados para operações de usuário no controlador.
     *
     * Este grupo de testes verifica se os métodos de busca no controlador
     * retornam corretamente as listas de usuários esperadas nas seguintes situações:
     * 1. Busca de usuários pelo nome.
     * 2. Busca de usuários pelo email.
     * 3. Busca de usuários por nome e email.
     * 4. Busca de usuários cujo nome começa com uma substring.
     * 5. Busca de usuários cujo nome contém uma substring.
     */
    @Test
    void testBuscarUsuariosPorNome() {
        // Simular dados de usuário
        List<UserEntity> userList = Arrays.asList(
                new UserEntity("1", "João", "joao@gmail.com", 30, new Date()),
                new UserEntity("2", "Joana", "joana@gmail.com", 25, new Date())
        );

        // Simular comportamento do serviço
        when(userService.buscarPorNome("João")).thenReturn(userList);

        // Chamar o método do controlador
        List<UserEntity> result = userController.buscarUsuariosPorNome("João");

        // Verificar se o resultado é o esperado
        assertEquals(userList, result);
    }

    @Test
    void testBuscarUsuariosPorEmail() {
        // Simular dados de usuário
        List<UserEntity> userList = Arrays.asList(
                new UserEntity("1", "João", "joao@gmail.com", 30, new Date())
        );

        // Simular comportamento do serviço
        when(userService.buscarPorEmail("joao@gmail.com")).thenReturn(userList);

        // Chamar o método do controlador
        List<UserEntity> result = userController.buscarUsuariosPorEmail("joao@gmail.com");

        // Verificar se o resultado é o esperado
        assertEquals(userList, result);
    }

    @Test
    void testBuscarUsuariosPorNomeEEmail() {
        // Simular dados de usuário
        List<UserEntity> userList = Arrays.asList(
                new UserEntity("1", "João", "joao@gmail.com", 30, new Date())
        );

        // Simular comportamento do serviço
        when(userService.buscarPorNomeEEmail("João", "joao@gmail.com")).thenReturn(userList);

        // Chamar o método do controlador
        List<UserEntity> result = userController.buscarUsuariosPorNomeEEmail("João", "joao@gmail.com");

        // Verificar se o resultado é o esperado
        assertEquals(userList, result);
    }

    @Test
    void testBuscarUsuariosPorNomeQueComecaCom() {
        // Simular dados de usuário
        List<UserEntity> userList = Arrays.asList(
                new UserEntity("1", "João", "joao@gmail.com", 30, new Date())
        );

        // Simular comportamento do serviço
        when(userService.buscarPorNomeQueComecaCom("J")).thenReturn(userList);

        // Chamar o método do controlador
        List<UserEntity> result = userController.buscarUsuariosPorNomeQueComecaCom("J");

        // Verificar se o resultado é o esperado
        assertEquals(userList, result);
    }

    @Test
    void testBuscarUsuariosPorNomeQueContem() {
        // Simular dados de usuário
        List<UserEntity> userList = Arrays.asList(
                new UserEntity("1", "João", "joao@gmail.com", 30, new Date())
        );

        // Simular comportamento do serviço
        when(userService.buscarPorNomeQueContem("oa")).thenReturn(userList);

        // Chamar o método do controlador
        List<UserEntity> result = userController.buscarUsuariosPorNomeQueContem("oa");

        // Verificar se o resultado é o esperado
        assertEquals(userList, result);
    }

    // Métodos adicionais para consultas com @Query

    /**
     * Testes de consulta para operações de usuário no controlador.
     *
     * Este grupo de testes verifica se os métodos de busca no controlador
     * retornam corretamente as listas de usuários esperadas nas seguintes situações:
     * 1. Busca por nome exato.
     * 2. Busca por email que contém uma substring.
     * 3. Busca por nome começando com uma substring.
     * 4. Busca por nome contendo uma substring.
     * 5. Busca por nome e email exatos.
     * 6. Busca por usuários com idade maior ou igual a um valor específico.
     * 7. Busca por usuários cadastrados após uma determinada data.
     * 8. Busca por usuários com idade dentro de um intervalo.
     * 9. Busca por usuários cujo nome ou email contém uma substring.
     * 10. Busca por usuários com idade ou cadastrados antes de uma determinada data.
     * 11. Busca por usuários cujo nome não contém uma substring.
     */
    @Test
    void testBuscarPorNomeExato() {
        List<UserEntity> userList = Arrays.asList(
                new UserEntity("1", "João", "joao@gmail.com", 30, new Date())
        );

        when(userService.buscarPorNomeExato("João")).thenReturn(userList);
        List<UserEntity> result = userController.buscarPorNomeExato("João");

        assertEquals(userList, result);
    }

    @Test
    void testBuscarPorEmailContem() {
        List<UserEntity> userList = Arrays.asList(
                new UserEntity("1", "João", "joao@gmail.com", 30, new Date())
        );

        when(userService.buscarPorEmailContem("joao@gmail.com")).thenReturn(userList);
        List<UserEntity> result = userController.buscarPorEmailContem("joao@gmail.com");

        assertEquals(userList, result);
    }

    @Test
    void testBuscarPorNomeComecandoComQuery() {
        List<UserEntity> userList = Arrays.asList(
                new UserEntity("1", "João", "joao@gmail.com", 30, new Date())
        );

        when(userService.buscarPorNomeComecandoComQuery("J")).thenReturn(userList);
        List<UserEntity> result = userController.buscarPorNomeComecandoComQuery("J");

        assertEquals(userList, result);
    }

    @Test
    void testBuscarPorNomeContemQuery() {
        List<UserEntity> userList = Arrays.asList(
                new UserEntity("1", "João", "joao@gmail.com", 30, new Date())
        );

        when(userService.buscarPorNomeContemQuery("oa")).thenReturn(userList);
        List<UserEntity> result = userController.buscarPorNomeContemQuery("oa");

        assertEquals(userList, result);
    }

    @Test
    void testBuscarPorNomeEEmailExato() {
        List<UserEntity> userList = Arrays.asList(
                new UserEntity("1", "João", "joao@gmail.com", 30, new Date())
        );

        when(userService.buscarPorNomeEEmailExato("João", "joao@gmail.com")).thenReturn(userList);
        List<UserEntity> result = userController.buscarPorNomeEEmailExato("João", "joao@gmail.com");

        assertEquals(userList, result);
    }

    @Test
    void testBuscarPorIdadeMaiorOuIgual() {
        List<UserEntity> userList = Arrays.asList(
                new UserEntity("1", "João", "joao@gmail.com", 30, new Date())
        );

        when(userService.buscarPorIdadeMaiorOuIgual(30)).thenReturn(userList);
        List<UserEntity> result = userController.buscarPorIdadeMaiorOuIgual(30);

        assertEquals(userList, result);
    }

    @Test
    void testBuscarPorDataDeCadastroDepois() {
        Date date = new Date();
        List<UserEntity> userList = Arrays.asList(
                new UserEntity("1", "João", "joao@gmail.com", 30, date)
        );

        when(userService.buscarPorDataDeCadastroDepois(date)).thenReturn(userList);
        List<UserEntity> result = userController.buscarPorDataDeCadastroDepois(date);

        assertEquals(userList, result);
    }

    @Test
    void testBuscarPorIdadeEntre() {
        List<UserEntity> userList = Arrays.asList(
                new UserEntity("1", "João", "joao@gmail.com", 30, new Date())
        );

        when(userService.buscarPorIdadeEntre(25, 35)).thenReturn(userList);
        List<UserEntity> result = userController.buscarPorIdadeEntre(25, 35);

        assertEquals(userList, result);
    }

    @Test
    void testBuscarPorNomeOuEmailContendo() {
        List<UserEntity> userList = Arrays.asList(
                new UserEntity("1", "João", "joao@gmail.com", 30, new Date())
        );

        when(userService.buscarPorNomeOuEmailContendo("Jo")).thenReturn(userList);
        List<UserEntity> result = userController.buscarPorNomeOuEmailContendo("Jo");

        assertEquals(userList, result);
    }

    @Test
    void testBuscarPorIdadeOuCadastroAntes() {
        Date date = new Date();
        List<UserEntity> userList = Arrays.asList(
                new UserEntity("1", "João", "joao@gmail.com", 30, new Date())
        );

        when(userService.buscarPorIdadeOuCadastroAntes(30, date)).thenReturn(userList);
        List<UserEntity> result = userController.buscarPorIdadeOuCadastroAntes(30, date);

        assertEquals(userList, result);
    }

    @Test
    void testBuscarPorNomeNaoContendo() {
        List<UserEntity> userList = Arrays.asList(
                new UserEntity("1", "João", "joao@gmail.com", 30, new Date())
        );

        when(userService.buscarPorNomeNaoContendo("Maria")).thenReturn(userList);
        List<UserEntity> result = userController.buscarPorNomeNaoContendo("Maria");

        assertEquals(userList, result);
    }

}
