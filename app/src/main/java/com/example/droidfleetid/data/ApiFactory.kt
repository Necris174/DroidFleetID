package com.example.droidfleetid.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {


    private val retrofit =
        Retrofit.Builder()
            .baseUrl("https://maps.locarus.ru/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service = retrofit.create(ApiService::class.java)


}