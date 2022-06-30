package com.example.droidfleetid.presentation

sealed class SelectedDevice<T>{
    class Device<T>(val data: T) : SelectedDevice<T>()
    class Reset<T>(val message: String) : SelectedDevice<T>()
}

