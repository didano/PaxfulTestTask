package com.example.paxfultesttask.domain.api

import com.example.paxfultesttask.data.models.JokeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



interface IcndbApiService {
    @GET("jokes/")
    suspend fun getJokes(): Response<JokeResponse>

    @GET("jokes/")
    suspend fun getNamedJokes(
        @Query("firstName") name: String,
        @Query("lastName") surname: String): Response<JokeResponse>
}