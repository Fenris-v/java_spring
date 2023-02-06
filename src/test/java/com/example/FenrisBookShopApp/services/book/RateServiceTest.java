package com.example.FenrisBookShopApp.services.book;

import com.example.FenrisBookShopApp.dto.book.RateDto;
import com.example.FenrisBookShopApp.entities.book.review.BookRateEntity;
import com.example.FenrisBookShopApp.entities.user.UserEntity;
import com.example.FenrisBookShopApp.repositories.book.BookRateRepository;
import com.example.FenrisBookShopApp.services.security.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RateServiceTest {
    private final RateService rateService;

    @MockBean
    private BookRateRepository bookRateRepositoryMock;

    @MockBean
    private AuthenticationService authenticationServiceMock;

    @Autowired
    public RateServiceTest(RateService rateService) {
        this.rateService = rateService;
    }

    @Test
    void rate() {
        UserEntity user = new UserEntity();
        user.setId(1000L);
        Mockito.doReturn(user)
                .when(authenticationServiceMock)
                .getCurrentUser();

        RateDto rateDto = new RateDto(1L, (byte) 3);
        BookRateEntity bookRate = rateService.rate(rateDto);
        Mockito.verify(bookRateRepositoryMock, Mockito.times(1))
                .save(Mockito.any(BookRateEntity.class));

        assertNotNull(bookRate);
        assertEquals(bookRate.getValue(), (byte) 3);
    }

    @Test
    void getAvgRatesByMap() {
        Map<Byte, Long> rateMap = new HashMap<>();
        rateMap.put((byte) 1, 5L);
        rateMap.put((byte) 2, 0L);
        rateMap.put((byte) 3, 10L);
        rateMap.put((byte) 4, 5L);
        rateMap.put((byte) 5, 2L);

        int excepted = 3;
        int result = rateService.getAvgRatesByMap(rateMap);

        assertEquals(excepted, result);
    }
}
