package com.example.sub2kotlinexpert.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match(
    var id: String = "",
    var name: String = "",
    var league: String  = "",
    var idHomeTeam: String = "",
    var homeLogo: String = "",
    var homeTeam: String = "",
    var homeScore: String = "",
    var homeRedCard: String = "",
    var homeYellowCard: String = "",
    var homeShot: String = "",
    var idAwayTeam: String = "",
    var awayLogo: String = "",
    var awayTeam: String = "",
    var awayScore: String = "",
    var awayRedCard: String = "",
    var awayYellowCard: String = "",
    var awayShot: String = "",
    var round: String = "",
    var date: String = "",
    var time: String = ""
): Parcelable