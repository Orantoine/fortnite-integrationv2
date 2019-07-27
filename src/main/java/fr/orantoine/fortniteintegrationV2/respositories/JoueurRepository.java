package fr.orantoine.fortniteintegrationV2.respositories;

import fr.orantoine.fortniteintegrationV2.models.Joueur;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JoueurRepository extends MongoRepository<Joueur,String> {

    Joueur findByPseudo(String pseudo);
}
