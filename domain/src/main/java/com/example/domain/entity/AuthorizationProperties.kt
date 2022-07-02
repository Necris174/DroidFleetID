package com.example.domain.entity

data class AuthorizationProperties(
    val accessToken: String,
    val expires: Int,
    val refreshToken: String
)
