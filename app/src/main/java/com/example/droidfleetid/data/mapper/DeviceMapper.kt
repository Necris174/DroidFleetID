package com.example.droidfleetid.data.mapper

import com.example.droidfleetid.data.DeviceEntityDbModel
import com.example.droidfleetid.data.SettingsDto
import com.example.droidfleetid.domain.entity.Device
import com.example.droidfleetid.domain.entity.DeviceEntity
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class DeviceMapper {


    fun mapSettingsDtoClassToDeviceEntity(setDto: SettingsDto): List<DeviceEntity> {
        return setDto.devices?.map { getDeviceEntity(it) } ?: listOf()
    }


    private fun getDeviceEntity(device: Device): DeviceEntity {
        return DeviceEntity(
            account_id = device.account_id,
            id = device.account_id,
            imei = device.imei,
            model = device.model,
            number = device.number,
            is_disabled = parseBlackDate(device),
            data = listOf()
        )
    }

    private fun parseBlackDate(device: Device): Boolean {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        val blackDate = format.parse(device.black_date)
        val licenseBlackDate = format.parse(device.license_black_date)
        val currentDate = Date()
        return if (device.is_licensed && device.is_unlimited) {
            blackDate > currentDate
        } else {
            licenseBlackDate > currentDate
        }
    }

    fun deviceEntityToDeviceEntiteDto(deviceEntity: DeviceEntity): DeviceEntityDbModel {
        return DeviceEntityDbModel(
            account_id = deviceEntity.account_id,
            id = deviceEntity.id,
            imei = deviceEntity.imei,
            is_disabled = deviceEntity.is_disabled,
            model = deviceEntity.model,
            number = deviceEntity.number,
            status = deviceEntity.status,
            descr = deviceEntity.descr,
            data = Gson().toJson(deviceEntity.data),
            sensors = Gson().toJson(deviceEntity.sensors)
        )

    }


}