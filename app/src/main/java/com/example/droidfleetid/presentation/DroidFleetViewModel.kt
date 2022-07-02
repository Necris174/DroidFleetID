package com.example.droidfleetid.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetDeviceEntityListUseCase
import com.example.domain.GetSettingsUseCase
import com.example.domain.GetTailsUseCase
import com.example.domain.StoreDeviceEntitiesUseCase
import com.example.domain.entity.AuthorizationProperties
import com.example.domain.entity.DeviceEntity
import com.example.domain.entity.DeviceRequest
import com.example.domain.entity.Tails
import kotlinx.coroutines.*
import javax.inject.Inject

class DroidFleetViewModel @Inject constructor(
    getDeviceEntityListUseCase: GetDeviceEntityListUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val getTailsUseCase: GetTailsUseCase,
    private val storeDeviceEntitiesUseCase: StoreDeviceEntitiesUseCase
   ) : ViewModel() {

    private var device: DeviceEntity? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        e.message?.let { Log.d("Exception Login:", it) }
    }

     private val _selectedDevice = MutableLiveData<SelectedDevice<DeviceEntity>>()
    val selectedDevice: LiveData<SelectedDevice<DeviceEntity>>
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
                        val deviceRequest = mutableListOf<DeviceRequest>()
                        deviceEntityList.map {
                            deviceRequest.add(DeviceRequest(it.account_id, it.imei))
                        }
                        //Getting tails
                        val tailsDtoList =
                            getTails(deviceRequest, authorizationProperties.accessToken)

                        for (i in tailsDtoList) {
                            for (y in deviceEntityList) {
                                if (i.imei == y.imei) {
                                    y.data = i.datumDto!!
                                    y.sensors = i.sensors
                                    y.status = i.status
                                    y.descr = i.descr
                                }
                            }
                        }
                        val deviceTracking = deviceEntityList.find { it.imei == device?.imei }
                        if (deviceTracking != null) {
                            selectDevice(deviceTracking)
                        }
                        // Storing the device list
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
        deviceEntity: List<DeviceRequest>,
        accessToken: String
    ): List<Tails> {
        return getTailsUseCase(deviceEntity, accessToken)
    }

    fun selectDevice(device: DeviceEntity) {
        _selectedDevice.postValue(SelectedDevice.Device(device))
    }

    fun resetSelectedDevice() {
        _selectedDevice.value = SelectedDevice.Reset("reset")
    }

    fun deviceTracking(clickCount: DeviceEntity?) {
        device = clickCount
    }


}