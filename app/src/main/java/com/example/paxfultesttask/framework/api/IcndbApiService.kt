package com.example.paxfultesttask.framework.api

import com.example.paxfultesttask.core.domain.JokeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IcndbApiService {
    @GET("jokes")
    suspend fun getJokes(): JokeResponse

    @GET("jokes/")
    suspend fun getJokeByNameSurname(
        @Query("firstName") name: String,
        @Query("lastName") surname: String): JokeResponse
}