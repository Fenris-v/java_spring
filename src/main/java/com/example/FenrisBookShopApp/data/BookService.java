package com.example.FenrisBookShopApp.data;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final JdbcTemplate jbdcTemlate;

    @Autowired
    public BookService(JdbcTemplate jbdcTemlate) {
        this.jbdcTemlate = jbdcTemlate;
    }

    public List<Book> getBooksData() {
        String sql = "SELECT books.*, first_name, last_name FROM books LEFT JOIN authors on authors.id = author_id";
        List<Book> books = jbdcTemlate.query(sql, (ResultSet rs, int rowNum) -> makeBook(rs));
        return new ArrayList<>(books);
    }

    @NotNull
    private static Book makeBook(@NotNull ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setAuthor(rs.getString("first_name") + ' ' + rs.getString("last_name"));
        book.setTitle(rs.getString("title"));
        book.setPriceOld(rs.getString("price_old"));
        book.setPrice(rs.getString("price"));
        return book;
    }
}
