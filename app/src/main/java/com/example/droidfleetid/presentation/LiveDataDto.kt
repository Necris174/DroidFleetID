package com.example.droidfleetid.presentation

sealed class LiveDataDto<T>{
    class Device<T>(val data: T) : LiveDataDto<T>()
    class Reset<T>(val message: String) : LiveDataDto<T>()
}

