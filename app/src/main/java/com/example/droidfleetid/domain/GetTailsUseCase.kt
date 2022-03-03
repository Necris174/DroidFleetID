package com.example.droidfleetid.domain

import com.example.droidfleetid.data.SettingsDto
import com.example.droidfleetid.data.TeilsDto
import com.example.droidfleetid.domain.entity.Device

class GetTailsUseCase(
    private val dfRepository: DFRepository) {

    suspend operator fun invoke(deviceList: List<Device>): TeilsDto {
        return dfRepository.getTails(deviceList)
    }

}