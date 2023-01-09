package com.example.FenrisBookShopApp.repositories.book;

import com.example.FenrisBookShopApp.dto.book.BookAvgRateDto;
import com.example.FenrisBookShopApp.dto.book.RateGroupedCountDto;
import com.example.FenrisBookShopApp.entities.book.review.BookRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRateRepository extends JpaRepository<BookRateEntity, Long> {
    BookRateEntity findBookRateEntityByBookIdAndUserId(Long bookId, Long userId);

    @Query("select new com.example.FenrisBookShopApp.dto.book.RateGroupedCountDto(count(br.id), br.value)" +
            " from BookRateEntity br where br.bookId = ?1 group by br.value")
    List<RateGroupedCountDto> getCountOfGroupedRate(Long bookId);

    @Query("select new com.example.FenrisBookShopApp.dto.book.BookAvgRateDto(br.bookId, avg(br.value))" +
            " from BookRateEntity br where br.bookId in ?1 group by br.bookId")
    List<BookAvgRateDto> getAvgCountsByBookIdIn(List<Long> bookIds);
}
