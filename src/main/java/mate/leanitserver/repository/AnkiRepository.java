package mate.leanitserver.repository;

import mate.leanitserver.model.AnkiCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnkiRepository extends JpaRepository<AnkiCard, Long> {
}
