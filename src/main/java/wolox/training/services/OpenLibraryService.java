package wolox.training.services;

import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wolox.training.dtos.BookInfoDTO;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;

@Service
public class OpenLibraryService {

    @Value("${external.api.url}")
    private String externalApiUrl;

    @Autowired
    RestTemplate restTemplate;

    public Optional<Book> bookInfo(Long isbn) {

        String queryParam = "ISBN:" + isbn;
        String url = String.format(externalApiUrl, queryParam);
        Book book = new Book();

        try {
            BookInfoDTO bookInfoDTO = restTemplate.getForObject(url, BookInfoDTO.class);
            adaptBookInfoToBookModel(bookInfoDTO, book);
        } catch (Exception e) {
            throw new BookNotFoundException();
        }

        return Optional.ofNullable(book);
    }

    private void adaptBookInfoToBookModel(BookInfoDTO bookInfo, Book book) {
        book.setIsbn(bookInfo.getIsbn());
        book.setYear(bookInfo.getPublishDate());
        book.setAuthor(bookInfo.getAuthors().toString());
        book.setPublisher(bookInfo.getPublishers().toString());
        book.setTitle(bookInfo.getTitle());
        book.setSubtitle(bookInfo.getSubtitle());
        book.setPages(bookInfo.getNumberOfPages());
        book.setImage("-");
    }

}
