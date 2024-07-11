package mate.leanitserver.repository;

import java.util.List;
import java.util.Optional;
import mate.leanitserver.model.Resource;
import mate.leanitserver.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    @Query("SELECT r FROM Resource r WHERE r.title LIKE %:title%")
    List<Resource> findAllByTitle(String title);

    Optional<Resource> findByIdAndUser(Long id, User user);

    Page<Resource> findAllByUser(User user, Pageable pageable);
}
