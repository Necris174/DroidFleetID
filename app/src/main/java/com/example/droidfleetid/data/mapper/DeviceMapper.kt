package com.example.droidfleetid.data.mapper

import com.example.droidfleetid.data.SettingsDto
import com.example.droidfleetid.domain.entity.Device
import com.example.droidfleetid.domain.entity.DeviceEntity
import java.text.SimpleDateFormat
import java.util.*

class DeviceMapper {


    fun  mapSettingsDtoClassToDeviceEntity(setDto: SettingsDto): List<DeviceEntity> {
        return setDto.devices?.map { getDeviceEntity(it) } ?: listOf()
    }


    private fun getDeviceEntity(device: Device): DeviceEntity {
        return DeviceEntity(
            account_id = device.account_id,
            id = device.account_id,
            imei = device.imei,
            model = device.model,
            number = device.number,
            is_disabled = parseBlackDate(device)
        )
    }

    private fun parseBlackDate(device: Device): Boolean {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        val blackDate = format.parse(device.black_date)
        val licenseBlackDate = format.parse(device.license_black_date)
        val currentDate = Date()
        if (device.is_licensed&&device.is_unlimited){

            return blackDate>currentDate
        } else {
            return licenseBlackDate>currentDate
        }
    }


}