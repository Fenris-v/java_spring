package com.example.FenrisBookShopApp.shedules;

import com.example.FenrisBookShopApp.dto.book.BookPopularityDto;
import com.example.FenrisBookShopApp.entities.book.BookEntity;
import com.example.FenrisBookShopApp.repositories.book.BookRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BooksPopularityUpdater {
    private final BookRepository bookRepository;
    private final List<BookEntity> updatedBooks = new ArrayList<>();

    @Autowired
    public BooksPopularityUpdater(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Scheduled(cron = "0 0 3 * * *", zone = "Europe/Moscow")
    private void update() {
        List<BookPopularityDto> books = bookRepository.findAllWithBuyerAndDeferredAndCartCount();
        books.forEach(this::calculatePopularity);

        bookRepository.saveAll(updatedBooks);
        updatedBooks.clear();
    }

    private void calculatePopularity(@NotNull BookPopularityDto bookPopularityDto) {
        double popularity = bookPopularityDto.getBuyerCount() +
                (0.7 * bookPopularityDto.getInCartsCount()) +
                (0.4 * bookPopularityDto.getDeferredCount());

        BookEntity book = bookPopularityDto.getBook();
        book.setPopularity(popularity);
        updatedBooks.add(book);

        if (updatedBooks.size() >= 100) {
            bookRepository.saveAll(updatedBooks);
            updatedBooks.clear();
        }
    }
}
