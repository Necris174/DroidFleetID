package com.example.droidfleetid.domain

interface DFRepository {

    fun authorization(login:String, password:String): AuthorizationProperties
}