package com.example.RestAPI.service;

import com.example.RestAPI.exception.UserAlreadyExistsException;
import com.example.RestAPI.exception.UserNotFoundException;
import com.example.RestAPI.repository.UserRepository;
import com.example.RestAPI.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Métodos CRUD

    public List<UserEntity> obterTodos() {
        return userRepository.findAll();
    }

    public UserEntity obterPorId(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com o ID: " + id));
    }

    public UserEntity inserir(UserEntity user) throws UserAlreadyExistsException {
        if (userRepository.existsById(user.getId())) {
            throw new UserAlreadyExistsException("Usuário já existe com o ID: " + user.getId());
        }
        return userRepository.save(user);
    }

    public UserEntity atualizar(String id, UserEntity newUser) throws UserNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("Usuário não encontrado com o ID: " + id);
        }

        UserEntity existingUser = optionalUser.get();
        existingUser.setNome(newUser.getNome());
        existingUser.setEmail(newUser.getEmail());
        return userRepository.save(existingUser);
    }

    public void excluir(String id) throws UserNotFoundException{
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com o ID: " + id));

        userRepository.delete(existingUser);
    }

    // Métodos utilizando consultas personalizadas

    public List<UserEntity> buscarPorNome(String nome) {
        return userRepository.findByNomeIgnoreCase(nome);
    }

    public List<UserEntity> buscarPorEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    public List<UserEntity> buscarPorNomeEEmail(String nome, String email) {
        return userRepository.findByNomeAndEmailAllIgnoreCase(nome, email);
    }

    public List<UserEntity> buscarPorNomeQueComecaCom(String prefixo) {
        return userRepository.findByNomeStartingWithIgnoreCase(prefixo);
    }

    public List<UserEntity> buscarPorNomeQueContem(String substring) {
        return userRepository.findByNomeContainingIgnoreCase(substring);
    }

    // Métodos adicionais para consultas com @Query

    public List<UserEntity> buscarPorNomeExato(String nome) {
        return userRepository.findByNomeExact(nome);
    }

    public List<UserEntity> buscarPorEmailContem(String email) {
        return userRepository.findByEmailContains(email);
    }

    public List<UserEntity> buscarPorNomeComecandoComQuery(String prefixo) {
        return userRepository.findByNomeStartingWithQuery(prefixo);
    }

    public List<UserEntity> buscarPorNomeContemQuery(String substring) {
        return userRepository.findByNomeContainingQuery(substring);
    }

    public List<UserEntity> buscarPorNomeEEmailExato(String nome, String email) {
        return userRepository.findByNomeAndEmailQuery(nome, email);
    }

    public List<UserEntity> buscarPorIdadeMaiorOuIgual(int idade) {
        return userRepository.findByIdadeGreaterThanEqual(idade);
    }

    public List<UserEntity> buscarPorDataDeCadastroDepois(Date data) {
        return userRepository.findByDataDeCadastroAfter(data);
    }

    public List<UserEntity> buscarPorIdadeEntre(int idadeInicio, int idadeFim) {
        return userRepository.findByIdadeBetween(idadeInicio, idadeFim);
    }

    public List<UserEntity> buscarPorNomeOuEmailContendo(String substring) {
        return userRepository.findByNomeOrEmailContaining(substring);
    }

    public List<UserEntity> buscarPorIdadeOuCadastroAntes(int idade, Date data) {
        return userRepository.findByIdadeExactOrDataDeCadastroBefore(idade, data);
    }

    public List<UserEntity> buscarPorNomeNaoContendo(String substring) {
        return userRepository.findByNomeNotContaining(substring);
    }
}
