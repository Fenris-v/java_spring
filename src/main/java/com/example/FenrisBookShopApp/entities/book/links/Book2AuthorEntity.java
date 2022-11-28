package com.example.FenrisBookShopApp.entities.book.links;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "book2author")
public class Book2AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "INT NOT NULL", name = "book_id")
    private Long bookId;

    @Column(columnDefinition = "INT NOT NULL", name = "author_id")
    private Long authorId;

    @Column(columnDefinition = "INT NOT NULL  DEFAULT 0")
    private int sortIndex;
}
