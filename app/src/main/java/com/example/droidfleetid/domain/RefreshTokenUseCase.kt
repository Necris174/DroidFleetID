package com.example.droidfleetid.domain

import com.example.droidfleetid.domain.entity.AuthorizationProperties


class RefreshTokenUseCase(
    private val dfRepository: DFRepository
) {
    suspend operator fun invoke(refreshToken: String): AuthorizationProperties {
        return dfRepository.refreshToken(refreshToken)
    }
}