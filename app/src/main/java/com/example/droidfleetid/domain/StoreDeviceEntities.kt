package com.example.droidfleetid.domain

import com.example.droidfleetid.domain.entity.DeviceEntity
import javax.inject.Inject

class StoreDeviceEntitiesUseCase @Inject constructor(
    private val dfRepository: DFRepository) {

    suspend operator  fun invoke(deviceList: List<DeviceEntity>) {
            return dfRepository.storeDeviceEntities(deviceList)
        }
}