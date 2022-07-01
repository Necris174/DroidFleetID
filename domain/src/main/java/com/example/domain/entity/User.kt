package com.example.domain.entity

data class User(
    val company_id: String,
    val email: String,
    val first_name: String,
    val id: String,
    val is_active: Boolean,
    val is_verified: Boolean,
    val last_name: String,
    val middle_name: String,
    val picture: String,
    val updated_at: String
)
