package com.example.domain.entity

data class AddressLayer(
    val points: List<AddressPoint>,
    val zoom: Int,
    val acceptLanguage: String
)