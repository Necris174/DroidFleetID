package com.example.droidfleetid.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.droidfleetid.data.network.ApiFactory
import com.example.droidfleetid.data.database.AppDataBase
import com.example.droidfleetid.data.mapper.AuthorizationMapper
import com.example.droidfleetid.data.mapper.DeviceMapper
import com.example.droidfleetid.data.model.DeviceRequestItem
import com.example.droidfleetid.data.model.SignInRequest
import com.example.droidfleetid.data.model.TailsDto
import com.example.droidfleetid.domain.DFRepository
import com.example.droidfleetid.domain.entity.AuthorizationProperties
import com.example.droidfleetid.domain.entity.DeviceEntity

class DFRepositoryImpl(
    application: Application
): DFRepository {

    private val apiService = ApiFactory.service
    private val mapper = AuthorizationMapper()
    private val deviceMapper = DeviceMapper()
    private val droidFleetDao = AppDataBase.getInstance(application).droidFleetDao()

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

    override suspend fun storeDeviceEntities(deviceList: List<DeviceEntity>) {
            return droidFleetDao.insertDeviceEntityList(deviceMapper.deviceEntityListToDeviceEntitiesDbModelList(deviceList))
    }

    override fun getDeviceEntityList(): LiveData<List<DeviceEntity>> = Transformations.map(
        droidFleetDao.getDeviceEntityList()
    ){
        Log.d("Dbmodel", it.toString())
        deviceMapper.deviceEntitiesDbModelListToDeviceEntityList(it)
    }




}