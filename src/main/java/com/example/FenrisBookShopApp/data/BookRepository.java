package com.example.FenrisBookShopApp.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findBookByAuthor_FirstName(String name);

    @Query("from Book ")
    List<Book> customFindAllBooks();
}
