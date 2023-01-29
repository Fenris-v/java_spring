package com.example.FenrisBookShopApp.entities.book.links;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "book2user")
public class Book2UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime time;

    @Column(columnDefinition = "INT NOT NULL")
    private Long typeId;

    @Column(name = "book_id", columnDefinition = "INT NOT NULL")
    private Long bookId;

    @Column(name = "user_id", columnDefinition = "INT NOT NULL")
    private Long userId;
}
