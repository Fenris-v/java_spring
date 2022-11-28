package com.example.FenrisBookShopApp.controllers;

import com.example.FenrisBookShopApp.dto.SearchQueryDto;
import org.springframework.web.bind.annotation.ModelAttribute;

abstract public class Controller {
    @ModelAttribute("searchQueryDto")
    public SearchQueryDto searchQueryDto() {
        return null;
    }
}
