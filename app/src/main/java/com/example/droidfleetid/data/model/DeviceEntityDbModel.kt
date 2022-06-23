package com.example.droidfleetid.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.droidfleetid.domain.entity.Sensor

@Entity(tableName = "device_entities")
data class DeviceEntityDbModel(
    val account_id: String,
    val id: String,
    @PrimaryKey
    val imei: String,
    val is_disabled: Boolean,
    val model: String? = null,
    val number: String? = null,
    var status: String? = null,
    var descr: String?= null,
    var data: String,
    var sensors: String? = null)