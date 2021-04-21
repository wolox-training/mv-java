package wolox.training.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository mockBookRepository;

    private Book oneTestBook;

    private String bookJson = "{\"bookId\":null,\"genre\":\"Terror\","
            + "\"author\":\"Stephen King\",\"image\":\"https://imagesforus.com/idsarf12.png\""
            + ",\"title\":\"It\",\"subtitle\":\"Worst Clown Ever\",\"publisher\":"
            + "\"Viking Press\",\"year\":\"1986\",\"pages\":\"1500\",\"isbn\":\"4578-245654\","
            + "\"users\":null}]}";

    private String listOfBookJson = "[{\"bookId\":null,\"genre\":\"Terror\","
            + "\"author\":\"Stephen King\",\"image\":\"https://imagesforus.com/idsarf12.png\""
            + ",\"title\":\"It\",\"subtitle\":\"Worst Clown Ever\",\"publisher\":"
            + "\"Viking Press\",\"year\":\"1986\",\"pages\":1500,\"isbn\":\"4578-245654\","
            + "\"users\":null}]]}";

    @BeforeEach
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
    public void givenExistingId_whenFindById_thenBookIsReturned() throws Exception {
        Mockito.when(mockBookRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(oneTestBook));
        String url = "/api/books/1";
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(bookJson));

    }

    @Test
    public void whenFindAllBooksWhichExists_thenBooksIsReturned() throws Exception {
        List<Book> books = Arrays.asList(oneTestBook);

        Mockito.when(mockBookRepository.findAll()).thenReturn(books);
        String url = "/api/books";
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(listOfBookJson));

    }

    @Test
    public void givenExistingTitle_whenFindByTitle_thenBookIsReturned() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(oneTestBook);

        Mockito.when(mockBookRepository.findByTitle("Stephen King")).thenReturn(books);
        String url = "/api/books/title/Stephen King";
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(listOfBookJson));

    }

    @Test
    public void testingGetAllBooks_WithoutAuth() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/books/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }


}
