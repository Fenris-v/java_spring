package com.example.FenrisBookShopApp.repositories.book;

import com.example.FenrisBookShopApp.entities.book.BookFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookFileRepository extends JpaRepository<BookFileEntity, Long> {
     BookFileEntity findBookFileEntitiesByHash(String hash);
}
