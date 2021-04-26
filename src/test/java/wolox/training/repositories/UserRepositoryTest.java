package wolox.training.repositories;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wolox.training.models.Users;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private Users oneTestUser;

    @Before
    public void setUp() {
        oneTestUser = new Users();
        oneTestUser.setUsername("ramiselton");
        oneTestUser.setName("Ramiro Selton");
        oneTestUser.setBirthdate(LocalDate.parse("1990-03-27"));
    }

    @Test
    public void whenCreateUsers_thenUsersIsPersisted(){
        Users persistedUsers = userRepository.save(oneTestUser);
        assertNotNull(persistedUsers);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreateUserWithoutUsername_thenThrowException() {
        oneTestUser.setUsername(null);
        userRepository.save(oneTestUser);
    }

}
