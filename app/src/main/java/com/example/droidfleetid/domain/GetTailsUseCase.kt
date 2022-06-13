package com.example.droidfleetid.domain

import com.example.droidfleetid.data.DeviceRequestItem
import com.example.droidfleetid.data.TailsDto

class GetTailsUseCase(
    private val dfRepository: DFRepository) {

    suspend operator fun invoke(deviceList: List<DeviceRequestItem>, accessToken: String): List<TailsDto> {
        return dfRepository.getTails(deviceList, accessToken)
    }

}