package com.example.FenrisBookShopApp.repositories;

import com.example.FenrisBookShopApp.entities.book.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    AuthorEntity findAuthorBySlugIgnoreCase(String slug);
}
