package wolox.training.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wolox.training.models.Book;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private Book oneTestBook;

    @BeforeEach
    public void setUp() {
        oneTestBook = new Book();
        oneTestBook.setTitle("IT");
        oneTestBook.setPages(234L);
        oneTestBook.setPublisher("Viking Press");
        oneTestBook.setIsbn("11112222333");
        oneTestBook.setImage("image2.png");
        oneTestBook.setGenre("terror");
        oneTestBook.setAuthor("Stephen King");
        oneTestBook.setSubtitle("-");
        oneTestBook.setYear("1986");
    }

    @Test
    public void whenCreateBook_thenBookIsPersisted() {
        Book persistedBook = bookRepository.save(oneTestBook);
        assertNotNull(persistedBook);
    }

    @Test
    public void testingGuavaPreconditions() {
        Assertions.assertThrows(NullPointerException.class, () -> oneTestBook.setAuthor(null));
    }

    @Test
    public void whenFindByPublisherAndGenreAndYear_thenBooksIsReturned() {
        bookRepository.save(oneTestBook);
        Page<Book> books = bookRepository
                .findByPublisherAndGenreAndYear("Viking Press", "terror", "1986", PageRequest.of(1,3));
        assertEquals(books.getTotalElements(), 1);
    }

    @Test
    public void whenFindByPublisherAndGenreAndYearWithNullValues_thenBooksIsReturned() {
        bookRepository.save(oneTestBook);
        Page<Book> books = bookRepository.findByPublisherAndGenreAndYear(null, null, null, PageRequest.of(1,3));
        assertEquals(books.getTotalElements(), 1);
    }

}

