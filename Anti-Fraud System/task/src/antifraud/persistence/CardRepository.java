package antifraud.persistence;

import antifraud.entity.Card;
import jakarta.persistence.Entity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {
    Optional<Card> findByNumber(String number);

    boolean existsByNumber(String number);
}
