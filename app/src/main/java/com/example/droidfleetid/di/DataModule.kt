package com.example.droidfleetid.di

import android.app.Application
import com.example.droidfleetid.data.DFRepositoryImpl
import com.example.droidfleetid.data.database.AppDataBase
import com.example.droidfleetid.data.database.DroidFleetDao
import com.example.droidfleetid.data.network.ApiFactory
import com.example.droidfleetid.data.network.ApiService
import com.example.droidfleetid.domain.DFRepository
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindDFRepositoryImpl(impl: DFRepositoryImpl):DFRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideDroidFleetDao(application: Application): DroidFleetDao {
            return AppDataBase.getInstance(application).droidFleetDao()
        }


        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService{
            return ApiFactory.service
        }

    }
}