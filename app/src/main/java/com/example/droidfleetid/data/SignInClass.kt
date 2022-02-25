package com.example.droidfleetid.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SignInClass (
    @SerializedName("access_token")
    @Expose
    val accessToken: String? = null,

    @SerializedName("expires_in")
    @Expose
    val expires: Int? = null,

    @SerializedName("refresh_token")
    @Expose
    val refreshToken: String? = null
)