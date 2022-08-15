package com.example.droidfleetid.di

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import com.example.data.DFRepositoryImpl
import com.example.data.database.AppDataBase
import com.example.data.database.DroidFleetDao
import com.example.data.network.ApiFactory
import com.example.data.network.ApiService
import com.example.domain.DFRepository
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindDFRepositoryImpl(impl: DFRepositoryImpl): DFRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideDroidFleetDao(application: Application): DroidFleetDao {
            return AppDataBase.getInstance(application).droidFleetDao()
        }


        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.service
        }

        @Provides
        @ApplicationScope
        fun provideSharedPreference(application: Application): SharedPreferences {
            val masterKey = MasterKey.Builder(application)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            return EncryptedSharedPreferences.create(application,"SharedPref",masterKey,EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
        }

    }
}