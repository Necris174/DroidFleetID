package com.example.droidfleetid.data

import com.example.droidfleetid.domain.entity.DeviceEntity
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.http.*

interface ApiService {


    @POST("signin")
    suspend fun getAccessToken(@Header("Accept") accept: String = "application/json",
                       @Header("Content-Type") contentType: String = "application/json",
                       @Header("X-Client-Type") xClientType: String = "droidfleet",
                       @Header("Connection") connection: String = "close",
                       @Body body:SignInRequest): SignInClass

    @POST("refresh")
    suspend fun refreshToken (@Header("Accept") accept: String,
                      @Header("Content-Type") contentType: String,
                      @Header("X-Client-Type") xClientType: String,
                      @Header("Connection") connection: String,
                      @Body body:SignInRequest) : JsonObject

    @GET("settings")
    suspend fun getSettings (@Header("Accept") accept: String = "application/json",
                             @Header("Content-Type") contentType: String = "application/json",
                             @Header("X-Client-Type") xClientType: String = "droidfleet",
                             @Header("Connection") connection: String = "close",
                             @Header("Authorization") headers5: String): SettingsDto

    @POST("tail")
    suspend fun getTails (@Header("Accept") accept: String = "application/json",
                          @Header("Content-Type") contentType: String = "application/json",
                          @Header("X-Client-Type") xClientType: String = "droidfleet",
                          @Header("Connection") connection: String = "close",
                          @Header("Authorization") authorization: String,
                          @Body body:List<DeviceRequestItem>): List<TailsDto>

    @GET("track")
    suspend fun getTrack (@Header("Accept") accept: String = "application/json",
                  @Header("Content-Type") contentType: String = "application/json",
                  @Header("X-Client-Type") xClientType: String = "droidfleet",
                  @Header("Connection") connection: String = "close",
                  @Header("Authorization") authorization: String,
                  @Query(DEVICE_IMEI) device_IMEI: String,
                  @Query(ACCOUNT_ID) account_id: String,
                  @Query(DATE_FROM) date_from: String,
                  @Query(DATE_TO) date_to: String,
                  @Query(SHIFT_VARS) shift_vars: String,
                  ): JsonObject


    companion object {
        private const val DEVICE_IMEI = "imei"
        private const val ACCOUNT_ID  = "account_id"
        private const val DATE_FROM  = "from"
        private const val DATE_TO  = "to"
        private const val SHIFT_VARS  = "shift_vars"
    }
}


