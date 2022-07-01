package com.example.domain.entity

data class AccountSettings(
    val device_groups: List<Any>,
    val devices: List<Device>,
    val features: List<Any>,
    val role: String,
    val user: User
)



