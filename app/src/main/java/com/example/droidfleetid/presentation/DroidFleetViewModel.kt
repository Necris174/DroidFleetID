package com.example.droidfleetid.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidfleetid.data.DFRepositoryImpl
import com.example.droidfleetid.data.SettingsDto
import com.example.droidfleetid.domain.AuthorizationUseCase
import com.example.droidfleetid.domain.GetSettingsUseCase
import com.example.droidfleetid.domain.GetTailsUseCase
import com.example.droidfleetid.domain.entity.AuthorizationProperties
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DroidFleetViewModel(var authorizationProperties: AuthorizationProperties): ViewModel() {

    private var authorizationJob: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        e.message?.let { Log.d("Exception Login:", it) }
    }
    private var settingsDto: SettingsDto? = null


    init {
        authorizationJob = viewModelScope.launch(exceptionHandler) {
            coroutineScope {
                try {
                    settingsDto =
                        authorizationProperties.accessToken?.let { loadSettings(it) }

                } catch (e: Exception) {

                }
            }
        }
    }
    private val dfRepository = DFRepositoryImpl()
    private val authorizationUseCase = AuthorizationUseCase(dfRepository)
    private val getSettingsUseCase = GetSettingsUseCase(dfRepository)
    private val getTailsUseCase = GetTailsUseCase(dfRepository)


    suspend fun loadSettings(accessToken: String): SettingsDto{
       return  getSettingsUseCase(accessToken)
    }




}