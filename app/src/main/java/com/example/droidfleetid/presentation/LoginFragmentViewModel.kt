package com.example.droidfleetid.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.droidfleetid.data.DFRepositoryImpl
import com.example.droidfleetid.domain.AuthorizationUseCase
import com.example.droidfleetid.domain.entity.AuthorizationProperties

class LoginFragmentViewModel : ViewModel() {

    private val dfRepository = DFRepositoryImpl()
    private val authorizationUseCase = AuthorizationUseCase(dfRepository)
    private var token: AuthorizationProperties? = null

    private val _errorInputLogin = MutableLiveData<Boolean>()
    val errorInputLogin: LiveData<Boolean>
    get() = _errorInputLogin

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword





    suspend fun authorization(login: String?, password: String?) {
        val validLogin = parseLogin(login)
        val validPassword = parsePassword(password)
        val fielValid = validateInput(validLogin, validPassword)
        if (fielValid) {
            token = authorizationUseCase(validLogin, validPassword)
        }

    }

    private fun parseLogin(login: String?): String {
        return login?.trim() ?: ""
    }

    private fun parsePassword(password: String?): String {
        return password?.trim() ?: ""
    }

    private fun validateInput(login: String, password: String): Boolean {
        var result = true
        if (login.isBlank()) {
            _errorInputLogin.value = true
            result = false
        }
        if (password.isBlank()) {
            _errorInputPassword.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputLogin() {
        _errorInputLogin.value = false
    }
    fun resetErrorInputPassword(){
        _errorInputPassword.value = false
    }


}