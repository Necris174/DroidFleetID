package com.example.droidfleetid.domain

import com.example.droidfleetid.data.SettingsDto
import com.example.droidfleetid.domain.entity.AuthorizationProperties

class GetSettingsUseCase(
    private val dfRepository: DFRepository) {

    suspend operator fun invoke(accessToken: String): SettingsDto {
        return dfRepository.getSettings(accessToken)
    }
}