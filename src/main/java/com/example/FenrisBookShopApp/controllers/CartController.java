package com.example.FenrisBookShopApp.controllers;

import com.example.FenrisBookShopApp.entities.book.BookEntity;
import com.example.FenrisBookShopApp.services.book.CartService;
import com.example.FenrisBookShopApp.services.book.RateService;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {
    private final CartService cartService;
    private final RateService rateService;

    @Autowired
    public CartController(CartService cartService, RateService rateService) {
        this.cartService = cartService;
        this.rateService = rateService;
    }

    @GetMapping(value = "cart", name = "app.cart")
    public String cartPage(@CookieValue(name = "cartContents", required = false) String cartContents,
                           @NotNull Model model) {
        boolean isEmpty = cartContents == null || cartContents.isEmpty();
        model.addAttribute("isCartEmpty", isEmpty);
        if (!isEmpty) {
            List<BookEntity> books = cartService.findBookFromCartCookie(cartContents);
            List<Long> bookIds = books.stream().map(BookEntity::getId).toList();
            model.addAttribute("books", books);
            model.addAttribute("cartTotalPrice", cartService.getTotalPrice(books));
            model.addAttribute("cartTotalOldPrice", cartService.getTotalOldPrice(books));
            model.addAttribute("rateMap", rateService.getAvgCountsByBookIdIn(bookIds));
        }

        return "cart";
    }

    @ResponseBody
    @PostMapping(value = "books/changeBookStatus/{bookId}", name = "app.cart.add")
    public boolean addBookToCart(@PathVariable String bookId,
                                 @CookieValue(name = "cartContents", required = false) String cartContents,
                                 HttpServletResponse response,
                                 @NotNull Model model) {
        cartService.addBookToCart(cartContents, bookId, response);
        model.addAttribute("isCartEmpty", false);

        return true;
    }

    @ResponseBody
    @DeleteMapping(value = "books/changeBookStatus/{bookId}", name = "app.cart.remove")
    public Map<String, Boolean> removeBookFromCart(@PathVariable String bookId,
                                                   @CookieValue(name = "cartContents", required = false)
                                                   String cartContents,
                                                   HttpServletResponse response) {
        Map<String, Boolean> responseData = new HashMap<>();
        if (cartContents == null || cartContents.isEmpty()) {
            responseData.put("success", false);
            return responseData;
        }

        cartService.removeFromCart(cartContents, bookId, response);
        responseData.put("success", true);
        return responseData;
    }
}
