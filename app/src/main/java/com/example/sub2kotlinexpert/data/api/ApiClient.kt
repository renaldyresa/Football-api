package com.example.sub2kotlinexpert.data.api

import com.example.sub2kotlinexpert.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    val INSTANCE: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        retrofit.create(Api::class.java)
    }


}