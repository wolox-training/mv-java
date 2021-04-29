package wolox.training.models;

import com.google.common.base.Preconditions;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.BookNotFoundException;

@Data
@Entity
public class Users {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;

    @Column(nullable = false)
    private String username;

    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false)
    @ManyToMany
    private List<Book> books = Collections.emptyList();

    public void setUsername(String username) {

        Preconditions.checkNotNull(username, "Please check the String username supplied, its null!");
        this.username = username;
    }

    public void setName(String name) {

        Preconditions.checkNotNull(name, "Please check the String name supplied, its null!");
        this.name = name;
    }

    public void setBirthdate(LocalDate birthdate) {

        Preconditions.checkNotNull(birthdate, "Please check the LocalDate birthdate supplied, its null!");
        Preconditions.checkArgument(birthdate.isBefore(LocalDate.now()));
        this.birthdate = birthdate;
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public void setBooks(List<Book> books) {

        Preconditions.checkNotNull(books, "Please check the List of Books supplied, its null!");
        this.books = books;
    }

    public void addBook(Book book) {

        if (books.contains(book)) {
            throw new BookAlreadyOwnedException();
        }

        books.add(book);
    }

    public void deleteBook(Long bookId) {
        books.remove(filterBookById(bookId));
    }

    public Book filterBookById(Long bookId) {
        return books.stream().filter(book -> bookId.equals(book.getBookId())).findFirst().orElseThrow(
                BookNotFoundException::new);
    }

}
