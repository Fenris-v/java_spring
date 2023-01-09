package com.example.FenrisBookShopApp.entities.book;

import com.example.FenrisBookShopApp.enums.BookFileType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "book_files")
public class BookFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hash;

    @Column(nullable = false)
    private int typeId;

    @Column(nullable = false)
    private String path;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private BookEntity book;

    public String getBookFileExtension() {
        return BookFileType.getFileExtensionByTypeId(typeId);
    }
}
