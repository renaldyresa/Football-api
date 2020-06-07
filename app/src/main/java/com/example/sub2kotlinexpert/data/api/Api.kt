package com.example.sub2kotlinexpert.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("eventsnextleague.php")
    fun getNextMatch(
        @Query("id")
        league: String
    ): Call<EventResponse>

    @GET("eventspastleague.php")
    fun getPreviousMatch(
        @Query("id")
        league: String
    ): Call<EventResponse>

    @GET("searchevents.php")
    fun getSearchMatch(
        @Query("e")
        team: String
    ): Call<MatchResponse>

    @GET("searchteams.php")
    fun getSearchTeam(
        @Query("t")
        team: String
    ): Call<TeamResponse>

    @GET("lookupevent.php?")
    fun getDetailMatch(
        @Query("id")
        id: String
    ): Call<EventResponse>


    @GET("lookupteam.php")
    fun getTeamDetail(
        @Query("id")
        team: String
    ): Call<TeamResponse>

    @GET("lookuptable.php")
    fun getClassementLeague(
        @Query("l")
        id: String
    ): Call<ClassementResponse>

    @GET("lookup_all_teams.php")
    fun getLeagueTeam(
        @Query("id")
        id: String
    ): Call<TeamResponse>
}