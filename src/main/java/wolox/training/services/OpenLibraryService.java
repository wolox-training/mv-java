package wolox.training.services;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Optional;
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

    public Optional<Book> bookInfo(String isbn) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        RestTemplate restTemplate = new RestTemplate();
        String queryParam = "ISBN:" + isbn;
        String url = String.format(externalApiUrl, queryParam);
        try {
            JsonNode bookInfoJsonNode = objectMapper
                    .readTree(restTemplate.getForObject(url, String.class));
            HashMap<String, Object> map = objectMapper
                    .treeToValue(bookInfoJsonNode.get(queryParam), HashMap.class);
            return Optional.ofNullable(objectMapper.convertValue(map, BookInfoDTO.class))
                    .map(bookInfoDTO -> buildBookModel(bookInfoDTO, isbn));
        } catch (Exception e) {
            throw new BookNotFoundException();
        }
    }
    private Book buildBookModel(BookInfoDTO bookInfo, String isbn) {
        Book book = new Book();
        book.setIsbn(isbn);
        book.setYear(bookInfo.getPublishDate());
        book.setAuthor(bookInfo.getAuthors().toString());
        book.setPublisher(bookInfo.getPublishers().toString());
        book.setTitle(bookInfo.getTitle());
        book.setSubtitle(bookInfo.getSubtitle());
        book.setPages(bookInfo.getNumberOfPages());
        book.setImage("-");
        return book;
    }

}
