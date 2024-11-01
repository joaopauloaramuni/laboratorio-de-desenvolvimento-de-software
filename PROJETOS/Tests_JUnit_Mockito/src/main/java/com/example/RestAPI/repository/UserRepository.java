package com.example.RestAPI.repository;

import com.example.RestAPI.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    // Métodos de CRUD já estão disponíveis

    // Busca usuários pelo nome, ignorando maiúsculas e minúsculas
    List<UserEntity> findByNomeIgnoreCase(String nome);

    // Busca usuários pelo email, ignorando maiúsculas e minúsculas
    List<UserEntity> findByEmailIgnoreCase(String email);

    // Busca usuários pelo nome e email, ignorando maiúsculas e minúsculas
    List<UserEntity> findByNomeAndEmailAllIgnoreCase(String nome, String email);

    // Busca usuários cujo nome começa com um determinado prefixo, ignorando maiúsculas e minúsculas
    List<UserEntity> findByNomeStartingWithIgnoreCase(String prefix);

    // Busca usuários cujo nome contém uma determinada substring, ignorando maiúsculas e minúsculas
    List<UserEntity> findByNomeContainingIgnoreCase(String substring);

    // Consultas personalizadas utilizando a anotação @Query

    // 1. Busca usuários pelo nome exato
    @Query("{ 'nome': ?0 }")
    List<UserEntity> findByNomeExact(String nome);

    // 2. Busca usuários cujo email contém uma substring (case insensitive)
    @Query("{ 'email': { $regex: ?0, $options: 'i' } }")
    List<UserEntity> findByEmailContains(String email);

    // 3. Busca usuários cujo nome começa com um prefixo específico (case insensitive)
    @Query("{ 'nome': { $regex: '^?0', $options: 'i' } }")
    List<UserEntity> findByNomeStartingWithQuery(String prefix);

    // 4. Busca usuários cujo nome contém uma substring (case insensitive)
    @Query("{ 'nome': { $regex: ?0, $options: 'i' } }")
    List<UserEntity> findByNomeContainingQuery(String substring);

    // 5. Busca usuários que possuem um nome e um email específicos
    @Query("{ 'nome': ?0, 'email': ?1 }")
    List<UserEntity> findByNomeAndEmailQuery(String nome, String email);

    // 6. Busca por usuários cuja idade é maior ou igual a um valor específico
    @Query("{ 'idade': { $gte: ?0 } }")
    List<UserEntity> findByIdadeGreaterThanEqual(int idade);

    // 7. Busca por usuários cadastrados após uma data específica
    @Query("{ 'dataDeCadastro': { $gte: ?0 } }")
    List<UserEntity> findByDataDeCadastroAfter(Date data);

    // 8. Busca usuários cuja idade está entre um intervalo específico
    @Query("{ 'idade': { $gte: ?0, $lte: ?1 } }")
    List<UserEntity> findByIdadeBetween(int idadeInicio, int idadeFim);

    // 9. Busca por usuários cujo nome ou email contêm uma substring específica
    @Query("{ '$or': [ { 'nome': { $regex: ?0, $options: 'i' } }, { 'email': { $regex: ?0, $options: 'i' } } ] }")
    List<UserEntity> findByNomeOrEmailContaining(String substring);

    // 10. Busca por usuários que têm uma idade exata ou foram cadastrados antes de uma data específica
    @Query("{ '$or': [ { 'idade': ?0 }, { 'dataDeCadastro': { $lt: ?1 } } ] }")
    List<UserEntity> findByIdadeExactOrDataDeCadastroBefore(int idade, Date data);

    // 11. Busca por usuários cujo nome não contém uma substring específica
    @Query("{ 'nome': { $not: { $regex: ?0, $options: 'i' } } }")
    List<UserEntity> findByNomeNotContaining(String substring);
}
