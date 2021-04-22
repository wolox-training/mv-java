package wolox.training.services;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
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

    public Optional<Book> bookInfo(String isbn) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        String queryParam = "ISBN:" + isbn;
        String url = String.format(externalApiUrl, queryParam);
        Book book = new Book();

        try {
            /*BookInfoDTO bookInfoDTO = restTemplate.getForObject(url, BookInfoDTO.class);*/
            JsonNode bookInfoJsonNode = objectMapper
                    .readTree(restTemplate.getForObject(url, String.class));
            HashMap<String, Object> map = objectMapper
                    .treeToValue(bookInfoJsonNode.get(queryParam), HashMap.class);
            BookInfoDTO bookInfo = objectMapper.convertValue(map, BookInfoDTO.class);

            if (bookInfo != null) {
                bookInfo.setIsbn(isbn);
            }

            adaptBookInfoToBookModel(bookInfo, book);
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
