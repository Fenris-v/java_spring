package com.example.FenrisBookShopApp.data;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final TreeMap<Character, TreeSet<Author>> authorsMap = new TreeMap<>();

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public TreeMap<Character, TreeSet<Author>> getAuthorsMap() {
        return groupAuthors(authorRepository.findAll());
    }

    @NotNull
    private TreeMap<Character, TreeSet<Author>> groupAuthors(@NotNull List<Author> authors) {
        authors.forEach(this::createGroupByFirstLetter);
        return authorsMap;
    }

    private void createGroupByFirstLetter(@NotNull Author author) {
        char firstLetter = Character.toUpperCase(author.getName().charAt(0));
        if (!authorsMap.containsKey(firstLetter)) {
            authorsMap.put(firstLetter, new TreeSet<>());
        }

        authorsMap.get(firstLetter).add(author);
    }
}
