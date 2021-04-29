package wolox.training.controllers;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.Users;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public Page<Users> findAll(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "userId") String sort) {
        return userRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));
    }

    /**
     * @param userName Name of a {@link Users}
     *
     * @return {@link Users} with the name passed as parameter
     */
    @GetMapping("/name/{userName}")
    public Users findByName(@PathVariable String userName) {
        return userRepository.findByName(userName)
                .orElseThrow(UserNotFoundException::new);
    }

    /**
     * @param id: Id of a user
     *
     * @return {@link Users} with the id passed as parameter
     */
    @GetMapping("/{id}")
    public Users findOne(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    /**
     * @param user: user to be created
     *
     * @return {@link Users} created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Users create(@RequestBody Users user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    /**
     * @param id: User Id to be deleted
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(id);
    }

    /**
     * @param user: {@link Users} to be updated
     * @param id:   User Id to be updated
     *
     * @return {@link Users} updated
     */
    @PutMapping("/{id}")
    public Users updateUser(@RequestBody Users user, @PathVariable Long id) {
        if (!user.getUserId().equals(id)) {
            throw new UserIdMismatchException();
        }
        userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return userRepository.save(user);
    }

    /**
     * @param book: Book to be added
     * @param id:   User Id where the book will be added
     *
     * @return {@link Users} with book added
     */
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Users addBook(@RequestBody Book book, @PathVariable Long id) {

        Users user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        user.addBook(book);

        return userRepository.save(user);
    }

    /**
     * @param book: Book to be deleted
     * @param id:   User Id where the book will be deleted
     *
     * @return {@link Users} with book deleted
     */
    @DeleteMapping("/book/{id}")
    public Users delete(@RequestBody Book book, @PathVariable Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        user.deleteBook(book.getBookId());
        return userRepository.save(user);
    }

    /**
     * @param authentication: login {@link Users}
     *
     * @return login {@link Users} name
     */
    @GetMapping("/username")
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }

    /**
     * @param startDate: initial date for search {@link Users}
     * @param endDate:   final date for search {@link Users}
     * @param sequence:  {@link Users} name character sequence
     *
     * @return Page of {@link Users}
     */
    @GetMapping("/specific")
    public Page<Users> getUsersByBirthdateAndCharacterSequenceName(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String sequence,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        return userRepository.findByNameIgnoreCaseContainingAndBirthdateBetween(startDate, endDate, sequence, PageRequest.of(0,3));
    }


}
