package com.example.paxfultesttask.domain.interactors

import com.example.paxfultesttask.data.models.Joke
import com.example.paxfultesttask.data.models.JokesPreferences

interface IJokesInteractor {
    suspend fun addJoke(joke: Joke)
    suspend fun getRandomJoke(): List<Joke>
    suspend fun likeJoke(joke: Joke)
    suspend fun getAllLikedJokes(): List<Joke>
    suspend fun deleteJokeFromLiked(joke: Joke)

    suspend fun writeFirstName(firstName: String?)
    suspend fun getPreferences(): JokesPreferences
    suspend fun getFirstName(): String?
    suspend fun writeLastName(lastName: String?)
    suspend fun getLastName(): String?
    suspend fun writeOfflineMode(mode: Boolean)
    suspend fun getOfflineMode(): Boolean

    suspend fun getAllJokesApi(firstName: String?, lastName: String?): List<Joke>

}