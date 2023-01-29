package com.example.FenrisBookShopApp.entities.user;

import com.example.FenrisBookShopApp.entities.book.review.BookRateEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class BookStoreUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String password;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime regTime;

    @Column(columnDefinition = "INT NOT NULL")
    private int balance;

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<BookRateEntity> rates = new ArrayList<>();
}
