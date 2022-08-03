package com.example.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.data.database.DroidFleetDao
import com.example.data.mapper.AuthorizationMapper
import com.example.data.mapper.DeviceMapper
import com.example.data.model.*
import com.example.data.network.ApiService
import com.example.domain.DFRepository
import com.example.domain.entity.*
import com.google.gson.JsonObject
import javax.inject.Inject

class DFRepositoryImpl @Inject constructor(
    private val mapper: AuthorizationMapper,
    private val deviceMapper: DeviceMapper,
    private val droidFleetDao: DroidFleetDao,
    private val apiService: ApiService): DFRepository {

    override suspend fun authorization(login: String, password: String): AuthorizationProperties {
        return mapper.mapSignInClassToAuthorizationProperties(apiService.getAccessToken( body = SignInRequest(password, login)))
    }


     override suspend fun refreshToken(refreshToken: String): AuthorizationProperties {
        TODO("Not yet implemented")
    }

    override suspend fun getSettings(accessToken: String): List<DeviceEntity> {
         return deviceMapper.mapSettingsDtoClassToDeviceEntity(apiService.getSettings(headers5 = "Bearer $accessToken"))
    }

    override suspend fun getTails(deviceList: List<DeviceRequest>, accessToken: String): List<Tails> {
        return deviceMapper.mapTailsDtoToTails(apiService.getTails(authorization = "Bearer $accessToken", body = deviceList))
    }

    override suspend fun storeDeviceEntities(deviceList: List<DeviceEntity>) {
            return droidFleetDao.insertDeviceEntityList(deviceMapper.deviceEntityListToDeviceEntitiesDbModelList(deviceList))
    }

    override fun getDeviceEntityList(): LiveData<List<DeviceEntity>> = Transformations.map(
        droidFleetDao.getDeviceEntityList()
    ){
        deviceMapper.deviceEntitiesDbModelListToDeviceEntityList(it)
    }

    override suspend fun deleteEntityList() {
        Log.d("DELETE", "DELETE ALL")
        droidFleetDao.deleteEntityList()
    }

    override suspend fun getAddressLayer(accessToken: String, addressLayer: AddressLayer): List<Address> {
        return deviceMapper.mapAddressLayerDtoToAddress(
            apiService.getAddress(
                authorization = "Bearer $accessToken",
                body = deviceMapper.mapAddressLayerToAddressRequestDto(addressLayer)))
    }
    override suspend fun getTrack (authorization: String, device_IMEI: String, account_id: String, date_from: String, date_to: String, shift_vars: String): JsonObject {

        val jsonObject = apiService.getTrack(
            authorization = authorization,
            device_IMEI = device_IMEI,
            account_id = account_id,
            date_from = date_from,
            date_to = date_to,
            shift_vars = shift_vars)
        Log.d("GETTRACK", jsonObject.toString())
            return jsonObject

    }

}