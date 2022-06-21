package com.example.droidfleetid.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.droidfleetid.R
import com.example.droidfleetid.data.DFRepositoryImpl
import com.example.droidfleetid.domain.AuthorizationUseCase
import com.example.droidfleetid.domain.GetSettingsUseCase
import com.example.droidfleetid.presentation.fragments.LoginFragment


class MainActivity : AppCompatActivity() {
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.droid_fleet_container, LoginFragment()).commit()
            }
            }
        }


