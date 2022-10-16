package com.example.FenrisBookShopApp.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Setter
@Getter
@ToString
public class Author implements Comparable<Author> {
    private Integer id;
    private String firstName;

    private String lastName;

    @Override
    public int compareTo(@NotNull Author author) {
        return CharSequence.compare(firstName + ' ' + lastName, author.getFirstName() + ' ' + author.getLastName());
    }
}
