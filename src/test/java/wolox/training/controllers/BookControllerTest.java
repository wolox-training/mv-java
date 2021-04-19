package wolox.training.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository mockBookRepository;

    private Book oneTestBook;

    @BeforeEach
    public void setUp() {
        oneTestBook = new Book();
        oneTestBook.setAuthor("Stephen King");
        oneTestBook.setGenre("Terror");
        oneTestBook.setImage("https://imagesforus.com/idsarf12.png");
        oneTestBook.setIsbn("4578-245654");
        oneTestBook.setPages("1500");
        oneTestBook.setPublisher("Viking Press");
        oneTestBook.setSubtitle("Worst Clown Ever");
        oneTestBook.setTitle("It");
        oneTestBook.setYear("1986");

    }

    @Test
    public void whenFindByIdWhichExists_thenBookIsReturned() throws Exception {
        Mockito.when(mockBookRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(oneTestBook));
        String url = ("/api/books/1");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"bookId\":null,\"genre\":\"Terror\","
                        + "\"author\":\"Stephen King\",\"image\":\"https://imagesforus.com/idsarf12.png\""
                        + ",\"title\":\"It\",\"subtitle\":\"Worst Clown Ever\",\"publisher\":"
                        + "\"Viking Press\",\"year\":\"1986\",\"pages\":\"1500\",\"isbn\":\"4578-245654\","
                        + "\"users\":null}]}"
                ));

    }

    @Test
    public void whenFindAllBooksWhichExists_thenBooksIsReturned() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(oneTestBook);

        Mockito.when(mockBookRepository.findAll()).thenReturn(books);
        String url = ("/api/books");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{\"bookId\":null,\"genre\":\"Terror\","
                                + "\"author\":\"Stephen King\",\"image\":\"https://imagesforus.com/idsarf12.png\""
                                + ",\"title\":\"It\",\"subtitle\":\"Worst Clown Ever\",\"publisher\":"
                                + "\"Viking Press\",\"year\":\"1986\",\"pages\":\"1500\",\"isbn\":\"4578-245654\","
                                + "\"users\":null}]]}"
                ));

    }

    @Test
    public void whenFindByTitleBooksWhichExists_thenBookIsReturned() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(oneTestBook);

        Mockito.when(mockBookRepository.findByTitle("Stephen King")).thenReturn(books);
        String url = ("/api/books/title/Stephen King");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{\"bookId\":null,\"genre\":\"Terror\","
                                + "\"author\":\"Stephen King\",\"image\":\"https://imagesforus.com/idsarf12.png\""
                                + ",\"title\":\"It\",\"subtitle\":\"Worst Clown Ever\",\"publisher\":"
                                + "\"Viking Press\",\"year\":\"1986\",\"pages\":\"1500\",\"isbn\":\"4578-245654\","
                                + "\"users\":null}]]}"
                ));

    }



}
