package com.example.FenrisBookShopApp.data;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

@Service
public class AuthorService {
    private final JdbcTemplate jbdcTemlate;
    private final TreeMap<Character, TreeSet<Author>> authorsMap = new TreeMap<>();

    @Autowired
    public AuthorService(JdbcTemplate jbdcTemlate) {
        this.jbdcTemlate = jbdcTemlate;
    }

    public TreeMap<Character, TreeSet<Author>> getAuthorsMap() {
        String sql = "SELECT * FROM authors";
        List<Author> authors = jbdcTemlate.query(sql, (ResultSet rs, int rowNum) -> makeAuthors(rs));

        return groupAuthors(authors);
    }

    @NotNull
    private Author makeAuthors(@NotNull ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setId(rs.getInt("id"));
        author.setFirstName(rs.getString("first_name"));
        author.setLastName(rs.getString("last_name"));
        return author;
    }

    @NotNull
    private TreeMap<Character, TreeSet<Author>> groupAuthors(@NotNull List<Author> authors) {
        authors.forEach(this::createGroupByFirstLetter);
        return authorsMap;
    }

    private void createGroupByFirstLetter(@NotNull Author author) {
        char firstLetter = Character.toUpperCase(author.getFirstName().charAt(0));
        if (!authorsMap.containsKey(firstLetter)) {
            authorsMap.put(firstLetter, new TreeSet<>());
        }

        authorsMap.get(firstLetter).add(author);
    }
}
