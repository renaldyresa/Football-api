package com.example.sub2kotlinexpert.helper

import android.annotation.SuppressLint
import android.database.Cursor
import com.example.sub2kotlinexpert.data.db.DatabaseContract
import com.example.sub2kotlinexpert.entity.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object Helper {

    fun mapCursorToArrayListMatch(cursor: Cursor?): ArrayList<FavoriteMatch> {
        val list = ArrayList<FavoriteMatch>()
        cursor?.apply {
            while (moveToNext()) {
                val id = getString(getColumnIndexOrThrow(DatabaseContract.MatchColumns.ID_MATCH))
                val league = getString(getColumnIndexOrThrow(DatabaseContract.MatchColumns.LEAGUE))
                val idHomeTeam = getString(getColumnIndexOrThrow(DatabaseContract.MatchColumns.ID_HOME_TEAM))
                val homeTeam = getString(getColumnIndexOrThrow(DatabaseContract.MatchColumns.HOME_TEAM))
                val homeLogo = getString(getColumnIndexOrThrow(DatabaseContract.MatchColumns.HOME_LOGO))
                val idAwayTeam = getString(getColumnIndexOrThrow(DatabaseContract.MatchColumns.ID_AWAY_TEAM))
                val awayTeam = getString(getColumnIndexOrThrow(DatabaseContract.MatchColumns.AWAY_TEAM))
                val awayLogo = getString(getColumnIndexOrThrow(DatabaseContract.MatchColumns.AWAY_LOGO))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.MatchColumns.DATE))
                val time = getString(getColumnIndexOrThrow(DatabaseContract.MatchColumns.TIME))
                list.add(FavoriteMatch(id, league, idHomeTeam, homeTeam, homeLogo, idAwayTeam, awayTeam, awayLogo, date, time))
            }
        }
        return list
    }

    fun mapCursorToArrayListTeam(cursor: Cursor?): ArrayList<Team> {
        val list = ArrayList<Team>()
        cursor?.apply {
            while (moveToNext()) {
                val idTeam = getString(getColumnIndexOrThrow(DatabaseContract.TeamColumns.ID_TEAM))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.TeamColumns.NAME))
                val logo = getString(getColumnIndexOrThrow(DatabaseContract.TeamColumns.LOGO))
                val formedYear = getString(getColumnIndexOrThrow(DatabaseContract.TeamColumns.FORMED_YEAR))
                val stadium = getString(getColumnIndexOrThrow(DatabaseContract.TeamColumns.STADIUM))
                list.add(Team(idTeam, logo, name, formedYear, stadium))
            }
        }
        return list
    }



    @SuppressLint("SimpleDateFormat")
    fun setTime(dateTime: String): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        val date = sdf.parse(dateTime) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.HOUR, 7)

        return sdf.format(calendar.time)
    }

    fun convertEventToMatch(match : JSONObject): Match{
        val matchItem = Match()
        matchItem.apply {
            id = match.getString("idEvent")
            name = match.getString("strEvent")
            league = match.getString("strLeague")
            idHomeTeam = match.getString("idHomeTeam")
            homeTeam = checkNullOrEmpty(match.getString("strHomeTeam"))
            homeScore = checkNullOrEmpty(match.getString("intHomeScore"))
            homeRedCard = cardCount(checkNullOrEmpty(match.getString("strHomeRedCards")))
            homeYellowCard = cardCount(checkNullOrEmpty(match.getString("strHomeYellowCards")))
            homeShot = checkNullOrEmpty(match.getString("intHomeShots"))
            idAwayTeam = match.getString("idAwayTeam")
            awayTeam = checkNullOrEmpty(match.getString("strAwayTeam"))
            awayScore = checkNullOrEmpty(match.getString("intAwayScore"))
            awayRedCard = cardCount(checkNullOrEmpty(match.getString("strAwayRedCards")))
            awayYellowCard = cardCount(checkNullOrEmpty(match.getString("strAwayYellowCards")))
            awayShot = checkNullOrEmpty(match.getString("intAwayShots"))
            round = checkNullOrEmpty(match.getString("intRound"))
            date = checkNullOrEmpty(match.getString("dateEvent"))
            time = checkNullOrEmpty(match.getString("strTime"))
        }

        return matchItem
    }

    fun convertTeam(team: JSONObject): Classement{
        val teamItem = Classement()
        teamItem.apply {
            idTeam = team.getString("teamid")
            name = team.getString("name")
            goalsFor = team.getInt("goalsfor")
            goalsAgainst = team.getInt("goalsagainst")
            goalsDifference = team.getInt("goalsdifference")
            win = team.getInt("win")
            draw = team.getInt("draw")
            loss = team.getInt("loss")
            total = team.getInt("total")
        }
        return teamItem
    }

    fun  convertListTeam(team: JSONObject): Team {
        val teamItem = Team()
        teamItem.apply {
            idTeam = team.getString("idTeam")
            logo = team.getString("strTeamBadge")
            name = team.getString("strTeam")
            formedYear = team.getString("intFormedYear")
            stadiumName = team.getString("strStadium")
        }
        return teamItem
    }

    fun  convertDetailTeam(team: JSONObject): TeamDetail {
        val teamItem = TeamDetail()
        teamItem.apply {
            idTeam = team.getString("idTeam")
            logo = team.getString("strTeamBadge")
            name = team.getString("strTeam")
            formedYear = team.getString("intFormedYear")
            league = team.getString("strLeague")
            alternativeName = team.getString("strAlternate")
            stadium = team.getString("strStadium")
            location = team.getString("strStadiumLocation")
            country = team.getString("strCountry")
            desc = team.getString("strDescriptionEN")
        }
        return teamItem
    }

    private fun checkNullOrEmpty(text: String): String{
        if (text == "null" || text.isEmpty())
            return "-"
        return text
    }

    private fun cardCount(text: String): String{
        if (text == "-" || text == "0")
            return text
        return text.split(";").size.toString()
    }

}