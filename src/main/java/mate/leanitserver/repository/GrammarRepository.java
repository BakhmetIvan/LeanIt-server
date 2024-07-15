package mate.leanitserver.repository;

import java.util.List;
import mate.leanitserver.model.Grammar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GrammarRepository extends JpaRepository<Grammar, Long> {
    @Query("SELECT g FROM Grammar g WHERE g.title LIKE %:title%")
    List<Grammar> findAllByTitle(String title);

    List<Grammar> findAllByIdIn(List<Long> id);
}
