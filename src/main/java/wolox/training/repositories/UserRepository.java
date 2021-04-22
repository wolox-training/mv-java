package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByName(String name);

    List<Users> findByBirthdateBetweenAndNameSequence(LocalDate startDate,
            LocalDate endDate, String sequence);

}
