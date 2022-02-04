package com.example.droidfleetid.data

import com.google.gson.annotations.SerializedName


data class SignInRequest (
    @SerializedName("jsonrpc") val jsonrpc : String,
    @SerializedName("method") val method: String,
    @SerializedName("params") val params: MutableMap<String,String>
        )
