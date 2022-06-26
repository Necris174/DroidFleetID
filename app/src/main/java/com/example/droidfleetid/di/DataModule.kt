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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
interface DataModule {

    @Binds
    fun bindDFRepositoryImpl(impl: DFRepositoryImpl):DFRepository

    companion object {

        @Provides
        fun provideDroidFleetDao(application: Application): DroidFleetDao {
            return AppDataBase.getInstance(application).droidFleetDao()
        }

        @Provides
        fun provideApiFactory (): Retrofit {
            return Retrofit.Builder()
                    .baseUrl("https://maps.locarus.ru/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        @Provides
        fun provideApiService(retrofit: Retrofit): ApiService{
            return retrofit.create(ApiService::class.java)
        }

    }
}