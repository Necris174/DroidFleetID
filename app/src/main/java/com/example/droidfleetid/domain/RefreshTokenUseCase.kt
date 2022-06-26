package com.example.droidfleetid.domain

import com.example.droidfleetid.domain.entity.AuthorizationProperties
import javax.inject.Inject


class RefreshTokenUseCase @Inject constructor(
    private val dfRepository: DFRepository
) {
    suspend operator fun invoke(refreshToken: String): AuthorizationProperties {
        return dfRepository.refreshToken(refreshToken)
    }
}