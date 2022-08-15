package com.example.data.mapper

import android.util.Log
import com.example.data.model.*
import com.example.domain.entity.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DeviceMapper @Inject constructor() {


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

        if (blackDate != null&&licenseBlackDate != null) {
                return if (device.is_licensed && device.is_unlimited) {
                    blackDate > currentDate
                } else {
                    licenseBlackDate > currentDate
                }
        }
        return false
    }


   private fun deviceEntityToDeviceEntitiesDbModel(deviceEntity: DeviceEntity): DeviceEntityDbModel {
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
            sensors = Gson().toJson(deviceEntity.sensors),
            address = deviceEntity.address
        )

    }

    fun deviceEntityListToDeviceEntitiesDbModelList (deviceEntityList: List<DeviceEntity>) = deviceEntityList.map {
        deviceEntityToDeviceEntitiesDbModel(it)
    }

    private fun deviceEntitiesDbModelToDeviceEntity(deviceEntityDbModel: DeviceEntityDbModel): DeviceEntity {
        val datum  = Gson().fromJson(deviceEntityDbModel.data ,object: TypeToken<List<Datum>>(){}.type ) ?: emptyList<Datum>()
        val sensors = Gson().fromJson(deviceEntityDbModel.sensors ,object: TypeToken<List<Sensor>>(){}.type ) ?: emptyList<Sensor>()
        return DeviceEntity(
            account_id = deviceEntityDbModel.account_id,
            id = deviceEntityDbModel.id,
            imei = deviceEntityDbModel.imei,
            is_disabled = deviceEntityDbModel.is_disabled,
            model = deviceEntityDbModel.model,
            number = deviceEntityDbModel.number,
            status = deviceEntityDbModel.status,
            descr = deviceEntityDbModel.descr,
            data = datum,
            sensors = sensors,
            address = deviceEntityDbModel.address
        )
    }

     fun deviceEntitiesDbModelListToDeviceEntityList(deviceEntityDbModelList: List<DeviceEntityDbModel>) = deviceEntityDbModelList.map {
        deviceEntitiesDbModelToDeviceEntity(it)
    }

     fun mapTailsDtoToTails(tails: List<TailsDto>): List<Tails> {
        return tails.map { getTails(it) }

    }

    private fun getTails(tailDto: TailsDto) : Tails{
        return Tails(
            accountId = tailDto.accountId,
            imei = tailDto.imei,
            status = tailDto.status,
            descr = tailDto.descr,
            datumDto = mapDatumDtoToDatum(tailDto.datumDto),
            sensors = tailDto.sensors)

    }

    private fun mapDatumDtoToDatum(datumDto: List<DatumDto>?): List<Datum> {
        return datumDto?.map { getDatum(it) } ?: listOf()
    }

    private fun getDatum(datumDto: DatumDto): Datum {
        val time = (System.currentTimeMillis()/1000).toInt()+20- datumDto.time!!
                return Datum(
                    coords = datumDto.coords,
                    flags = datumDto.flags,
                    time = time)
    }

    fun mapAddressLayerDtoToAddress(address: List<AddressLayerDto>): List<Address> {
        Log.d("ADDRESS", address.first().address.toString())
                    return   address.map {getAddress(it) }
    }

    private fun getAddress(address: AddressLayerDto) : Address {
        val city = address.address?.city ?: ""
        val country = address.address?.country ?: ""
        val cityDistrict = address.address?.cityDistrict ?: ""
        val houseNumber = address.address?.houseNumber ?: ""
        val road = address.address?.road ?: ""
        val suburb = address.address?.suburb ?: ""
        return Address(city,country,cityDistrict,houseNumber,road, suburb)
    }

    fun mapAddressLayerToAddressRequestDto(addressLayer: AddressLayer): AddressRequestDto {
                return AddressRequestDto(points = addressLayer.points,
                zoom = addressLayer.zoom,
                acceptLanguage = addressLayer.acceptLanguage)

    }


}