package com.example.FenrisBookShopApp.services.book;

import com.example.FenrisBookShopApp.dto.book.BookAvgRateDto;
import com.example.FenrisBookShopApp.dto.book.RateDto;
import com.example.FenrisBookShopApp.entities.book.review.BookRateEntity;
import com.example.FenrisBookShopApp.entities.user.UserEntity;
import com.example.FenrisBookShopApp.repositories.book.BookRateRepository;
import com.example.FenrisBookShopApp.services.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RateService {
    private final BookRateRepository bookRateRepository;
    private final AuthenticationService authenticationService;

    public boolean rate(@NotNull RateDto rateDto) {
        Long userId = authenticationService.getCurrentUser().getId();
        BookRateEntity bookRate = bookRateRepository.findBookRateEntityByBookIdAndUserId(rateDto.getBookId(), userId);
        if (bookRate == null) {
            bookRate = new BookRateEntity();
            bookRate.setBookId(rateDto.getBookId());
            bookRate.setUserId(userId);
        }

        bookRate.setValue(rateDto.getValue());
        bookRateRepository.save(bookRate);
        return true;
    }

    public BookRateEntity findBookRateEntityByBookIdAndUser(Long bookId, UserEntity user) {
        return user == null ? null : bookRateRepository.findBookRateEntityByBookIdAndUserId(bookId, user.getId());
    }

    public Map<Byte, Long> getRatesCountMap(Long bookId) {
        Map<Byte, Long> groupedRatesCount = new HashMap<>();
        for (byte i = 1; i <= 5; i++) {
            groupedRatesCount.put(i, 0L);
        }

        bookRateRepository.getCountOfGroupedRate(bookId)
                .forEach(rateDto -> groupedRatesCount.put(rateDto.getValue(), rateDto.getCount()));
        return groupedRatesCount;
    }

    public byte getAvgRatesByMap(@NotNull Map<Byte, Long> ratesMap) {
        double totalCount = 0;
        double totalRate = 0;
        for (byte key : ratesMap.keySet()) {
            if (ratesMap.get(key) > 0) {
                totalCount += ratesMap.get(key);
                totalRate += (ratesMap.get(key) * key);
            }
        }

        return totalCount == 0 ? 0 : (byte) Math.round(totalRate / totalCount);
    }

    public int countBookRatesByMap(@NotNull Map<Byte, Long> ratesMap) {
        int totalCount = 0;
        for (byte key : ratesMap.keySet()) {
            totalCount += ratesMap.get(key);
        }

        return totalCount;
    }

    public Map<Long, Byte> getAvgCountsByBookIdIn(@NotNull List<Long> bookIds) {
        Map<Long, Byte> rateMap = new HashMap<>();
        bookIds.forEach(bookId -> rateMap.put(bookId, (byte) 0));
        List<BookAvgRateDto> rates = bookRateRepository.getAvgCountsByBookIdIn(bookIds);
        for (BookAvgRateDto bookAvgRateDto : rates) {
            rateMap.put(bookAvgRateDto.getBookId(), bookAvgRateDto.getRate());
        }

        return rateMap;
    }
}
