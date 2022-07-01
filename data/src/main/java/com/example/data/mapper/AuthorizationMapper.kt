package com.example.data.mapper

import com.example.data.model.SignInClass
import com.example.domain.entity.AuthorizationProperties
import javax.inject.Inject

class AuthorizationMapper @Inject constructor(){

    fun mapSignInClassToAuthorizationProperties(sic: SignInClass): AuthorizationProperties {
        return AuthorizationProperties(sic.accessToken,sic.expires,sic.refreshToken)
    }
}