package com.example.domain.entity

data class Device(
    val account_id: String,
    val black_date: String,
    val id: String,
    val imei: String,
    val is_disabled: Boolean,
    val is_licensed: Boolean,
    val is_protected: Boolean,
    val is_unlimited: Boolean,
    val license_black_date: String,
    val model: String,
    val number: String,
    val password: String,
    val updated_at: String
)
