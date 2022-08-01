package com.example.droidfleetid.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.*
import com.example.domain.entity.*
import kotlinx.coroutines.*
import javax.inject.Inject

class DroidFleetViewModel @Inject constructor(
    getDeviceEntityListUseCase: GetDeviceEntityListUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val getTailsUseCase: GetTailsUseCase,
    private val storeDeviceEntitiesUseCase: StoreDeviceEntitiesUseCase,
    private val deleteEntityListUseCase: DeleteEntityListUseCase,
    private val getAddressLayerUseCase: AddressLayerUseCase
) : ViewModel() {

    private var device: DeviceEntity? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        e.message?.let { Log.d("Exception: ", it) }
    }
    var isUserLoggedIn = true
    private val _selectedDevice = MutableLiveData<SelectedDevice<DeviceEntity>>()
    val selectedDevice: LiveData<SelectedDevice<DeviceEntity>>
        get() = _selectedDevice

    // Getting deviceEntity from the Database
    val deviceEntityListLD = getDeviceEntityListUseCase()

    fun loadAllSettings(authorizationProperties: AuthorizationProperties) {
        Log.d("VIEWMODEL", "MY VIEWMODEL RELOAD" )
        viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
            while (isUserLoggedIn) {
                try {
                    //Loading basic device information
                    val deviceEntityList = loadSettings(authorizationProperties.accessToken)
                    //Getting account_id and imei
                    val deviceRequest = mutableListOf<DeviceRequest>()
                    deviceEntityList.map {
                        deviceRequest.add(DeviceRequest(it.account_id, it.imei))
                    }
                    //Getting tails
                    val tailsList =
                        getTails(deviceRequest, authorizationProperties.accessToken)

                    deviceEntityList.map { device ->
                        tailsList.map {
                            if (device.imei == it.imei) {
                                device.data = it.datumDto
                                device.sensors = it.sensors
                                device.status = it.status
                                device.descr = it.descr
                            }
                        }
                    }
//
//                        for (i in tailsList) {
//                            for (y in deviceEntityList) {
//                                if (i.imei == y.imei) {
//                                    y.data = i.datumDto
//                                    y.sensors = i.sensors
//                                    y.status = i.status
//                                    y.descr = i.descr
//                                }
//                            }
//                        }

                    if (device != null) {
                        val deviceTracking = deviceEntityList.find { it.imei == device?.imei }
                        if (deviceTracking != null) {
                            selectedDevice(deviceTracking)
                        }
                    }
                    //Getting address
                    getAddress(deviceEntityList, authorizationProperties.accessToken)

                    // Storing the device list
                    storeDeviceEntitiesUseCase(deviceEntityList)

                    if (!isUserLoggedIn) {
                        deleteEntityList()
                    }

                } catch (e: Exception) {
                    Log.d("tailsDTO", "Ошибка: $e")
                }
                delay(20000)
            }
        }
    }

    private suspend fun getAddress(deviceEntityList: List<DeviceEntity>, accessToken: String) {
        deviceEntityList.map {
            if (it.data.isNotEmpty()) {
                try {
                    val addressList = getAddressLayerUseCase(
                        accessToken, AddressLayer(
                            listOf(
                                AddressPoint(
                                    it.data.first().coords.lat,
                                    it.data.first().coords.lon
                                )
                            ), 10, "ru"
                        )
                    )
                    // Log.d("ADDRESS", "$addressList")
                    with(addressList.first()) {
                        it.address = "$country $city $cityDistrict \n$road $houseNumber $suburb"
                    }
                } catch (e: Exception) {
                    Log.d("tailsDTO", "Ошибка: $e")
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

    fun selectedDevice(device: DeviceEntity) {
        _selectedDevice.postValue(SelectedDevice.Device(device))
    }

    fun resetSelectedDevice() {
        _selectedDevice.value = SelectedDevice.Reset("reset")
    }

    fun deviceTracking(clickCount: DeviceEntity?) {
        device = clickCount
    }

    fun deleteEntityList() {
        viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
            deleteEntityListUseCase()
        }

    }

}