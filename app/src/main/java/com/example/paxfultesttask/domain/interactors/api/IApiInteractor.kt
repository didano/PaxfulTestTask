package com.example.paxfultesttask.domain.interactors.api

import com.example.paxfultesttask.data.models.Joke

interface IApiInteractor {
    suspend fun getAllJokes():List<Joke>
    suspend fun getAllNamedJokes(firstName:String, lastName:String):List<Joke>
}