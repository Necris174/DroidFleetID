package com.example.data.model

import com.example.domain.entity.Device
import com.example.domain.entity.User
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class SettingsDto(
    @SerializedName("user")
    @Expose
    var user: User? = null,

    @SerializedName("role")
    @Expose
    var role: String? = null,

    @SerializedName("device_groups")
    @Expose
    var deviceGroups: List<Any>? = null,

    @SerializedName("devices")
    @Expose
    var devices: List<Device>? = null,

    @SerializedName("features")
    @Expose
    var features: List<Any>? = null
)
