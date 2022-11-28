package com.example.FenrisBookShopApp.services.genre;

import com.example.FenrisBookShopApp.dto.GenreDto;
import com.example.FenrisBookShopApp.dto.genre.GenresTreeDto;
import com.example.FenrisBookShopApp.entities.genre.GenreEntity;
import com.example.FenrisBookShopApp.repositories.genre.GenreRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeSet;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public GenreEntity getGenreBySlug(String slug) {
        return genreRepository.findBySlug(slug);
    }

    public TreeSet<GenreDto> getGenres() {
        GenresTreeDto genresTreeDto = new GenresTreeDto(genreRepository.findAllWithBookCount());
        setDepthMap(genresTreeDto);
        addChildrenToGenresDto(genresTreeDto.getDepthMap());
        markPreLastLevel(genresTreeDto.getDepthMap());

        return genresTreeDto.getDepthMap().get(0);
    }

    private void setDepthMap(GenresTreeDto genresTreeDto) {
        setTopLevel(genresTreeDto);
        setSubLevels(genresTreeDto);
    }

    private void setTopLevel(@NotNull GenresTreeDto genresTreeDto) {
        genresTreeDto.getDepthMap().put(0, new TreeSet<>());
        for (GenreDto genre : genresTreeDto.getGenres()) {
            if (genre.getParentId() == null) {
                genresTreeDto.getDepthMap().get(0).add(genre);
                genresTreeDto.getRemoveList().add(genre);
            }
        }

        removeAddedFromList(genresTreeDto);
    }

    private void removeAddedFromList(@NotNull GenresTreeDto genresTreeDto) {
        genresTreeDto.getGenres().removeAll(genresTreeDto.getRemoveList());
        genresTreeDto.getRemoveList().clear();
    }

    private void setSubLevels(@NotNull GenresTreeDto genresTreeDto) {
        int depth = 1;
        while (!genresTreeDto.getGenres().isEmpty() && depth < 10) {
            genresTreeDto.getDepthMap().put(depth, new TreeSet<>());
            for (GenreDto genre : genresTreeDto.getGenres()) {
                addGenresToSubLevels(genresTreeDto, genre, depth);
            }

            removeAddedFromList(genresTreeDto);
            depth++;
        }
    }

    private void addGenresToSubLevels(@NotNull GenresTreeDto genresTreeDto, GenreDto genre, int depth) {
        for (GenreDto parent : genresTreeDto.getDepthMap().get(depth - 1)) {
            if (!parent.getId().equals(genre.getParentId())) {
                continue;
            }

            genre.setParent(parent);
            parent.setBooksCount(genre.getBooksCount());
            genresTreeDto.getDepthMap().get(depth).add(genre);
            genresTreeDto.getRemoveList().add(genre);
            break;
        }
    }

    private void addChildrenToGenresDto(@NotNull Map<Integer, TreeSet<GenreDto>> depthMap) {
        int depth = 0;
        while (depthMap.containsKey(depth + 1)) {
            addChildrenToLevel(depthMap, depth);
            depth++;
        }
    }

    private void addChildrenToLevel(@NotNull Map<Integer, TreeSet<GenreDto>> depthMap, int depth) {
        depthMap.get(depth).forEach(genre -> depthMap.get(depth + 1).forEach(child -> {
            if (genre.getId().equals(child.getParentId())) {
                genre.getChildren().add(child);
            }
        }));
    }

    private void markPreLastLevel(@NotNull Map<Integer, TreeSet<GenreDto>> depthMap) {
        depthMap.get(0).forEach(this::checkDtoAboutChildren);
    }

    private void checkDtoAboutChildren(@NotNull GenreDto genre) {
        if (!genre.getChildren().isEmpty()) {
            genre.setHasMoreThatOneChildLevel(true);
            genre.getChildren().forEach(this::checkDtoAboutChildren);
        }
    }
}
