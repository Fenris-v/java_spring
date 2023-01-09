package com.example.FenrisBookShopApp.controllers.categories;

import com.example.FenrisBookShopApp.entities.book.BookEntity;
import com.example.FenrisBookShopApp.services.book.PostponedService;
import com.example.FenrisBookShopApp.services.book.RateService;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PostponedController {
    private final PostponedService postponedService;
    private final RateService rateService;

    public PostponedController(PostponedService postponedService, RateService rateService) {
        this.postponedService = postponedService;
        this.rateService = rateService;
    }

    @GetMapping(value = "postponed", name = "app.favorite.list")
    public String postponed(@CookieValue(name = "postponed", required = false) String postponed,
                            @NotNull Model model) {
        List<BookEntity> books = postponed == null || postponed.isEmpty()
                ? new ArrayList<>()
                : postponedService.findBookFromPostponedCookie(postponed);

        List<Long> bookIds = books.stream().map(BookEntity::getId).toList();
        model.addAttribute("books", books);
        model.addAttribute("rateMap", rateService.getAvgCountsByBookIdIn(bookIds));
        return "postponed";
    }

    @ResponseBody
    @PostMapping(value = "books/postponed/{bookId}", name = "app.postponed.add")
    public boolean addToPostponed(@PathVariable String bookId,
                                  @CookieValue(name = "postponed", required = false) String postponed,
                                  HttpServletResponse response) {
        postponedService.addToPostponed(postponed, bookId, response);
        return true;
    }

    @ResponseBody
    @DeleteMapping(value = "books/postponed/{bookId}", name = "app.postponed.remove")
    public Map<String, Boolean> removeFromPostponed(@PathVariable String bookId,
                                                    @CookieValue(name = "postponed", required = false) String postponed,
                                                    HttpServletResponse response) {
        Map<String, Boolean> responseData = new HashMap<>();
        if (postponed == null || postponed.isEmpty()) {
            responseData.put("success", false);
            return responseData;
        }

        postponedService.removeFromPostponed(postponed, bookId, response);
        responseData.put("success", true);
        return responseData;
    }
}
