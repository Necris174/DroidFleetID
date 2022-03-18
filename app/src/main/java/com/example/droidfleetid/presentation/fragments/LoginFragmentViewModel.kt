package com.example.droidfleetid.presentation.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidfleetid.data.DFRepositoryImpl
import com.example.droidfleetid.domain.AuthorizationUseCase
import com.example.droidfleetid.domain.entity.AuthorizationProperties
import com.example.droidfleetid.presentation.Result
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class LoginFragmentViewModel : ViewModel() {

    private val dfRepository = DFRepositoryImpl()
    private val authorizationUseCase = AuthorizationUseCase(dfRepository)

    private val _errorInputLogin = MutableLiveData<Boolean>()
    val errorInputLogin: LiveData<Boolean>
        get() = _errorInputLogin

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword

    private var _authorizationToken = MutableLiveData<Result<AuthorizationProperties>>()
    val authorizationToken: LiveData<Result<AuthorizationProperties>>
        get() = _authorizationToken

    private var authorizationJob: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        e.message?.let { Log.d("Exception Login:", it) }
    }


    fun authorization(login: String?, password: String?) {
        authorizationJob = viewModelScope.launch(exceptionHandler) {
            coroutineScope {
                try {
                    val validLogin = parseLogin(login)
                    val validPassword = parsePassword(password)
                    val fieldValid = validateInput(validLogin, validPassword)
                    if (fieldValid) {
                        Log.d("dsad", "Прошло")
                        _authorizationToken.value =
                            Result.Success(authorizationUseCase(validLogin, validPassword))
                    }
                } catch (e: Exception) {
                    _authorizationToken.value =
                        Result.Error("$e:  Не удалось получить ответ от сервера")
                }
            }
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
            _errorInputLogin.postValue(true)
            result = false
        }
        if (password.isBlank()) {
            _errorInputPassword.postValue(true)
            result = false
        }
        Log.d("dsad", "result = $result login = $login")
        return result
    }

    fun resetErrorInputLogin() {
        _errorInputLogin.postValue(false)
    }

    fun resetErrorInputPassword() {
        _errorInputPassword.postValue(false)
    }


}