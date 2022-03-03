package com.example.droidfleetid.domain

import com.example.droidfleetid.data.SettingsDto
import com.example.droidfleetid.data.SignInClass
import com.example.droidfleetid.data.TeilsDto
import com.example.droidfleetid.domain.entity.AuthorizationProperties
import com.example.droidfleetid.domain.entity.Device

interface DFRepository {

    suspend fun authorization(login:String, password:String): AuthorizationProperties

    suspend fun refreshToken (refreshToken: String): AuthorizationProperties

    suspend fun getSettings (accessToken: String): SettingsDto

    suspend fun  getTails (deviceList: List<Device>): TeilsDto

}