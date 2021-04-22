package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wolox.training.models.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByName(String name);

    @Query("SELECT u FROM Users u WHERE (:sequence IS NULL OR lower(u.name) "
            + "like lower(concat('%', :sequence,'%')))"
            + " and (:startDate IS NULL OR :endDate IS NULL "
            + "OR u.birthdate BETWEEN :startDate AND :endDate)")
    List<Users> findByNameIgnoreCaseContainingAndBirthdateBetween(LocalDate startDate,
            LocalDate endDate, String sequence);

}
