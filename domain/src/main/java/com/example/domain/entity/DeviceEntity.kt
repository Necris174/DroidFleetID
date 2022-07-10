package com.example.domain.entity


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
    var sensors: List<Sensor>? = null,
    var address: String? = null
)