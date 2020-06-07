package com.example.sub2kotlinexpert.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.sub2kotlinexpert.data.db.DatabaseContract.TeamColumns.Companion.ID_
import com.example.sub2kotlinexpert.data.db.DatabaseContract.TeamColumns.Companion.ID_TEAM
import com.example.sub2kotlinexpert.data.db.DatabaseContract.TeamColumns.Companion.TABLE_NAME
import java.sql.SQLException

class FavoriteTeamHelper (context: Context) {

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var dataBaseHelper: DatabaseHelper
        private lateinit var database: SQLiteDatabase
        private var INSTANCE: FavoriteTeamHelper? = null
        fun getInstance(context: Context): FavoriteTeamHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteTeamHelper(context)
            }
    }

    init {
        dataBaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun isOpen(): Boolean{
        return database.isOpen
    }

    fun close() {
        dataBaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$ID_ DESC")
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$ID_TEAM = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }


    fun deleteById(id_team: String): Int {
        return database.delete(DATABASE_TABLE, "$ID_TEAM = '$id_team'", null)
    }


}