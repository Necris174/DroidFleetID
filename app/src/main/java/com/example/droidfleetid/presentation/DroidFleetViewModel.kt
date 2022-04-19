package com.example.droidfleetid.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidfleetid.data.DFRepositoryImpl
import com.example.droidfleetid.data.DeviceRequestItem
import com.example.droidfleetid.data.TailsDto
import com.example.droidfleetid.data.mapper.DeviceMapper
import com.example.droidfleetid.domain.GetSettingsUseCase
import com.example.droidfleetid.domain.GetTailsUseCase
import com.example.droidfleetid.domain.entity.AuthorizationProperties
import com.example.droidfleetid.domain.entity.DeviceEntity
import kotlinx.coroutines.*

class DroidFleetViewModel() : ViewModel() {
    private val dfRepository = DFRepositoryImpl()
    private val getSettingsUseCase = GetSettingsUseCase(dfRepository)
    private val getTailsUseCase = GetTailsUseCase(dfRepository)
    private val deviceMapper = DeviceMapper()

    private var authorizationJob: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        e.message?.let { Log.d("Exception Login:", it) }
    }


    private val _deviceEntityListLD = MutableLiveData<List<DeviceEntity>>()
    val deviceEntityListLD: LiveData<List<DeviceEntity>>
        get() = _deviceEntityListLD

    fun loadAllSettings(authorizationProperties: AuthorizationProperties) {
        authorizationJob = viewModelScope.launch(exceptionHandler) {
            coroutineScope {
                while (true) {
                    try {
                        val deviceEntityList = loadSettings(authorizationProperties.accessToken)
                        val deviceRequestItem = mutableListOf<DeviceRequestItem>()
                        deviceEntityList.map {
                            deviceRequestItem.add(DeviceRequestItem(it.account_id, it.imei))
                        }
                        val tailsDtoList =
                            getTails(deviceRequestItem, authorizationProperties.accessToken)

                        for (i in tailsDtoList) {
                            for (y in deviceEntityList) {
                                if (i.imei == y.imei) {
                                    y.sensors = i.sensors
                                    y.status = i.status
                                    y.descr = i.descr
                                    y.data = i.data
                                }
                            }
                        }
                        _deviceEntityListLD.value = deviceEntityList
                        Log.d("teilsDTO", "$deviceEntityList")

                    } catch (e: Exception) {
                        Log.d("teilsDTO", "Ошибка: $e")
                    }
                    delay(10000)
                }
            }
        }
    }



    private suspend fun loadSettings(accessToken: String): List<DeviceEntity> {
        return getSettingsUseCase(accessToken)
    }

    private suspend fun getTails(
        deviceEntity: List<DeviceRequestItem>,
        accessToken: String
    ): List<TailsDto> {
        return getTailsUseCase(deviceEntity, accessToken)
    }


}