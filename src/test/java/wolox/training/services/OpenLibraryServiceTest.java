package wolox.training.services;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.Assert.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wolox.training.models.Book;

@ExtendWith(SpringExtension.class)
@Import(value = OpenLibraryService.class)
@TestPropertySource("classpath:test.application.properties")
public class OpenLibraryServiceTest {

    private String ISBN = "0385472579";
    private String URL = String.format("/api/books?bibkeys=ISBN:%s&format=json&jscmd=data", ISBN);

    private WireMockServer wireMockServer;

    @Autowired
    private OpenLibraryService openLibraryService;

    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer(8082, 8083);
        this.wireMockServer.start();
    }

    @AfterEach
    public void resetWiremock() {
        this.wireMockServer.stop();
    }

    @Test
    public void wiremockTest() {
        String isbn = "0385472579";
        String url = String.format("/api/books?bibkeys=ISBN:%s&format=json&jscmd=data", isbn);
        this.wireMockServer.stubFor(
                WireMock.get(url)
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("openLibraryResponse.json"))
        );

        Optional<Book> optionalBookInfoDTO = openLibraryService.bookInfo(isbn);

        assertEquals(optionalBookInfoDTO.get().getIsbn(), isbn);
    }

    @Test
    public void wiremockExceptionTest() {

        this.wireMockServer.stubFor(
                WireMock.get(URL)
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(""))
        );

        Assertions.assertThrows(IllegalArgumentException.class, ()-> openLibraryService.bookInfo(
                ISBN));
    }
}
