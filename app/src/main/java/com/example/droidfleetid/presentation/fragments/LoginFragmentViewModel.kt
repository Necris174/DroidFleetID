package com.example.droidfleetid.presentation.fragments

import android.app.Application
import android.security.keystore.UserNotAuthenticatedException
import android.util.Log
import androidx.lifecycle.*
import com.example.droidfleetid.data.DFRepositoryImpl
import com.example.droidfleetid.domain.AuthorizationUseCase
import com.example.droidfleetid.domain.entity.AuthorizationProperties
import com.example.droidfleetid.presentation.Result
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception

class LoginFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val dfRepository = DFRepositoryImpl(application)
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


    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        e.message?.let { Log.d("Exception Login:", it) }
    }


    fun authorization(login: String?, password: String?) {
        viewModelScope.launch(exceptionHandler) {
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
                } catch (e: HttpException) {
                    _authorizationToken.value =
                        Result.Error("${e.code()}:  Не удалось получить ответ от сервера")
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