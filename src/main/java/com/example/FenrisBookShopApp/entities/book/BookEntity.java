package com.example.FenrisBookShopApp.entities.book;

import com.example.FenrisBookShopApp.entities.book.review.BookRateEntity;
import com.example.FenrisBookShopApp.entities.book.review.BookReviewEntity;
import com.example.FenrisBookShopApp.entities.genre.GenreEntity;
import com.example.FenrisBookShopApp.entities.other.TagEntity;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @JsonIgnore
    @JsonManagedReference
    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private List<TagEntity> tags = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<BookFileEntity> files = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private List<BookRateEntity> rates = new ArrayList<>();

    @OneToMany
    @OrderBy("time desc")
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private List<BookReviewEntity> reviews = new ArrayList<>();

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

    @JsonGetter("authors")
    public String authorsFullName() {
        StringBuilder names = new StringBuilder();
        authors.forEach(author -> {
            if (!names.isEmpty()) {
                names.append(", ");
            }

            names.append(author.toString());
        });

        return names.toString();
    }

    @JsonProperty
    public Integer getPriceWithDiscount() {
        if (discount == 0) {
            return null;
        }

        return price - price * discount / 100;
    }
}
