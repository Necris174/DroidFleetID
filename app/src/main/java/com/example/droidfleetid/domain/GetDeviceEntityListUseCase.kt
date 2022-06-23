package com.example.droidfleetid.domain

import androidx.lifecycle.LiveData
import com.example.droidfleetid.domain.entity.DeviceEntity

class GetDeviceEntityListUseCase(
   private val dfRepository: DFRepository
) {
    operator fun invoke(): LiveData<List<DeviceEntity>> {
        return dfRepository.getDeviceEntityList()
    }
}