package com.example.sub2kotlinexpert.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sub2kotlinexpert.data.db.DatabaseContract.MatchColumns.Companion as dcMatch
import com.example.sub2kotlinexpert.data.db.DatabaseContract.TeamColumns.Companion as dcTeam

internal class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbmatchfavorite"

        private const val DATABASE_VERSION = 4

        private const val SQL_CREATE_TABLE_FAVORITE_MATCH = "CREATE TABLE ${dcMatch.TABLE_NAME}" +
                " (${dcMatch.ID_} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${dcMatch.ID_MATCH} TEXT NOT NULL," +
                " ${dcMatch.LEAGUE} TEXT NOT NULL," +
                " ${dcMatch.ID_HOME_TEAM} TEXT NOT NULL," +
                " ${dcMatch.HOME_TEAM} TEXT NOT NULL," +
                " ${dcMatch.HOME_LOGO} TEXT NOT NULL," +
                " ${dcMatch.ID_AWAY_TEAM} TEXT NOT NULL," +
                " ${dcMatch.AWAY_TEAM} TEXT NOT NULL," +
                " ${dcMatch.AWAY_LOGO} TEXT NOT NULL," +
                " ${dcMatch.DATE} TEXT NOT NULL," +
                " ${dcMatch.TIME} TEXT NOT NULL)"

        private const val SQL_CREATE_TABLE_FAVORITE_TEAM = "CREATE TABLE ${dcTeam.TABLE_NAME}" +
                " (${dcTeam.ID_} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${dcTeam.ID_TEAM} TEXT NOT NULL," +
                " ${dcTeam.LOGO} TEXT NOT NULL," +
                " ${dcTeam.NAME} TEXT NOT NULL," +
                " ${dcTeam.FORMED_YEAR} TEXT NOT NULL," +
                " ${dcTeam.STADIUM} TEXT NOT NULL)"


    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE_MATCH)
        db.execSQL(SQL_CREATE_TABLE_FAVORITE_TEAM)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.run {
            execSQL("DROP TABLE IF EXISTS '${dcMatch.TABLE_NAME}'")
            execSQL("DROP TABLE IF EXISTS '${dcTeam.TABLE_NAME}'")
            onCreate(this)
        }
    }

}