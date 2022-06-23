package com.example.droidfleetid.domain

import com.example.droidfleetid.domain.entity.DeviceEntity

class StoreDeviceEntitiesUseCase(
    private val dfRepository: DFRepository) {

    suspend operator  fun invoke(deviceList: List<DeviceEntity>) {
            return dfRepository.storeDeviceEntities(deviceList)
        }
}