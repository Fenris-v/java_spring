package com.example.FenrisBookShopApp.controllers.categories;

import com.example.FenrisBookShopApp.data.ResourceStorage;
import com.example.FenrisBookShopApp.entities.book.BookEntity;
import com.example.FenrisBookShopApp.entities.book.review.BookRateEntity;
import com.example.FenrisBookShopApp.services.book.BookService;
import com.example.FenrisBookShopApp.services.book.RateService;
import com.example.FenrisBookShopApp.services.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("books")
public class BooksController {
    private final BookService bookService;
    private final RateService rateService;
    private final ResourceStorage resourceStorage;
    private final AuthenticationService authenticationService;

    @GetMapping(value = "recent", name = "app.new_book.list", produces = MediaType.TEXT_HTML_VALUE)
    public String recent(@NotNull Model model) {
        model.addAttribute("recentBooks", bookService.getPageOfRecentBooks(0, 20));
        model.addAttribute("isNewsPage", true);
        return "books/recent";
    }

    @GetMapping(value = "popular", name = "app.popular.list", produces = MediaType.TEXT_HTML_VALUE)
    public String popular(@NotNull Model model) {
        model.addAttribute("popularBooks", bookService.getPageOfPopularBooks(0, 20));
        model.addAttribute("isPopularPage", true);
        return "books/popular";
    }

    @GetMapping(value = "{slug}", name = "app.books.detail")
    public String book(@PathVariable String slug, @NotNull Model model) {
        BookEntity book = bookService.findBookBySlug(slug);
        BookRateEntity rateByUser = rateService.findBookRateEntityByBookIdAndUser(book.getId(),
                authenticationService.getCurrentUser());
        Map<Byte, Long> ratesMap = rateService.getRatesCountMap(book.getId());
        model.addAttribute("book", book);
        model.addAttribute("rateByUser", rateByUser == null ? 0 : rateByUser.getValue());
        model.addAttribute("rateMap", ratesMap);
        model.addAttribute("avgRate", rateService.getAvgRatesByMap(ratesMap));
        model.addAttribute("countOfRates", rateService.countBookRatesByMap(ratesMap));
        model.addAttribute("reviews", book.getReviews());

        return "books/slug";
    }

    @PostMapping(value = "{slug}/img/save", name = "app.book.upload_image")
    public String uploadBookImage(@PathVariable String slug, @RequestParam("file") MultipartFile file)
            throws IOException {
        String filePath = resourceStorage.uploadBookImage(file, slug);
        BookEntity book = bookService.findBookBySlug(slug);
        book.setImage(filePath);
        bookService.save(book);

        String redirectUrl = MvcUriComponentsBuilder.fromMappingName("app.books.detail").arg(0, slug).build();
        return ("redirect:" + redirectUrl);
    }
}
