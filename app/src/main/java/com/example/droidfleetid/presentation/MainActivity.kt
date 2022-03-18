package com.example.droidfleetid.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.droidfleetid.R
import com.example.droidfleetid.data.DFRepositoryImpl
import com.example.droidfleetid.domain.AuthorizationUseCase
import com.example.droidfleetid.domain.GetSettingsUseCase


class MainActivity : AppCompatActivity() {

    private val dfRepository = DFRepositoryImpl()
    private val authorizationUseCase = AuthorizationUseCase(dfRepository)
    private val getSettingsUseCase = GetSettingsUseCase(dfRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



            }
        }


