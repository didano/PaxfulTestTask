package com.example.paxfultesttask.domain.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IcndbApiFactory {

    companion object {
        private const val BASE_URL = "http://api.icndb.com/"

        private var instance: IcndbApiService? = null

        private fun create(): IcndbApiService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).build()
            return retrofit.create(IcndbApiService::class.java)
        }

        fun getInstance(): IcndbApiService = (instance ?: create()).also { instance = it }
    }
}