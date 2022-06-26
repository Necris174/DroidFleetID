package com.example.droidfleetid.domain

import androidx.lifecycle.LiveData
import com.example.droidfleetid.domain.entity.DeviceEntity
import javax.inject.Inject

class GetDeviceEntityListUseCase @Inject constructor(
   private val dfRepository: DFRepository
) {
    operator fun invoke(): LiveData<List<DeviceEntity>> {
        return dfRepository.getDeviceEntityList()
    }
}