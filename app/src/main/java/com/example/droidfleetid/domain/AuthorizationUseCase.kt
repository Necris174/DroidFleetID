package com.example.droidfleetid.domain

import com.example.droidfleetid.data.SignInClass
import com.example.droidfleetid.domain.entity.AuthorizationProperties

class AuthorizationUseCase(
    private val dfRepository: DFRepository
    ) {

   suspend operator fun invoke(login:String, password:String): AuthorizationProperties {
         return dfRepository.authorization(login,password)
    }
}