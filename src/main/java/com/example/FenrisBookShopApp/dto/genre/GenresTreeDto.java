package com.example.FenrisBookShopApp.dto.genre;

import com.example.FenrisBookShopApp.dto.GenreDto;
import lombok.Getter;

import java.util.*;

@Getter
public class GenresTreeDto {
    private final List<GenreDto> genres;
    private final Map<Integer, TreeSet<GenreDto>> depthMap = new HashMap<>();
    private final List<GenreDto> removeList = new ArrayList<>();

    public GenresTreeDto(List<GenreDto> genres) {
        this.genres = genres;
    }
}
