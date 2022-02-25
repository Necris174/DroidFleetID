package com.example.droidfleetid.data.mapper

import com.example.droidfleetid.data.SignInClass
import com.example.droidfleetid.domain.entity.AuthorizationProperties

class AuthorizationMapper {

    fun mapSignInClasstoAuthorizationProperties(sic: SignInClass): AuthorizationProperties{
        return AuthorizationProperties(
            accessToken = sic.accessToken,
            expires = sic.expires,
            refreshToken = sic.refreshToken
        )
    }
}