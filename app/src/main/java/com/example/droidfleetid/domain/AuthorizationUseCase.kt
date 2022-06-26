package com.example.droidfleetid.domain

import com.example.droidfleetid.domain.entity.AuthorizationProperties
import javax.inject.Inject

class AuthorizationUseCase @Inject constructor(
    private val dfRepository: DFRepository
    ) {

   suspend operator fun invoke(login:String, password:String): AuthorizationProperties {
         return dfRepository.authorization(login,password)
    }
}