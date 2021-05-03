package wolox.training.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import wolox.training.models.Users;
import wolox.training.repositories.UserRepository;

@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository mockUserRepository;

    @MockBean
    private PasswordEncoder password;

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
        oneTestUser.setPassword("123456");
        oneTestUser.setName("Ramiro Selton");
        oneTestUser.setBirthdate(LocalDate.parse("1990-03-27"));
    }

    @Test
    @WithMockUser
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
    @WithMockUser
    public void givenExistingId_whenFindById_thenUserIsReturned() throws Exception {
        Mockito.when(mockUserRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(oneTestUser));
        String url = ("/api/users/1");
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(userJson));

    }

    @Test
    public void testingGetAllUsers_WithoutAuth() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/users/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

}
