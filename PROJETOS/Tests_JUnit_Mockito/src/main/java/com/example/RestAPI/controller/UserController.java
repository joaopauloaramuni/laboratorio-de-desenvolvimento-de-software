package com.example.RestAPI.controller;

import com.example.RestAPI.model.UserEntity;
import com.example.RestAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserEntity> obterTodos() {
        return userService.obterTodos();
    }

    @GetMapping("/{id}")
    public UserEntity obterPorId(@PathVariable String id) {
        return userService.obterPorId(id);
    }

    @PostMapping
    public UserEntity inserir(@RequestBody UserEntity user) {
        return userService.inserir(user);
    }

    @PutMapping("/{id}")
    public UserEntity atualizar(@PathVariable String id, @RequestBody UserEntity user) {
        return userService.atualizar(id, user);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable String id) {
        userService.excluir(id);
    }

    // Utilizando consultas personalizadas
    @GetMapping("/buscarPorNome/{nome}")
    public List<UserEntity> buscarUsuariosPorNome(@PathVariable String nome) {
        return userService.buscarPorNome(nome);
    }

    @GetMapping("/buscarPorEmail/{email}")
    public List<UserEntity> buscarUsuariosPorEmail(@PathVariable String email) {
        return userService.buscarPorEmail(email);
    }

    @GetMapping("/buscarPorNomeEEmail")
    public List<UserEntity> buscarUsuariosPorNomeEEmail(@RequestParam("nome") String nome, @RequestParam("email") String email) {
        return userService.buscarPorNomeEEmail(nome, email);
    }

    @GetMapping("/buscarPorNomeQueComecaCom/{prefixo}")
    public List<UserEntity> buscarUsuariosPorNomeQueComecaCom(@PathVariable String prefixo) {
        return userService.buscarPorNomeQueComecaCom(prefixo);
    }

    @GetMapping("/buscarPorNomeQueContem/{contem}")
    public List<UserEntity> buscarUsuariosPorNomeQueContem(@PathVariable String contem) {
        return userService.buscarPorNomeQueContem(contem);
    }

    // Endpoints para métodos usando @Query
    @GetMapping("/buscar/exato/{nome}")
    public List<UserEntity> buscarPorNomeExato(@PathVariable String nome) {
        return userService.buscarPorNomeExato(nome);
    }

    @GetMapping("/buscar/email-contem/{email}")
    public List<UserEntity> buscarPorEmailContem(@PathVariable String email) {
        return userService.buscarPorEmailContem(email);
    }

    @GetMapping("/buscar/prefixo-query/{prefixo}")
    public List<UserEntity> buscarPorNomeComecandoComQuery(@PathVariable String prefixo) {
        return userService.buscarPorNomeComecandoComQuery(prefixo);
    }

    @GetMapping("/buscar/contem-query/{substring}")
    public List<UserEntity> buscarPorNomeContemQuery(@PathVariable String substring) {
        return userService.buscarPorNomeContemQuery(substring);
    }

    @GetMapping("/buscar/exato-nome-email")
    public List<UserEntity> buscarPorNomeEEmailExato(@RequestParam String nome, @RequestParam String email) {
        return userService.buscarPorNomeEEmailExato(nome, email);
    }

    // Novos endpoints adicionados
    @GetMapping("/buscar/idade-maior-ou-igual/{idade}")
    public List<UserEntity> buscarPorIdadeMaiorOuIgual(@PathVariable int idade) {
        return userService.buscarPorIdadeMaiorOuIgual(idade);
    }

    @GetMapping("/buscar/data-de-cadastro-depois")
    public List<UserEntity> buscarPorDataDeCadastroDepois(@RequestParam Date data) {
        return userService.buscarPorDataDeCadastroDepois(data);
    }

    @GetMapping("/buscar/idade-entre")
    public List<UserEntity> buscarPorIdadeEntre(@RequestParam int idadeInicio, @RequestParam int idadeFim) {
        return userService.buscarPorIdadeEntre(idadeInicio, idadeFim);
    }

    @GetMapping("/buscar/nome-ou-email-contem/{substring}")
    public List<UserEntity> buscarPorNomeOuEmailContendo(@PathVariable String substring) {
        return userService.buscarPorNomeOuEmailContendo(substring);
    }

    @GetMapping("/buscar/idade-ou-cadastro-antes")
    public List<UserEntity> buscarPorIdadeOuCadastroAntes(@RequestParam int idade, @RequestParam Date data) {
        return userService.buscarPorIdadeOuCadastroAntes(idade, data);
    }

    @GetMapping("/buscar/nome-nao-contem/{substring}")
    public List<UserEntity> buscarPorNomeNaoContendo(@PathVariable String substring) {
        return userService.buscarPorNomeNaoContendo(substring);
    }
}
