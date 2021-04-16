package wolox.training.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    /**
     *
     * @param userName Name of a {@link Users}
     * @return {@link Users} with the name passed as parameter
     */
    @GetMapping("/name/{userName}")
    public Users findByName(@PathVariable String userName) {

        Users user;
        try {
            user = userRepository.findByName(userName);
        } catch(Exception e) {
            throw new UserNotFoundException();
        }

        return user;
    }

    /**
     *
     * @param id: Id of a user
     * @return {@link Users} with the id passed as parameter
     */
    @GetMapping("/{id}")
    public Users findOne(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    /**
     *
     * @param user: user to be created
     * @return {@link Users} created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Users create(@RequestBody Users user) {
        return userRepository.save(user);
    }

    /**
     *
     * @param id: User Id to be deleted
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(id);
    }

    /**
     *
     * @param user: {@link Users} to be updated
     * @param id: User Id to be updated
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
     *
     * @param book: Book to be added
     * @param id: User Id where the book will be added
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
     *
     * @param book: Book to be deleted
     * @param id: User Id where the book will be deleted
     * @return {@link Users} with book deleted
     */
    @DeleteMapping("/book/{id}")
    public Users delete(@RequestBody Book book, @PathVariable Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        user.deleteBook(book.getBookId());
        return userRepository.save(user);
    }
}
