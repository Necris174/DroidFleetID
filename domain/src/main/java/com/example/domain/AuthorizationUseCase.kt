package com.example.domain

import com.example.domain.entity.AuthorizationProperties
import javax.inject.Inject

class AuthorizationUseCase @Inject constructor(
    private val dfRepository: DFRepository
    ) {

   suspend operator fun invoke(login:String, password:String): AuthorizationProperties {
         return dfRepository.authorization(login,password)
    }
}