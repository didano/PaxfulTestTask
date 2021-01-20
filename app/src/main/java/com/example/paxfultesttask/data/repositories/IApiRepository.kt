package com.example.paxfultesttask.data.repositories

import com.example.paxfultesttask.data.models.Joke

interface IApiRepository {

    suspend fun getJokes():List<Joke>
    suspend fun getNamedJokes(firstName:String,lastName:String):List<Joke>

}