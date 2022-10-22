package com.example.FenrisBookShopApp.data;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "authors")
public class Author implements Comparable<Author> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;

    @OneToMany
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private List<Book> bookList = new ArrayList<>();

    @Override
    public String toString() {
        return firstName + ' ' + lastName;
    }

    @Override
    public int compareTo(@NotNull Author author) {
        return CharSequence.compare(firstName + ' ' + lastName, author.getFirstName() + ' ' + author.getLastName());
    }
}
