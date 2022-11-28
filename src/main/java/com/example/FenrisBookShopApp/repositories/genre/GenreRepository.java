package com.example.FenrisBookShopApp.repositories.genre;

import com.example.FenrisBookShopApp.dto.GenreDto;
import com.example.FenrisBookShopApp.entities.genre.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    @Query("select new com.example.FenrisBookShopApp.dto.GenreDto(g.id, g.parentId, g.slug, g.name, count(b2g.id)) from GenreEntity g left join Book2GenreEntity b2g on g.id = b2g.genreId group by g.id")
    List<GenreDto> findAllWithBookCount();

    GenreEntity findBySlug(String slug);
}
