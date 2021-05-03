package wolox.training.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wolox.training.models.Users;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private Users oneTestUser;

    @BeforeEach
    public void setUp() {
        oneTestUser = new Users();
        oneTestUser.setUsername("Rodriguez");
        oneTestUser.setName("Pepe Rodriguez");
        oneTestUser.setPassword("pwd");
        oneTestUser.setBirthdate(LocalDate.of(1998,11,11));
    }

    @Test
    public void whenCreateUsers_thenUsersIsPersisted(){
        Users persistedUsers = userRepository.save(oneTestUser);
        assertNotNull(persistedUsers);
    }

    @Test
    public void testingGuavaPreconditions(){
        Assertions.assertThrows(NullPointerException.class, ()-> oneTestUser.setUsername(null));
    }

    @Test
    public void testingFindByNameContainingAndBirthdateBetweenMethod(){
        userRepository.save(oneTestUser);
        Page<Users> users = userRepository.findByNameIgnoreCaseContainingAndBirthdateBetween(LocalDate.of(1998,9,11), LocalDate.of(1998,12,11), "EPE",
                PageRequest.of(1,3, Sort.by("id")));
        assertEquals(users.getTotalElements(), 1);
    }

    @Test
    public void testingFindByNameContainingAndBirthdateBetweenMethodWithNullValues(){
        userRepository.save(oneTestUser);
        Page<Users> users = userRepository.findByNameIgnoreCaseContainingAndBirthdateBetween(null, null, null, PageRequest.of(1,3, Sort.by("id")));
        assertEquals(users.getTotalElements(), 1);
    }

}
