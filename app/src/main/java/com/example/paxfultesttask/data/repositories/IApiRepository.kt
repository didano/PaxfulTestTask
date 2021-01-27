package com.example.paxfultesttask.data.repositories

import com.example.paxfultesttask.data.models.JokeResponse

interface IApiRepository {

    suspend fun getJokes(): ApiRepository.ResultWrapper<JokeResponse>

    suspend fun getNamedJokes(firstName:String,lastName:String): ApiRepository.ResultWrapper<JokeResponse>

}