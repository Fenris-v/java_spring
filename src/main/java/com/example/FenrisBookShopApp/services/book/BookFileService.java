package com.example.FenrisBookShopApp.services.book;

import com.example.FenrisBookShopApp.entities.book.BookFileEntity;
import com.example.FenrisBookShopApp.repositories.book.BookFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookFileService {
    private final BookFileRepository bookFileRepository;

    @Autowired
    public BookFileService(BookFileRepository bookFileRepository) {
        this.bookFileRepository = bookFileRepository;
    }

    public BookFileEntity findBookFileEntitiesByHash(String hash) {
        return bookFileRepository.findBookFileEntitiesByHash(hash);
    }
}
