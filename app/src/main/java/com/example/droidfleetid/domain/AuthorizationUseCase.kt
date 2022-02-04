package com.example.droidfleetid.domain

class AuthorizationUseCase(private val dfRepository: DFRepository) {

    fun authorization(login:String, password:String): AuthorizationProperties{
         return dfRepository.authorization(login,password)
    }
}