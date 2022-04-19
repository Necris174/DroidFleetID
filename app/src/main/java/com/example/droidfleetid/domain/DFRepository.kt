package com.example.droidfleetid.domain

import com.example.droidfleetid.data.DeviceRequestItem
import com.example.droidfleetid.data.TailsDto
import com.example.droidfleetid.domain.entity.AuthorizationProperties
import com.example.droidfleetid.domain.entity.DeviceEntity
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject

interface DFRepository {

    suspend fun authorization(login:String, password:String): AuthorizationProperties

    suspend fun refreshToken (refreshToken: String): AuthorizationProperties

    suspend fun getSettings (accessToken: String): List<DeviceEntity>

    suspend fun  getTails (deviceList: List<DeviceRequestItem>, accessToken: String): List<TailsDto>

}