package com.example.droidfleetid.data

import com.google.gson.JsonObject
import io.reactivex.Single
import org.json.JSONObject
import retrofit2.http.*

interface ApiService {


    @POST("api/")
    fun getAccessToken(@Header("Accept") headers1: String,
                       @Header("Content-Type") headers2: String,
                       @Header("X-Client-Type") headers3: String,
                       @Body body:SignInRequest): Single<JsonObject>

}