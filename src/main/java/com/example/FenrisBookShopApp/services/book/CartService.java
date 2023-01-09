package com.example.FenrisBookShopApp.services.book;

import com.example.FenrisBookShopApp.entities.book.BookEntity;
import com.example.FenrisBookShopApp.repositories.book.BookRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService extends AbstractUserCookieService {
    private static final String NAME = "cartContents";
    private final BookRepository bookRepository;

    @Autowired
    public CartService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookEntity> findBookFromCartCookie(String cookieCart) {
        return bookRepository.findBookEntitiesByIdIn(getIdsFromCookieString(cookieCart));
    }

    public void addBookToCart(String cartContents, String bookId, HttpServletResponse response) {
        if (cartContents == null || cartContents.isEmpty()) {
            setCookie(response, bookId, NAME);
            return;
        }

        cartContents = addToCookieString(cartContents, bookId);
        setCookie(response, cartContents, NAME);
    }

    public void removeFromCart(String cartContents, @NotNull String bookId, HttpServletResponse response) {
        cartContents = removeFromCookieString(cartContents, bookId);
        setCookie(response, cartContents, NAME);
    }

    public int getTotalPrice(@NotNull List<BookEntity> books) {
        int oldPrice = 0;
        for (BookEntity book : books) {
            oldPrice += book.getPriceWithDiscount() == null ? book.getPrice() : book.getPriceWithDiscount();
        }

        return oldPrice;
    }

    public int getTotalOldPrice(@NotNull List<BookEntity> books) {
        int price = 0;
        for (BookEntity book : books) {
            price += book.getPrice();
        }

        return price;
    }
}
