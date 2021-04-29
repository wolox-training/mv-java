package wolox.training.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
        Book persistedBook = bookRepository.findByTitle("It")
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
        List<Book> books = bookRepository.findByPublisherAndGenreAndYear("Viking Press", "Terror", "1986");
        assertThat(books, is(not(empty())));
    }

    @Test
    public void givenAnExistingTitle_whenFindAll_thenBooksIsReturned() {
        bookRepository.save(oneTestBook);
        List<Book> books = bookRepository.findAll(null, null, null, null,
                "Viking Press", null, null, null, null, null);
        assertThat(books, is(not(empty())));
    }

}
