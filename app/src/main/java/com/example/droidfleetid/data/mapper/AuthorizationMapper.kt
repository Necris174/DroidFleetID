package com.example.droidfleetid.data.mapper

import com.example.droidfleetid.data.model.SignInClass
import com.example.droidfleetid.domain.entity.AuthorizationProperties

class AuthorizationMapper {

    fun mapSignInClassToAuthorizationProperties(sic: SignInClass): AuthorizationProperties {
        return AuthorizationProperties(sic.accessToken,sic.expires,sic.refreshToken)
    }
}