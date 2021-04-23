package wolox.training.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private Book oneTestBook;

    @Before
    public void setUp() {
        oneTestBook = new Book();
        oneTestBook.setAuthor("Stephen King");
        oneTestBook.setGenre("Terror");
        oneTestBook.setImage("https://imagesforus.com/idsarf12.png");
        oneTestBook.setIsbn("4578-245654");
        oneTestBook.setPages(1500L);
        oneTestBook.setPublisher("Viking Press");
        oneTestBook.setSubtitle("Worst Clown Ever");
        oneTestBook.setTitle("It");
        oneTestBook.setYear("1986");

    }

    @Test
    public void whenCreateBook_thenBookIsPersisted() {
        Book persistedBook = bookRepository.findByTitle("It", PageRequest.of(0, 3))
                .stream().findFirst().orElseThrow(BookNotFoundException::new);

        assertThat(persistedBook.getTitle().equals(oneTestBook.getTitle())).isTrue();
        assertThat(persistedBook.getAuthor().equals(oneTestBook.getAuthor())).isTrue();
        assertThat(persistedBook.getGenre().equals(oneTestBook.getGenre())).isTrue();
        assertThat(persistedBook.getImage().equals(oneTestBook.getImage())).isTrue();
        assertThat(persistedBook.getIsbn().equals(oneTestBook.getIsbn())).isTrue();
        assertThat(persistedBook.getPages().equals(oneTestBook.getPages())).isTrue();

    }

    @Test
    public void testingFindByPublisherAndGenreAndYearMethod(){
        bookRepository.save(oneTestBook);
        Page<Book> books = bookRepository.findByPublisherAndGenreAndYear("Viking Press",
                "Terror", "1986", PageRequest.of(0, 3));
        assertEquals(books.getTotalElements(), 1);
    }

    @Test
    public void givenAnExistingTitle_whenFindAll_thenBooksIsReturned() {
        bookRepository.save(oneTestBook);
        Page<Book> books = bookRepository.findAll(null, null, null, null,
                "Viking Press", null, null, null, null, null,
                PageRequest.of(0, 3));
        assertEquals(books.getTotalElements(), 1);
    }

}
