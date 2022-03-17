package com.example.thecatapi.retrofit

import com.example.thecatapi.retrofit.service.RetrofitService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHttp {
    val IS_TESTER =true
    val SERVER_DEVELOPER= "https://api.thecatapi.com/v1/"
    val SERVER_PRODUCTION = "https://api.thecatapi.com/v1/"
    val retrofit = Retrofit.Builder().baseUrl(server()).addConverterFactory(GsonConverterFactory.create()).build()

    fun server(): String{
        if (IS_TESTER) return SERVER_DEVELOPER
        return SERVER_PRODUCTION
    }

    val photoService:RetrofitService = retrofit.create(RetrofitService::class.java)
}