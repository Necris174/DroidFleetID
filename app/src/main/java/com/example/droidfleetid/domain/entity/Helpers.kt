package com.example.droidfleetid.domain.entity

data class Helpers(
    val geozones: List<Any>,
    val sensors: List<Sensor>,
    val shifts: List<Shift>,
    val stops: List<Stop>,
    val warnings: List<Warning>
)
