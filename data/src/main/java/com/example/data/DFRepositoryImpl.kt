package com.example.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.data.database.DroidFleetDao
import com.example.data.mapper.AuthorizationMapper
import com.example.data.mapper.DeviceMapper
import com.example.data.model.DeviceRequestItem
import com.example.data.model.SignInRequest
import com.example.data.model.TailsDto
import com.example.data.network.ApiService
import com.example.domain.DFRepository
import com.example.domain.entity.AuthorizationProperties
import com.example.domain.entity.DeviceEntity
import com.example.domain.entity.DeviceRequest
import com.example.domain.entity.Tails
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
        Log.d("Dbmodel", it.toString())
        deviceMapper.deviceEntitiesDbModelListToDeviceEntityList(it)
    }

    override suspend fun deleteEntityList() {
        Log.d("DELETE", "DELETE ALL")
        droidFleetDao.deleteEntityList()
    }


}