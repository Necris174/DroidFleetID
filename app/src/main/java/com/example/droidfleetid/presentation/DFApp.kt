package com.example.droidfleetid.presentation

import android.app.Application
import com.example.droidfleetid.di.DaggerApplicationComponent

class DFApp: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}