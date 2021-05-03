package wolox.training.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;
import lombok.Data;

@Data
public class BookInfoDTO {

    @JsonProperty("isbn_10")
    private String isbn;

    private String title;

    private String subtitle;

    private List<PublisherDTO> publishers = Collections.emptyList();

    @JsonProperty("publish_date")
    private String publishDate;

    @JsonProperty("number_of_pages")
    private Long numberOfPages;

    private List<AuthorDTO> authors = Collections.emptyList();

}
