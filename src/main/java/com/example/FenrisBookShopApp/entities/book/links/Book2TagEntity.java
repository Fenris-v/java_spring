package com.example.FenrisBookShopApp.entities.book.links;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "book2tag")
public class Book2TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "INT NOT NULL", name = "book_id")
    private Long bookId;

    @Column(columnDefinition = "INT NOT NULL", name = "tag_id")
    private Long tagId;
}
