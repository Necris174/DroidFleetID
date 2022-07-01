package com.example.domain

import com.example.domain.entity.DeviceEntity
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val dfRepository: DFRepository) {

    suspend operator fun invoke(accessToken: String): List<DeviceEntity> {
        return dfRepository.getSettings(accessToken)
    }
}