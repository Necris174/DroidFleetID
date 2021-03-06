package com.example.droidfleetid.presentation.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.AuthorizationUseCase
import com.example.domain.entity.AuthorizationProperties
import com.example.droidfleetid.presentation.Result
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class LoginFragmentViewModel @Inject constructor(
    private val authorizationUseCase: AuthorizationUseCase
) : ViewModel() {



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
        e.message?.let { Log.d("Exception Login:", it)
            _authorizationToken.postValue( Result.Error("${it}:  Не удалось получить ответ от сервера"))

        }
    }


    fun authorization(login: String?, password: String?) {
        viewModelScope.launch(exceptionHandler) {
                try {
                    val validLogin = parseLogin(login)
                    val validPassword = parsePassword(password)
                    val fieldValid = validateInput(validLogin, validPassword)
                    if (fieldValid) {
                        _authorizationToken.value =
                            Result.Success(authorizationUseCase(validLogin, validPassword))
                    }
                } catch (e: HttpException) {
                    if(e.code() == 401){
                        _authorizationToken.value =
                            Result.Error("Введен неверный логин или пароль")
                    } else{
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
        return result
    }

    fun resetErrorInputLogin() {
        _errorInputLogin.postValue(false)
    }

    fun resetErrorInputPassword() {
        _errorInputPassword.postValue(false)
    }


}