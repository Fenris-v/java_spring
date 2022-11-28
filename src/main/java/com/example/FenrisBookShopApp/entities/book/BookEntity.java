package com.example.FenrisBookShopApp.entities.book;

import com.example.FenrisBookShopApp.entities.genre.GenreEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "books")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<AuthorEntity> authors = new ArrayList<>();

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<GenreEntity> genres = new ArrayList<>();

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime pubDate;

    @Column(columnDefinition = "BOOLEAN DEFAULT false", nullable = false)
    private boolean isBestseller;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false)
    private String title;

    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int price;

    @Column(columnDefinition = "SMALLINT DEFAULT 0", nullable = false)
    private int discount;

    private double popularity;
}
