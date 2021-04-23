package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByAuthor(String author);

    Page<Book> findByTitle(String bookTitle, Pageable pageable);

    Optional<Book> findByisbn(String isbn);

    @Query("Select b from Book b where (b.publisher = :publisher or :publisher IS NULL) "
            + "and (b.genre = :genre or :genre IS NULL) and b.year = :year or :year IS NULL")
    Page<Book> findByPublisherAndGenreAndYear(@Param("publisher") String publisher,
            @Param("genre") String genre, @Param("year") String year, Pageable pageable);

    @Query("Select b from Book b where (b.bookId = :bookId or :bookId IS NULL) "
            + "and (b.genre = :genre or :genre IS NULL) and (b.author = :author or :author IS NULL) "
            + "and (b.image = :image or :image IS NULL) and (b.title = :title or :title IS NULL) "
            + "and (b.subtitle = :subtitle or :subtitle IS NULL) and (b.publisher = :publisher "
            + "or :publisher IS NULL) and (b.year = :year or :year IS NULL) and (b.pages = :pages "
            + "or :pages IS NULL) and (b.isbn = :isbn or :isbn IS NULL)")
    Page<Book> findAll(@Param("bookId") Long bookId, @Param("genre") String genre,
            @Param("author") String author, @Param("image") String image, @Param("title") String title,
            @Param("subtitle") String subtitle, @Param("publisher") String publisher, @Param("year") String year,
            @Param("pages") Long pages, @Param("isbn") String isbn, Pageable pageable);

}
