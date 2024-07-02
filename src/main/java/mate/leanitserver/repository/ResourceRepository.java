package mate.leanitserver.repository;

import java.util.List;
import mate.leanitserver.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    @Query("SELECT r FROM Resource r WHERE r.title LIKE %:title%")
    List<Resource> findAllByTitle(String title);
}
