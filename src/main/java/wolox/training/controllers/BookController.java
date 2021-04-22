package wolox.training.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.services.OpenLibraryService;

@RestController
@RequestMapping("/api/books")
@Api
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OpenLibraryService openLibraryService;

    @GetMapping
    @ApiOperation(value = "List all Books")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Books found")
    })
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     *
     * @param bookTitle Title of a {@link Book}
     * @return List of {@link Book} with the title passed as parameter
     */
    @GetMapping("/title/{bookTitle}")
    @ApiOperation(value = "List all Books by Book Title")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Books found")
    })
    public List<Book> findByTitle(@PathVariable String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }

    /**
     *
     * @param id: Id of a book
     * @return books with the id passed as parameter
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Giving an Id, return the book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book found"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    public Book findOne(@PathVariable Long id) {
        return bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    /**
     *
     * @param book: book to be created
     * @return book created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book created"),
            @ApiResponse(code = 404, message = "Book not created")
    })
    public Book create(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    /**
     *
     * @param id: Book Id to be deleted
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a Book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book deleted"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    public void delete(@PathVariable Long id) {
        bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }

    /**
     *
     * @param book: Book to be updated
     * @param id: Book Id to be updated
     * @return Book updated
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "Update a Book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book updated"),
            @ApiResponse(code = 404, message = "Book not found"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (!book.getBookId().equals(id)) {
            throw new BookIdMismatchException();
        }
        bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }

    /**
     * @param isbn: isbn of a book
     * @return {@link Book} by ISBN
     */
    @GetMapping("/isbn/{isbn}")
    @ApiOperation(value = "Giving an isbn, return the book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book found"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    public ResponseEntity<Book> getBookByISBN(@PathVariable String isbn) {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        return book
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(
                        bookRepository.save(openLibraryService.bookInfo(isbn)
                                .orElseThrow(BookNotFoundException::new))
                        , HttpStatus.CREATED));
    }

    /**
     *
     * @param publisher: {@link Book} publisher
     * @param genre: {@link Book} genre
     * @param year: {@link Book} year
     * @return List of {@link Book}
     */
    @GetMapping
    @ApiOperation(value = "Giving an publisher, genre and year, return the books")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Book found"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    public List<Book> getBookByPublisherAndGenreAndYear(@RequestParam String publisher,
            @RequestParam String genre, @RequestParam String year) {

        return bookRepository.findByPublisherAndGenreAndYear(publisher, genre, year);
    }

}
