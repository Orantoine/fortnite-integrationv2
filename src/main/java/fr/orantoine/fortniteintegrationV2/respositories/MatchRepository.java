package fr.orantoine.fortniteintegrationV2.respositories;

import fr.orantoine.fortniteintegrationV2.models.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchRepository extends MongoRepository<Match, Integer> {
}
