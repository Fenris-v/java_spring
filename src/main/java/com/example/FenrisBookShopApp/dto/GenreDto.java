package com.example.FenrisBookShopApp.dto;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
public class GenreDto implements Comparable<GenreDto> {
    private Long id;
    private Long parentId;
    private String slug;
    private String name;
    private Long booksCount;

    private Set<GenreDto> children = new TreeSet<>();
    private int depth = 0;
    private GenreDto parent = null;
    private boolean hasMoreThatOneChildLevel = false;

    public GenreDto(Long id, Long parentId, String slug, String name, Long booksCount) {
        this.id = id;
        this.parentId = parentId;
        this.slug = slug;
        this.name = name;
        this.booksCount = booksCount;
    }

    public void setBooksCount(Long booksCount) {
        this.booksCount += booksCount;

        if (parent != null) {
            parent.setBooksCount(booksCount);
        }
    }

    @Override
    public int compareTo(@NotNull GenreDto genreDto) {
        return genreDto.getName().compareTo(name);
    }
}
