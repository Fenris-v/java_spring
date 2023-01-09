package com.example.FenrisBookShopApp.controllers;

import com.example.FenrisBookShopApp.dto.book.RateDto;
import com.example.FenrisBookShopApp.services.book.RateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class RateController {
    private final RateService rateService;

    @Autowired
    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @ResponseBody
    @PostMapping(value = "rateBook")
    public HashMap<String, Boolean> rateBook(@Valid RateDto rateDto) {
        boolean rateUpdated = rateService.rate(rateDto);
        HashMap<String, Boolean> response = new HashMap<>();
        response.put("result", rateUpdated);

        return response;
    }
}
