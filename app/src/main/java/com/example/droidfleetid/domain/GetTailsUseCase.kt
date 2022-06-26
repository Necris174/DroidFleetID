package com.example.droidfleetid.domain

import com.example.droidfleetid.data.model.DeviceRequestItem
import com.example.droidfleetid.data.model.TailsDto
import javax.inject.Inject

class GetTailsUseCase @Inject constructor(
    private val dfRepository: DFRepository) {

    suspend operator fun invoke(deviceList: List<DeviceRequestItem>, accessToken: String): List<TailsDto> {
        return dfRepository.getTails(deviceList, accessToken)
    }

}