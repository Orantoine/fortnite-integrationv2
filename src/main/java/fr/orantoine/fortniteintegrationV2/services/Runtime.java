package fr.orantoine.fortniteintegrationV2.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import fr.orantoine.fortniteintegrationV2.models.Joueur;
import fr.orantoine.fortniteintegrationV2.models.Match;
import fr.orantoine.fortniteintegrationV2.respositories.JoueurRepository;
import fr.orantoine.fortniteintegrationV2.respositories.MatchRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


@Component
public class Runtime {

    private static final Logger log = LoggerFactory.getLogger(Runtime.class);

    @Autowired
    private JoueurRepository joueurRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Scheduled(cron = "0 0/1 * * * *",zone = "GMT+1:00")
    public void getData(){
        List<Joueur> joueurList = joueurRepository.findAll();
        for (Joueur joueur : joueurList) {
            log.info("Récupération des données pour le joueur : "+joueur.getPseudo());
            getInfoJoueurs(joueur.getPseudo());
            log.info("Récupération terminée pour le joueur : "+joueur.getPseudo());
        }

    }

    private void getInfoJoueurs(String pseudo) {
        try {
            HttpResponse<String> jsonResponse = Unirest.get("https://api.fortnitetracker.com/v1/profile/ps4/" + pseudo)
                    .header("accept", "application/json")
                    .header("TRN-Api-Key", "2dda56fe-8332-49f0-a4b5-7266757efdf4")
                    .header("cache-control", "no-cache").asString();
            log.debug(jsonResponse.getBody());
            saveInfo(jsonResponse);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    private void saveInfo(HttpResponse<String> jsonResponse) {
        if (jsonResponse != null && !jsonResponse.getBody().isEmpty()){
            JSONObject jsonObject = new JSONObject(jsonResponse.getBody());
            JSONArray recentMatch = jsonObject.getJSONArray("recentMatches");
            log.info(recentMatch.length()+" matchs trouvés pour le joueur");
            for (int i = 0; i<recentMatch.length();i++){
                JSONObject jsonMatch = recentMatch.getJSONObject(i);
                Converter converter = new Converter();
                Match match = converter.jsonMatchToMatch(jsonMatch);
                log.debug(match.toString());
                matchRepository.save(match);
            }

        }
    }
}
