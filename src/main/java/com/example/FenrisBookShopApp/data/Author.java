package com.example.FenrisBookShopApp.data;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "authors")
public class Author implements Comparable<Author> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photo;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

//    @OneToMany(mappedBy = "author")
//    private List<Book> bookList = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(@NotNull Author author) {
        return CharSequence.compare(name, author.getName());
    }
}
