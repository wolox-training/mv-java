package wolox.training.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/loggedUser")
    @ApiOperation(value = "Logged user's username", response = String.class)
    @ApiResponses(value = @ApiResponse(code = 200, message = "Successfully username"))
    public Users currentUserName(Principal principal) {
        Users user = new Users();
        user.setUsername(principal.getName());
        return user;
    }

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
        return userRepository.findByName(userName)
                .orElseThrow(UserNotFoundException::new);
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

        user.setPassword(passwordEncoder.encode(user.getPassword()));

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
     * @param userId: {@link Users} id
     * @param password: {@link Users} password
     * @return {@link Users} updated
     */

    @PutMapping("/{id}/password")
    @ApiOperation(value = "Giving an id, updates the user password", response = Users.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "User updated successfully"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public Users updateUser(@PathVariable Long userId,
            @RequestParam(name = "password") String password) {

        Users user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

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
