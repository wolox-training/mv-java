package wolox.training.dtos;

import lombok.Data;

@Data
public class BookDTO {

    private String genre;

    private String author;

    private String image;

    private String title;

    private String subtitle;

    private String publisher;

    private String year;

    private Integer pages;

    private String isbn;

}
