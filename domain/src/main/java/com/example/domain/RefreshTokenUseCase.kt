package com.example.domain

import com.example.domain.entity.AuthorizationProperties
import javax.inject.Inject


class RefreshTokenUseCase @Inject constructor(
    private val dfRepository: DFRepository
) {
    suspend operator fun invoke(refreshToken: String): AuthorizationProperties {
        return dfRepository.refreshToken(refreshToken)
    }
}