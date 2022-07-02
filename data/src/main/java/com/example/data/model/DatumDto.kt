package com.example.data.model

import com.example.domain.entity.Coords
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DatumDto (
    @SerializedName("coords")
    @Expose
    var coords: Coords,
    @SerializedName("flags")
    @Expose
    var flags: Int? = null,
    @SerializedName("time")
    @Expose
    var time: Int? = null
)