package com.example.droidfleetid

import android.app.Application
import com.example.droidfleetid.di.DaggerApplicationComponent

class DFApp: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}