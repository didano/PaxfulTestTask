package com.example.paxfultesttask.domain.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IcndbApiFactory {

    companion object {
        private const val BASE_URL = "http://api.icndb.com/"

        private var instance: IcndbApiService? = null

        private fun create(): IcndbApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).build()
            val apiService: IcndbApiService = retrofit.create(IcndbApiService::class.java)
            return apiService
        }

        fun getInstance(): IcndbApiService = (instance ?: create()).also { instance =it}
    }
}