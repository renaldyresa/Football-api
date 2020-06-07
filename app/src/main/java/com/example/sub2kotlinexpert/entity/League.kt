package com.example.sub2kotlinexpert.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League(
    var id: Int = 0,
    var name: String = "",
    var image: Int = 0,
    var desc: String = ""
): Parcelable