package com.example.FenrisBookShopApp.repositories.book;

import com.example.FenrisBookShopApp.dto.book.BookPopularityDto;
import com.example.FenrisBookShopApp.entities.book.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Page<BookEntity> findAllByOrderByPopularityDescPubDateDesc(Pageable page);

    @Query("select b from BookEntity b left join Book2GenreEntity b2g on b.id = b2g.bookId where b2g.genreId = ?1 order by b.pubDate desc")
    Page<BookEntity> getByGenreId(Long genreId, Pageable page);

    Page<BookEntity> findBookByTitleContainingIgnoreCaseOrderByTitle(String bookTitle, Pageable page);

    Page<BookEntity> findBookEntitiesByPubDateGreaterThanOrderByPubDateDesc(LocalDateTime date, Pageable page);

    Page<BookEntity> findBooksByPubDateBetweenOrderByPubDateDesc(LocalDateTime from, LocalDateTime to, Pageable page);

    @Query("select b from BookEntity b inner join Book2AuthorEntity b2a on b.id = b2a.bookId where b2a.authorId = ?1 " +
            "order by b2a.sortIndex")
    Page<BookEntity> findBooksByAuthorId(Long authorId, Pageable page);

    @Query("select b from BookEntity b inner join Book2TagEntity b2t on b.id = b2t.bookId where b2t.tagId = ?1 " +
            "order by b.popularity desc, b.pubDate desc ")
    Page<BookEntity> findBooksByTagId(Long tagId, Pageable page);

    @Query("select new com.example.FenrisBookShopApp.dto.book.BookPopularityDto(b, count(b2u), count(c), count(db)) " +
            "from BookEntity b left join DeferredBookEntity db on b.id = db.bookId left join CartEntity c on b.id = c.bookId " +
            "left join Book2UserEntity b2u on b.id = b2u.bookId group by b.id")
    List<BookPopularityDto> findAllWithBuyerAndDeferredAndCartCount();

    BookEntity findBookEntityBySlug(String slug);

    List<BookEntity> findBookEntitiesByIdIn(List<Long> ids);

    @Query("select b from BookEntity b left join Book2UserEntity b2u on b.id = b2u.bookId " +
            "where b2u.userId = ?1 order by b2u.time desc")
    List<BookEntity> findBookEntitiesByUserId(Long id);
}
