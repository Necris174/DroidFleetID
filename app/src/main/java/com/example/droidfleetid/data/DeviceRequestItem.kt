package com.example.droidfleetid.data

import com.google.gson.annotations.SerializedName

data class DeviceRequestItem(
    @SerializedName("account_id") val account_id: String,
    @SerializedName("imei") val imei: String
)