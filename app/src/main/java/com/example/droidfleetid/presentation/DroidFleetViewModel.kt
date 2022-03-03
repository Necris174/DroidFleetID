package com.example.droidfleetid.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.droidfleetid.data.DFRepositoryImpl
import com.example.droidfleetid.domain.AuthorizationUseCase
import com.example.droidfleetid.domain.GetSettingsUseCase
import com.example.droidfleetid.domain.GetTailsUseCase
import com.example.droidfleetid.domain.entity.AuthorizationProperties

class DroidFleetViewModel(application: Application): AndroidViewModel(application) {

    private lateinit var authorizationProperties: AuthorizationProperties
    private val dfRepository = DFRepositoryImpl()
    private val authorizationUseCase = AuthorizationUseCase(dfRepository)
    private val getSettingsUseCase = GetSettingsUseCase(dfRepository)
    private val getTailsUseCase = GetTailsUseCase(dfRepository)


    suspend fun loadSettings(login: String, password: String){
        authorizationProperties = authorizationUseCase(login,password)
    }




}