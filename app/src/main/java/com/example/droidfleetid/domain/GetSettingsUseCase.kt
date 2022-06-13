package com.example.droidfleetid.domain

import com.example.droidfleetid.domain.entity.DeviceEntity

class GetSettingsUseCase(
    private val dfRepository: DFRepository) {

    suspend operator fun invoke(accessToken: String): List<DeviceEntity> {
        return dfRepository.getSettings(accessToken)
    }
}