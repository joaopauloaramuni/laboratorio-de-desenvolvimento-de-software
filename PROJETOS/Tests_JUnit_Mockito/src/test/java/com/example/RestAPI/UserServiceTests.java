package com.example.RestAPI;

import com.example.RestAPI.exception.UserAlreadyExistsException;
import com.example.RestAPI.exception.UserNotFoundException;
import com.example.RestAPI.model.UserEntity;
import com.example.RestAPI.repository.UserRepository;
import com.example.RestAPI.service.UserService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserEntity existingUser;

    @BeforeEach
    void setUp() {
        // Inicializa um usuário existente antes de cada teste
        existingUser = new UserEntity("1234", "joao", "joao@gmail.com", 33, new Date());
    }

    /**
     * Testes de operações do serviço de usuários.
     *
     * Esta classe contém testes unitários que verificam a funcionalidade do serviço de usuários,
     * incluindo a manipulação de operações CRUD (Criar, Ler, Atualizar, Excluir). Os testes abordam
     * os seguintes cenários:
     * 1. Inserção de usuários existentes e novos.
     * 2. Atualização de usuários, tanto existentes quanto não encontrados.
     * 3. Obtenção de todos os usuários e busca por ID.
     * 4. Exclusão de usuários existentes e tratamento de exclusões de usuários não encontrados.
     * 5. Busca de usuários por nome e e-mail, incluindo métodos que suportam filtros como prefixo e substring.
     */
    @Test
    void testInserirUsuarioExistente() {
        // Verifica se uma exceção é lançada ao tentar inserir um usuário que já existe
        when(userRepository.existsById(existingUser.getId())).thenReturn(true);

        Assertions.assertThrows(UserAlreadyExistsException.class, () -> {
            userService.inserir(existingUser);
        });
    }

    @Test
    void testAtualizarUsuarioNaoEncontrado() {
        // Verifica se uma exceção é lançada ao tentar atualizar um usuário que não existe
        String userId = "nonExistingUserId";
        UserEntity updatedUser = new UserEntity(userId, "User2", "user2@example.com", 25, new Date());

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.atualizar(userId, updatedUser);
        });
    }

    @Test
    void testObterTodosUsuarios() {
        // Testa se todos os usuários são retornados corretamente
        List<UserEntity> users = new ArrayList<>();
        users.add(existingUser);
        when(userRepository.findAll()).thenReturn(users);

        List<UserEntity> result = userService.obterTodos();
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(existingUser, result.get(0));
    }

    @Test
    void testObterUsuarioPorIdExistente() {
        // Verifica se um usuário é retornado corretamente pelo seu ID
        when(userRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));

        UserEntity result = userService.obterPorId(existingUser.getId());
        Assertions.assertEquals(existingUser, result);
    }

    @Test
    void testObterUsuarioPorIdNaoExistente() {
        // Verifica se uma exceção é lançada ao tentar obter um usuário que não existe
        String userId = "nonExistingUserId";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.obterPorId(userId);
        });
    }

    @Test
    void testInserirUsuarioNovo() throws UserAlreadyExistsException {
        // Testa a inserção de um novo usuário que ainda não existe
        when(userRepository.existsById(existingUser.getId())).thenReturn(false);
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        UserEntity result = userService.inserir(existingUser);
        Assertions.assertEquals(existingUser, result);
        verify(userRepository).save(existingUser); // Verifica se o método save foi chamado
    }

    @Test
    void testAtualizarUsuarioExistente() throws UserNotFoundException {
        // Verifica se um usuário existente é atualizado corretamente
        String userId = existingUser.getId();
        UserEntity updatedUser = new UserEntity(userId, "UpdatedName", "updated@gmail.com", 30, new Date());

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        UserEntity result = userService.atualizar(userId, updatedUser);
        Assertions.assertEquals(existingUser.getNome(), result.getNome());
        Assertions.assertEquals(existingUser.getEmail(), result.getEmail());
    }

    @Test
    void testExcluirUsuarioExistente() throws UserNotFoundException {
        // Testa a exclusão de um usuário existente
        when(userRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));

        userService.excluir(existingUser.getId());

        verify(userRepository).delete(existingUser); // Verifica se o método delete foi chamado
    }

    @Test
    void testExcluirUsuarioNaoEncontrado() {
        // Verifica se uma exceção é lançada ao tentar excluir um usuário que não existe
        String userId = "nonExistingUserId";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userService.excluir(userId);
        });
    }

    @Test
    void testBuscarPorNome() {
        // Verifica se a busca por nome retorna o usuário correto
        String name = "joao";
        List<UserEntity> users = new ArrayList<>();
        users.add(existingUser);
        when(userRepository.findByNomeIgnoreCase(name)).thenReturn(users);

        List<UserEntity> result = userService.buscarPorNome(name);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(existingUser, result.get(0));
    }

    @Test
    void testBuscarPorEmail() {
        // Verifica se a busca por e-mail retorna o usuário correto
        String email = "joao@gmail.com";
        List<UserEntity> users = new ArrayList<>();
        users.add(existingUser);
        when(userRepository.findByEmailIgnoreCase(email)).thenReturn(users);

        List<UserEntity> result = userService.buscarPorEmail(email);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(existingUser, result.get(0));
    }

    @Test
    void testBuscarPorNomeQueComecaCom() {
        // Verifica se a busca por nome que começa com um prefixo retorna o usuário correto
        String prefix = "jo";
        List<UserEntity> users = new ArrayList<>();
        users.add(existingUser);
        when(userRepository.findByNomeStartingWithIgnoreCase(prefix)).thenReturn(users);

        List<UserEntity> result = userService.buscarPorNomeQueComecaCom(prefix);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(existingUser, result.get(0));
    }

    @Test
    void testBuscarPorNomeQueContem() {
        // Verifica se a busca por nome que contém uma substring retorna o usuário correto
        String substring = "oa";
        List<UserEntity> users = new ArrayList<>();
        users.add(existingUser);
        when(userRepository.findByNomeContainingIgnoreCase(substring)).thenReturn(users);

        List<UserEntity> result = userService.buscarPorNomeQueContem(substring);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(existingUser, result.get(0));
    }

    @Test
    void testBuscarPorNomeComVariosResultados() {
        // Verifica se a busca por nome que contém retorna múltiplos usuários corretamente
        UserEntity user2 = new UserEntity("5678", "Joana", "joana@gmail.com", 28, new Date());
        List<UserEntity> users = new ArrayList<>();
        users.add(existingUser);
        users.add(user2);
        when(userRepository.findByNomeContainingIgnoreCase("jo")).thenReturn(users);

        List<UserEntity> result = userService.buscarPorNomeQueContem("jo");
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(existingUser, result.get(0));
        Assertions.assertEquals(user2, result.get(1));
    }

    @Test
    void testBuscarPorEmailComVariosResultados() {
        // Verifica se a busca por e-mail com vários resultados retorna usuários corretamente
        UserEntity user2 = new UserEntity("5678", "Maria", "maria@gmail.com", 30, new Date());
        List<UserEntity> users = new ArrayList<>();
        users.add(existingUser);
        users.add(user2);

        // O repositório retorna ambos os usuários
        when(userRepository.findByEmailIgnoreCase("maria@gmail.com")).thenReturn(users);

        List<UserEntity> result = userService.buscarPorEmail("maria@gmail.com");

        // Alterar para esperar dois resultados
        Assertions.assertEquals(2, result.size()); // Agora espera dois resultados
        Assertions.assertTrue(result.contains(user2)); // Verifica se user2 está na lista
        Assertions.assertTrue(result.contains(existingUser)); // Verifica se existingUser também está na lista
    }

}
