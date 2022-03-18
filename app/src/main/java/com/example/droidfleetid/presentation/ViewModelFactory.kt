package com.example.droidfleetid.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.droidfleetid.domain.entity.AuthorizationProperties
import java.lang.RuntimeException

class ViewModelFactory(private val authorizationProperties: AuthorizationProperties) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == DroidFleetViewModel::class.java) {
            return DroidFleetViewModel(authorizationProperties) as T
        }
        throw RuntimeException("Unknown view model $modelClass")
    }
}