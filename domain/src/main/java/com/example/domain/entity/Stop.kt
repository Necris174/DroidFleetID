package com.example.domain.entity

data class Stop(
    val coords: Coords,
    val properties: List<Property>,
    val timeBegin: String,
    val timeEnd: String
)