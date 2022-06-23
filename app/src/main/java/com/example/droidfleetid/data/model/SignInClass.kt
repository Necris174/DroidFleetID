package com.example.droidfleetid.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SignInClass (
    @SerializedName("access_token")
    @Expose
    val accessToken: String,

    @SerializedName("expires_in")
    @Expose
    val expires: Int,

    @SerializedName("refresh_token")
    @Expose
    val refreshToken: String
)