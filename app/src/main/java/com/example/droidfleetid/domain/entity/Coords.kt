package com.example.droidfleetid.domain.entity

data class Coords(
    val dir: Int,
    val lat: Double,
    val lon: Double,
    val speed: Int,
    val valid: Boolean
)
