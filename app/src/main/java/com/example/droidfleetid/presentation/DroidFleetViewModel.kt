package com.example.droidfleetid.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.*
import com.example.domain.entity.*
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

class DroidFleetViewModel @Inject constructor(
    getDeviceEntityListUseCase: GetDeviceEntityListUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val getTailsUseCase: GetTailsUseCase,
    private val storeDeviceEntitiesUseCase: StoreDeviceEntitiesUseCase,
    private val deleteEntityListUseCase: DeleteEntityListUseCase,
    private val getAddressLayerUseCase: AddressLayerUseCase,
    private val getTrackUseCase: GetTrackUseCase
) : ViewModel() {

    private var device: DeviceEntity? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        e.message?.let { Log.d("Exception: ", it) }
    }
    var isUserLoggedIn = true
    private val _selectedDevice = MutableLiveData<SelectedDevice<DeviceEntity>>()
    val selectedDevice: LiveData<SelectedDevice<DeviceEntity>>
        get() = _selectedDevice

    private val _calendarBegin = MutableLiveData<Calendar>()
    val calendarBegin: LiveData<Calendar>
        get() = _calendarBegin

    private val _calendarEnd = MutableLiveData<Calendar>()
    val calendarEnd: LiveData<Calendar>
        get() = _calendarEnd

    private val _selectedReportDevice = MutableLiveData<DeviceEntity>()
    val selectedReportDevice: LiveData<DeviceEntity>
        get() = _selectedReportDevice

    // Getting deviceEntity from the Database
    val deviceEntityListLD = getDeviceEntityListUseCase()
    lateinit var authorizationProperties: AuthorizationProperties


    fun loadAllSettings(authorizationProperties: AuthorizationProperties) {
        this.authorizationProperties = authorizationProperties
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

     suspend fun getTrack(imei: String, accountId: String, dateFrom: String, dateTo: String, shiftVars: String) :JsonObject {
         Log.d("tailsDTO", "Ошибка: ${authorizationProperties.accessToken}")
        return getTrackUseCase(authorizationProperties.accessToken, imei, accountId, dateFrom, dateTo, shiftVars)

    }

    fun selectedReportDevice (device: DeviceEntity) {
        _selectedReportDevice.value = device
    }

    fun saveCalendar(calendarBegin: Calendar, calendarEnd: Calendar) {
        _calendarEnd.value = calendarEnd
        _calendarBegin.value = calendarBegin
    }

}