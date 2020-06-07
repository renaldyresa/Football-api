package com.example.sub2kotlinexpert.data.db

import android.provider.BaseColumns

object DatabaseContract {

    class MatchColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "match"
            const val ID_ = "id_"
            const val ID_MATCH = "id_match"
            const val LEAGUE = "league"
            const val ID_HOME_TEAM = "id_home_team"
            const val HOME_TEAM = "home_team"
            const val HOME_LOGO = "home_logo"
            const val ID_AWAY_TEAM = "id_away_team"
            const val AWAY_TEAM = "away_team"
            const val AWAY_LOGO = "away_logo"
            const val DATE = "date"
            const val TIME = "time"

        }
    }

    class TeamColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "team"
            const val ID_ = "id_"
            const val ID_TEAM = "id_team"
            const val LOGO = "logo"
            const val NAME = "name"
            const val FORMED_YEAR = "formed_year"
            const val STADIUM = "stadium"
        }
    }

}