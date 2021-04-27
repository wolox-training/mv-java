package wolox.training.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByAuthor(String author);

    List<Book> findByTitle(String bookTitle);

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByPublisherAndGenreAndYear(String publisher, String genre, String year);

}
