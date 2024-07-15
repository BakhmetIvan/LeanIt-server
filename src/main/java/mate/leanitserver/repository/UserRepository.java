package mate.leanitserver.repository;

import java.util.Optional;
import mate.leanitserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);
}
