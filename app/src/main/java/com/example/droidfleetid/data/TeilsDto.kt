package com.example.droidfleetid.data

import com.example.droidfleetid.domain.entity.Sensor
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TeilsDto(
    @SerializedName("account_id")
    @Expose
    var accountId: String,
    @SerializedName("imei")
    @Expose
     var imei: String,
    @SerializedName("status")
    @Expose
    var status: String ,
    @SerializedName("descr")
    @Expose
    var descr: String,
    @SerializedName("data")
    @Expose
    var data: List<Datum>?  = null,
    @SerializedName("sensors")
    @Expose
    var sensors: List<Sensor>? = null)


