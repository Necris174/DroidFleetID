package com.example.domain

import com.example.domain.entity.DeviceEntity
import javax.inject.Inject

class StoreDeviceEntitiesUseCase @Inject constructor(
    private val dfRepository: DFRepository) {

    suspend operator  fun invoke(deviceList: List<DeviceEntity>) {
            return dfRepository.storeDeviceEntities(deviceList)
        }
}