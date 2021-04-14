package wolox.training.models;

import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
@ApiModel(description = "Books from the OpenLibraryAPI")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long bookId;

    @Column(nullable = true)
    @ApiModelProperty(notes = "The book genre: could be horror, comedy, drama, etc")
    private String genre;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Name of the book author")
    private String author;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Title of the book")
    private String title;

    @Column(nullable = false)
    private String subtitle;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Year of the book")
    private String year;

    @Column(nullable = false)
    @ApiModelProperty(notes = "Number of pages of the book")
    private String pages;

    @Column(nullable = false)
    private String isbn;

    @ManyToMany(mappedBy = "books")
    private List<Users> users;

    public Book() {

    }

    public Long getBookId() {
        return bookId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {

        Preconditions.checkNotNull(author, "Please check the String author supplied, its null!");
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {

        Preconditions.checkNotNull(image, "Please check the String image supplied, its null!");
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        Preconditions.checkNotNull(title, "Please check the String title supplied, its null!");
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {

        Preconditions.checkNotNull(subtitle, "Please check the String subtitle supplied, its null!");
        this.subtitle = subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {

        Preconditions.checkNotNull(publisher, "Please check the String publisher supplied, its null!");
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {

        Preconditions.checkNotNull(year, "Please check the String year supplied, its null!");
        this.year = year;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {

        Preconditions.checkNotNull(pages, "Please check the String pages supplied, its null!");
        this.pages = pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {

        Preconditions.checkNotNull(isbn, "Please check the String isbn supplied, its null!");
        this.isbn = isbn;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {

        Preconditions.checkNotNull(users, "Please check the List of Users supplied, its null!");
        this.users = users;
    }
}

