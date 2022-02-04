package com.example.droidfleetid.domain

data class AuthorizationProperties(
    val accessToken: String,
    val expires: Long,
    val refreshToken: String
)
