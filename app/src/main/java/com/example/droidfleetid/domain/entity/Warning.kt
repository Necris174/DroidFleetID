package com.example.droidfleetid.domain.entity


data class Warning(
    val coords: Coords,
    val timeBegin: String,
    val timeEnd: String,
    val warningMessage: String,
    val warningType: Int
)