package com.example.BD_RestAPI_PostgreSQL.service;

import com.example.BD_RestAPI_PostgreSQL.model.BookEntity;
import com.example.BD_RestAPI_PostgreSQL.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    public BookEntity obterPorId(Long id) {
        Optional<BookEntity> optionalBook = bookRepository.findById(id);
        return optionalBook.orElse(null); // Retorna null se não encontrar
    }

    public BookEntity inserir(BookEntity book) {
        return bookRepository.save(book);
    }

    public BookEntity atualizar(Long id, BookEntity book) {
        if (bookRepository.existsById(id)) {
            book.setId(id); // Atualiza o ID do livro
            return bookRepository.save(book);
        }
        return null; // Retorna null se o livro não existir
    }

    public void excluir(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookEntity> buscarPorTitulo(String titulo) {
        return bookRepository.findByTitle(titulo);
    }

    public List<BookEntity> buscarPorAutor(String autor) {
        return bookRepository.findByAuthor(autor);
    }

    public List<BookEntity> buscarPorTituloEAutor(String titulo, String autor) {
        return bookRepository.findByTitleAndAuthor(titulo, autor);
    }

    public List<BookEntity> buscarPorTituloQueComecaCom(String prefixo) {
        return bookRepository.findByTitleStartingWith(prefixo);
    }

    public List<BookEntity> buscarPorTituloQueContem(String contem) {
        return bookRepository.findByTitleContaining(contem);
    }
}
