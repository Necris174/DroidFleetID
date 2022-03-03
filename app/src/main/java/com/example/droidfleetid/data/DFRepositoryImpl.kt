package com.example.droidfleetid.data

import com.example.droidfleetid.data.mapper.AuthorizationMapper
import com.example.droidfleetid.domain.DFRepository
import com.example.droidfleetid.domain.entity.AuthorizationProperties
import com.example.droidfleetid.domain.entity.Device

class DFRepositoryImpl: DFRepository {

    private val apiService = ApiFactory.service
    private val mapper = AuthorizationMapper()

    override suspend fun authorization(login: String, password: String): AuthorizationProperties {
        return mapper.mapSignInClasstoAuthorizationProperties(
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

    override suspend fun getSettings(accessToken: String): SettingsDto {
         return apiService.getSettings(headers5 = "Bearer $accessToken")
    }

    override suspend fun getTails(deviceList: List<Device>): TeilsDto {
        TODO("Not yet implemented")
    }


}