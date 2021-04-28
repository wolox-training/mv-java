package wolox.training.repositories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.List;
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

    @Test
    public void whenfindByNameIgnoreCaseContainingAndBirthdateBetweenMethod_theUsersIsReturned(){
        userRepository.save(oneTestUser);
        List<Users> users = userRepository.findByNameIgnoreCaseContainingAndBirthdateBetween
                (LocalDate.of(1996,4,11),
                        LocalDate.of(1996,6,11), "TIAS" );
        assertThat(users, is(not(empty())));
    }

    @Test
    public void whenfindByNameIgnoreCaseContainingNullAndDateNull_theUsersIsReturned() {
        userRepository.save(oneTestUser);
        List<Users> users = userRepository.
                findByNameIgnoreCaseContainingAndBirthdateBetween(null,
                        null,
                        null);
        assertThat(users, is(not(empty())));
    }
}
