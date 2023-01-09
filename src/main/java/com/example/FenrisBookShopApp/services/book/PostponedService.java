package com.example.FenrisBookShopApp.services.book;

import com.example.FenrisBookShopApp.entities.book.BookEntity;
import com.example.FenrisBookShopApp.repositories.book.BookRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostponedService extends AbstractUserCookieService {
    private static final String NAME = "postponed";

    private final BookRepository bookRepository;

    @Autowired
    public PostponedService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookEntity> findBookFromPostponedCookie(String postponed) {
        return bookRepository.findBookEntitiesByIdIn(getIdsFromCookieString(postponed));
    }

    public void addToPostponed(String postponed, String bookId, HttpServletResponse response) {
        if (postponed == null || postponed.isEmpty()) {
            setCookie(response, bookId, NAME);
            return;
        }

        postponed = addToCookieString(postponed, bookId);
        setCookie(response, postponed, NAME);
    }

    public void removeFromPostponed(String postponed, String bookId, HttpServletResponse response) {
        postponed = removeFromCookieString(postponed, bookId);
        setCookie(response, postponed, NAME);
    }
}
