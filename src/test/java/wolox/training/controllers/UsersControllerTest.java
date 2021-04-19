package wolox.training.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.jws.soap.SOAPBinding.Use;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Book;
import wolox.training.models.Users;
import wolox.training.repositories.UserRepository;

@WebMvcTest(UserController.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository mockUserRepository;

    private Users oneTestUser;

    @BeforeEach
    public void setUp() {
        oneTestUser = new Users();
        oneTestUser.setUsername("ramiselton");
        oneTestUser.setName("Ramiro Selton");
        oneTestUser.setBirthdate(LocalDate.parse("1990-03-27"));
    }

    @Test
    public void whenFindAllUsers_thenUsersIsReturned() throws Exception {

        List<Users> users = new ArrayList<>();
        users.add(oneTestUser);

        Mockito.when(mockUserRepository.findAll()).thenReturn(users);
        String url = ("/api/users");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{\"userId\":null,\"username\":\"ramiselton\","
                                + "\"name\":\"Ramiro Selton\",\"birthdate\":\"1990-03-27\""
                                + ",\"books\":[]}]]}"
                ));

    }

    @Test
    public void whenFindByNameUserWhichExists_thenUserIsReturned() throws Exception {

        Mockito.when(mockUserRepository.findByName("Ramiro Selton")).thenReturn(
                java.util.Optional.ofNullable(oneTestUser));
        String url = ("/api/users/name/Ramiro Selton");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"userId\":null,\"username\":\"ramiselton\","
                                + "\"name\":\"Ramiro Selton\",\"birthdate\":\"1990-03-27\""
                                + ",\"books\":[]}]}"
                ));
    }

    @Test
    public void whenFindByIdWhichExists_thenUserIsReturned() throws Exception {
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(oneTestUser));
        String url = ("/api/users/1");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"userId\":null,\"username\":\"ramiselton\","
                                + "\"name\":\"Ramiro Selton\",\"birthdate\":\"1990-03-27\""
                                + ",\"books\":[]}]}"
                ));

    }

}
