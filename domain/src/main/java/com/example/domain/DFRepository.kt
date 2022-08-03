package com.example.domain

import androidx.lifecycle.LiveData
import com.example.domain.entity.*
import com.google.gson.JsonObject

interface DFRepository {

    suspend fun authorization(login:String, password:String): AuthorizationProperties

    suspend fun refreshToken (refreshToken: String): AuthorizationProperties

    suspend fun getSettings (accessToken: String): List<DeviceEntity>

    suspend fun  getTails (deviceList: List<DeviceRequest>, accessToken: String): List<Tails>

    suspend fun storeDeviceEntities(deviceList: List<DeviceEntity>)

    fun getDeviceEntityList(): LiveData<List<DeviceEntity>>

    suspend fun deleteEntityList()

    suspend fun getAddressLayer(accessToken: String ,addressLayer: AddressLayer): List<Address>

    suspend fun getTrack(authorization: String, device_IMEI: String, account_id: String, date_from: String, date_to: String, shift_vars: String): JsonObject

}