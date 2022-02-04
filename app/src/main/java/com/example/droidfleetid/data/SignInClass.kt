package com.example.droidfleetid.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SignInClass (
    @SerializedName("accessToken")
    @Expose
    val accessToken: String? = null,

    @SerializedName("expires")
    @Expose
    val expires: Int? = null,

    @SerializedName("refreshToken")
    @Expose
    val refreshToken: String? = null,
)