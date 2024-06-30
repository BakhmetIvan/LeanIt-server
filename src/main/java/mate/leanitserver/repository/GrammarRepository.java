package mate.leanitserver.repository;

import java.util.List;
import mate.leanitserver.model.Grammar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrammarRepository extends JpaRepository<Grammar, Long> {
    List<Grammar> findAllByTitle(String title);
}
