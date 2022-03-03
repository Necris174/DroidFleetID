package com.example.droidfleetid.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.droidfleetid.R
import com.example.droidfleetid.data.DFRepositoryImpl
import com.example.droidfleetid.domain.AuthorizationUseCase
import com.example.droidfleetid.domain.GetSettingsUseCase
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val dfRepository = DFRepositoryImpl()
    private val authorizationUseCase = AuthorizationUseCase(dfRepository)
    private val getSettingsUseCase = GetSettingsUseCase(dfRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {

            try {
                val authorizationProperties = authorizationUseCase("genadich74","genadich")
                Log.d("authorizationProperties", authorizationProperties.toString())
                val user = authorizationProperties.accessToken?.let { getSettingsUseCase(it) }
                Log.d("authorizationProperties", user.toString())
            } catch (e: Exception) {
            }
        }

    }
}