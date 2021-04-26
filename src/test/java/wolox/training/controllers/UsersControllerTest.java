package wolox.training.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import wolox.training.models.Users;
import wolox.training.repositories.UserRepository;

@WebMvcTest(UserController.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository mockUserRepository;

    private Users oneTestUser;

    private String jsonListOfUser = "[{\"userId\":null,\"username\":\"ramiselton\","
            + "\"name\":\"Ramiro Selton\",\"birthdate\":\"1990-03-27\""
            + ",\"books\":[]}]]}";

    private String userJson = "{\"userId\":null,\"username\":\"ramiselton\","
            + "\"name\":\"Ramiro Selton\",\"birthdate\":\"1990-03-27\""
            + ",\"books\":[]}]}";

    @BeforeEach
    public void setUp() {
        oneTestUser = new Users();
        oneTestUser.setUsername("ramiselton");
        oneTestUser.setName("Ramiro Selton");
        oneTestUser.setBirthdate(LocalDate.parse("1990-03-27"));
    }

    @Test
    public void whenFindAllUsers_thenUsersIsReturned() throws Exception {

        List<Users> users = Arrays.asList(oneTestUser);

        Mockito.when(mockUserRepository.findAll()).thenReturn(users);
        String url = ("/api/users");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonListOfUser));

    }

    @Test
    public void givenExistingName_whenFindByName_thenUserIsReturned() throws Exception {

        Mockito.when(mockUserRepository.findByName("Ramiro Selton")).thenReturn(
                java.util.Optional.ofNullable(oneTestUser));
        String url = ("/api/users/name/Ramiro Selton");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(userJson));
    }

    @Test
    public void givenExistingId_whenFindById_thenUserIsReturned() throws Exception {
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(oneTestUser));
        String url = ("/api/users/1");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(userJson));

    }

}
