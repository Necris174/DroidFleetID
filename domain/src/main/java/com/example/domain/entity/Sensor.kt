package com.example.domain.entity


data class Sensor(
    var name: String? = null,
    var value: String? = null,
    var units: String? = null,
    var varName: String? = null,
    var ooType: Int? = null,
    var time: Long? = null
)

