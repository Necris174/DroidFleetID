package com.example.droidfleetid.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.droidfleetid.data.DFRepositoryImpl
import com.example.droidfleetid.data.DeviceRequestItem
import com.example.droidfleetid.data.TailsDto
import com.example.droidfleetid.domain.GetDeviceEntityListUseCase
import com.example.droidfleetid.domain.GetSettingsUseCase
import com.example.droidfleetid.domain.GetTailsUseCase
import com.example.droidfleetid.domain.StoreDeviceEntitiesUseCase
import com.example.droidfleetid.domain.entity.AuthorizationProperties
import com.example.droidfleetid.domain.entity.DeviceEntity
import kotlinx.coroutines.*

class DroidFleetViewModel(application: Application) : AndroidViewModel(application) {


    private val dfRepository = DFRepositoryImpl(application)
    private val getSettingsUseCase = GetSettingsUseCase(dfRepository)
    private val getTailsUseCase = GetTailsUseCase(dfRepository)
    private val storeDeviceEntitiesUseCase = StoreDeviceEntitiesUseCase(dfRepository)
    private val getDeviceEntityListUseCase = GetDeviceEntityListUseCase(dfRepository)


    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        e.message?.let { Log.d("Exception Login:", it) }
    }

     private val _selectedDevice = MutableLiveData<LiveDataDto<DeviceEntity>>()
    val selectedDevice: LiveData<LiveDataDto<DeviceEntity>>
        get() = _selectedDevice

    // Getting deviceEntity from the Database
    val deviceEntityListLD = getDeviceEntityListUseCase()


    fun loadAllSettings(authorizationProperties: AuthorizationProperties) {
        viewModelScope.launch(exceptionHandler+Dispatchers.IO) {
            coroutineScope {
                while (true) {
                    try {
                        //Loading basic device information
                        val deviceEntityList = loadSettings(authorizationProperties.accessToken)
                        //Getting account_id and imei
                        val deviceRequestItem = mutableListOf<DeviceRequestItem>()
                        deviceEntityList.map {
                            deviceRequestItem.add(DeviceRequestItem(it.account_id, it.imei))
                        }
                        //Getting tails
                        val tailsDtoList =
                            getTails(deviceRequestItem, authorizationProperties.accessToken)

                        for (i in tailsDtoList) {
                            for (y in deviceEntityList) {
                                if (i.imei == y.imei) {
                                    y.sensors = i.sensors
                                    y.status = i.status
                                    y.descr = i.descr
                                    y.data = i.data!!
                                }
                            }
                        }

                        storeDeviceEntitiesUseCase(deviceEntityList)

                    } catch (e: Exception) {
                        Log.d("tailsDTO", "Ошибка: $e")
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

    fun selectDevice(device: DeviceEntity) {
        _selectedDevice.value = LiveDataDto.Device(device)
    }

    fun resetSelectedDevice() {
        _selectedDevice.value = LiveDataDto.Reset("reset")
    }


}