package com.example.domain.entity

data class Shift(
    val properties: List<Property>,
    val shiftType: Int,
    val timeBegin: String,
    val timeEnd: String
)
