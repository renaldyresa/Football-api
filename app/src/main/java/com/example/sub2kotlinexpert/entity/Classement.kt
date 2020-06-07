package com.example.sub2kotlinexpert.entity

data class Classement (
    var idTeam: String = "",
    var logo: String = "",
    var name: String = "",
    var goalsFor: Int = 0,
    var goalsAgainst: Int = 0,
    var goalsDifference: Int = 0,
    var win: Int = 0,
    var draw: Int = 0,
    var loss: Int = 0,
    var total: Int = 0
)