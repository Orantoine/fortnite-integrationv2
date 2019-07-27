package fr.orantoine.fortniteintegrationV2.services;

import fr.orantoine.fortniteintegrationV2.models.Match;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;

import java.sql.Date;

public class Converter {


    public Converter() {
    }

    public Match jsonMatchToMatch(JSONObject jsonMatch){
        JSONObject input = jsonMatch;
        Match match = new Match();
        match.setId(input.getInt("id"));
        match.setAccountId(input.getString("accountId"));
        Date date = stringToDate(input.getString("dateCollected"));
        match.setDateCollected(date);
        match.setKills(input.getInt("kills"));
        match.setMatches(input.getInt("matches"));
        match.setMinutesPlayed(input.getInt("minutesPlayed"));
        match.setPlatform(input.getInt("platform"));
        match.setPlaylist(input.getString("playlist"));
        match.setScore(input.getInt("score"));
        match.setTop1(input.getInt("top1"));
        match.setTop3(input.getInt("top3"));
        match.setTop5(input.getInt("top5"));
        match.setTop6(input.getInt("top6"));
        match.setTop10(input.getInt("top10"));
        match.setTop12(input.getInt("top12"));
        match.setTop25(input.getInt("top25"));
        match.setTrnRating(input.getLong("trnRating"));
        match.setTrnRatingChange(input.getLong("trnRatingChange"));

        return match;
    }

    public Date stringToDate(String stringDate){
        DateTimeFormatter df = DateTimeFormat.forPattern("YYYY-MM-dd'T'HH:mm:ss.SSS");
        long millis = df.parseMillis(stringDate);
        Date date  = new Date(millis);
        return date;
    }


}
