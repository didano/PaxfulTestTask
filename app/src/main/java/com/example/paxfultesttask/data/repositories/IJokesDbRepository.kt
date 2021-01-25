package com.example.paxfultesttask.data.repositories

import com.example.paxfultesttask.data.models.Joke

interface IJokesDbRepository {
    suspend fun addJoke(joke:Joke)
    suspend fun getAllJokes():List<Joke>
    suspend fun getRandomJoke():List<Joke>
    suspend fun addLikedJoke(joke:Joke)
    suspend fun getLikedJokes():List<Joke>
    suspend fun deleteLikedJoke(joke:Joke)
}