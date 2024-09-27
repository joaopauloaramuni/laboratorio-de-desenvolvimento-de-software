package com.example.BD_RestAPI_PostgreSQL.controller;

import com.example.BD_RestAPI_PostgreSQL.model.BookEntity;
import com.example.BD_RestAPI_PostgreSQL.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books") // Define a rota base para todos os métodos na classe
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BookEntity> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookEntity obterPorId(@PathVariable Long id) {
        return bookService.obterPorId(id);
    }

    @PostMapping
    public BookEntity inserir(@RequestBody BookEntity book) {
        return bookService.inserir(book);
    }

    @PutMapping("/{id}")
    public BookEntity atualizar(@PathVariable Long id, @RequestBody BookEntity book) {
        return bookService.atualizar(id, book);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        bookService.excluir(id);
    }

    // Consultas personalizadas

    // http://localhost:8080/books/buscarPorTitulo/MeuLivro
    @GetMapping("/buscarPorTitulo/{titulo}")
    public List<BookEntity> buscarLivrosPorTitulo(@PathVariable String titulo) {
        return bookService.buscarPorTitulo(titulo);
    }

    // http://localhost:8080/books/buscarPorAutor/J.K.Rowling
    @GetMapping("/buscarPorAutor/{autor}")
    public List<BookEntity> buscarLivrosPorAutor(@PathVariable String autor) {
        return bookService.buscarPorAutor(autor);
    }

    // http://localhost:8080/books/buscarPorTituloEAutor?titulo=MeuLivro&autor=J.K.Rowling
    @GetMapping("/buscarPorTituloEAutor")
    public List<BookEntity> buscarLivrosPorTituloEAutor(@RequestParam("titulo") String titulo, @RequestParam("autor") String autor) {
        return bookService.buscarPorTituloEAutor(titulo, autor);
    }

    // http://localhost:8080/books/buscarPorTituloQueComecaCom/m
    @GetMapping("/buscarPorTituloQueComecaCom/{prefixo}")
    public List<BookEntity> buscarLivrosPorTituloQueComecaCom(@PathVariable String prefixo) {
        return bookService.buscarPorTituloQueComecaCom(prefixo);
    }

    // http://localhost:8080/books/buscarPorTituloQueContem/livro
    @GetMapping("/buscarPorTituloQueContem/{contem}")
    public List<BookEntity> buscarLivrosPorTituloQueContem(@PathVariable String contem) {
        return bookService.buscarPorTituloQueContem(contem);
    }
}
