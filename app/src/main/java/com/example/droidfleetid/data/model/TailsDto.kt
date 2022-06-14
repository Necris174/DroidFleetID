package com.example.droidfleetid.data.model

import com.example.droidfleetid.data.model.Datum
import com.example.droidfleetid.domain.entity.Sensor
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TailsDto (
    @SerializedName("account_id")
    @Expose
    var accountId: String? = null,
    @SerializedName("imei")
    @Expose
     var imei: String? = null,
    @SerializedName("status")
    @Expose
    var status: String? = null,
    @SerializedName("descr")
    @Expose
    var descr: String? = null,
    @SerializedName("data")
    @Expose
    var data: List<Datum>?  = null,
    @SerializedName("sensors")
    @Expose
    var sensors: List<Sensor>? = null)


