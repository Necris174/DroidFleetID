package com.example.droidfleetid.data

import com.google.gson.annotations.SerializedName


data class SignInRequest (
    @SerializedName("password") val password : String,
    @SerializedName("login") val login: String)
