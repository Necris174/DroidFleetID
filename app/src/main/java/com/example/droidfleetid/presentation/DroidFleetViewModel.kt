package com.example.droidfleetid.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidfleetid.data.model.DeviceRequestItem
import com.example.droidfleetid.data.model.TailsDto
import com.example.droidfleetid.domain.GetDeviceEntityListUseCase
import com.example.droidfleetid.domain.GetSettingsUseCase
import com.example.droidfleetid.domain.GetTailsUseCase
import com.example.droidfleetid.domain.StoreDeviceEntitiesUseCase
import com.example.droidfleetid.domain.entity.AuthorizationProperties
import com.example.droidfleetid.domain.entity.DeviceEntity
import kotlinx.coroutines.*
import javax.inject.Inject

class DroidFleetViewModel @Inject constructor(
    private val getDeviceEntityListUseCase: GetDeviceEntityListUseCase,
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
                                    y.data = i.data!!
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
        deviceEntity: List<DeviceRequestItem>,
        accessToken: String
    ): List<TailsDto> {
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