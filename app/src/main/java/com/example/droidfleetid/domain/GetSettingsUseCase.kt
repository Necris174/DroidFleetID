package com.example.droidfleetid.domain

import com.example.droidfleetid.domain.entity.DeviceEntity
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val dfRepository: DFRepository) {

    suspend operator fun invoke(accessToken: String): List<DeviceEntity> {
        return dfRepository.getSettings(accessToken)
    }
}