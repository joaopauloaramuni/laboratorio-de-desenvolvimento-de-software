package com.example.BD_RestAPI_PostgreSQL.repository;

import com.example.BD_RestAPI_PostgreSQL.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByTitle(String title);
    List<BookEntity> findByAuthor(String author);
    List<BookEntity> findByTitleAndAuthor(String title, String author);
    List<BookEntity> findByTitleStartingWith(String prefix);
    List<BookEntity> findByTitleContaining(String contains);
}