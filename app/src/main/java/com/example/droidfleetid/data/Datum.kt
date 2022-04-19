package com.example.droidfleetid.data

import com.example.droidfleetid.domain.entity.Coords
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

 data class Datum (
    @SerializedName("coords")
    @Expose
    var coords: Coords? = null,
    @SerializedName("flags")
    @Expose
    var flags: Int? = null,
    @SerializedName("time")
    @Expose
    var time: Int? = null
)