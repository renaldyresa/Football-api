package com.example.sub2kotlinexpert.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteMatch (
    var id: String = "",
    var league: String = "",
    var idHomeTeam: String = "",
    var homeTeam: String = "",
    var homeLogo: String = "",
    var idAwayTeam: String = "",
    var awayTeam: String = "",
    var awayLogo: String = "",
    var date: String = "",
    var time: String = ""
): Parcelable