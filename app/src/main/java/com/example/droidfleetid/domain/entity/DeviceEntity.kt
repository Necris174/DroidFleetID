package com.example.droidfleetid.domain.entity

import com.example.droidfleetid.data.model.Datum


data class DeviceEntity(
    val account_id: String,
    val id: String,
    val imei: String,
    val is_disabled: Boolean,
    val model: String? = null,
    val number: String? = null,
    var status: String? = null,
    var descr: String? = null,
    var data: List<Datum>,
    var sensors: List<Sensor>? = null
)