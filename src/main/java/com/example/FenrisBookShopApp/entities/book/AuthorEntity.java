package com.example.FenrisBookShopApp.entities.book;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "authors")
@ApiModel("Author entity")
public class AuthorEntity implements Comparable<AuthorEntity> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("Auto generating")
    private Long id;

    private String photo;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "book2author",
            joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id")
    )
    @JsonBackReference
    private List<BookEntity> books = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(@NotNull AuthorEntity author) {
        return CharSequence.compare(name, author.getName());
    }
}
