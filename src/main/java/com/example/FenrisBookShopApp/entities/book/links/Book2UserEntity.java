package com.example.FenrisBookShopApp.entities.book.links;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "book2user")
public class Book2UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime time;

    @Column(columnDefinition = "INT NOT NULL")
    private Long typeId;

    @Column(columnDefinition = "INT NOT NULL")
    private Long bookId;

    @Column(columnDefinition = "INT NOT NULL")
    private Long userId;
}
