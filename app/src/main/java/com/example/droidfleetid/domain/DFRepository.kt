package com.example.droidfleetid.domain

import androidx.lifecycle.LiveData
import com.example.droidfleetid.data.model.DeviceRequestItem
import com.example.droidfleetid.data.model.TailsDto
import com.example.droidfleetid.domain.entity.AuthorizationProperties
import com.example.droidfleetid.domain.entity.DeviceEntity

interface DFRepository {

    suspend fun authorization(login:String, password:String): AuthorizationProperties

    suspend fun refreshToken (refreshToken: String): AuthorizationProperties

    suspend fun getSettings (accessToken: String): List<DeviceEntity>

    suspend fun  getTails (deviceList: List<DeviceRequestItem>, accessToken: String): List<TailsDto>

    suspend fun storeDeviceEntities(deviceList: List<DeviceEntity>)

    fun getDeviceEntityList(): LiveData<List<DeviceEntity>>

}