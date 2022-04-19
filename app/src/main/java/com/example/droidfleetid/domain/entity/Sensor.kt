package com.example.droidfleetid.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Sensor(
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("value")
    @Expose
    var value: String? = null,
    @SerializedName("units")
    @Expose
    var units: String? = null,
    @SerializedName("varName")
    @Expose
    var varName: String? = null,
    @SerializedName("ooType")
    @Expose
    var ooType: Int? = null,
    @SerializedName("time")
    @Expose
    var time: Long? = null
)

