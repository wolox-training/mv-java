package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByAuthor(String author);

    List<Book> findByTitle(String bookTitle);

    Optional<Book> findByIsbn(String isbn);

    @Query("Select b from Book b where (b.publisher = :publisher or :publisher IS NULL) "
            + "and (b.genre = :genre or :genre IS NULL) and b.year = :year or :year IS NULL")
    List<Book> findByPublisherAndGenreAndYear(@Param("publisher") String publisher, @Param("genre") String genre, @Param("year") String year);

}
