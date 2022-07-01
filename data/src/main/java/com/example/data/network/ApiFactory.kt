package com.example.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {


    private val retrofit =
        Retrofit.Builder()
            .baseUrl("https://maps.locarus.ru/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service = retrofit.create(ApiService::class.java)


}