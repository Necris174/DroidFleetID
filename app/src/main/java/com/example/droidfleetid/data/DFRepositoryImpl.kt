package com.example.droidfleetid.data

import android.util.Log
import com.example.droidfleetid.data.mapper.AuthorizationMapper
import com.example.droidfleetid.data.mapper.DeviceMapper
import com.example.droidfleetid.domain.DFRepository
import com.example.droidfleetid.domain.entity.AuthorizationProperties
import com.example.droidfleetid.domain.entity.DeviceEntity
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject

class DFRepositoryImpl: DFRepository {

    private val apiService = ApiFactory.service
    private val mapper = AuthorizationMapper()
    private val deviceMapper = DeviceMapper()

    override suspend fun authorization(login: String, password: String): AuthorizationProperties {
        return mapper.mapSignInClassToAuthorizationProperties(
            apiService.getAccessToken(
                body = SignInRequest(
                    password,
                    login
                )
            )
        )
    }

     override suspend fun refreshToken(refreshToken: String): AuthorizationProperties {
        TODO("Not yet implemented")
    }

    override suspend fun getSettings(accessToken: String): List<DeviceEntity> {
         return deviceMapper.mapSettingsDtoClassToDeviceEntity(apiService.getSettings(headers5 = "Bearer $accessToken"))
    }

    override suspend fun getTails(deviceList: List<DeviceRequestItem>, accessToken: String): List<TailsDto> {
        return apiService.getTails(authorization = "Bearer $accessToken", body = deviceList)
    }


}