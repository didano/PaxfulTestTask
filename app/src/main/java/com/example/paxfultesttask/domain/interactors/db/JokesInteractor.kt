package com.example.paxfultesttask.domain.interactors.db

import com.example.paxfultesttask.data.models.Joke
import com.example.paxfultesttask.data.models.JokesPreferences
import com.example.paxfultesttask.data.repositories.ApiRepository
import com.example.paxfultesttask.data.repositories.IApiRepository
import com.example.paxfultesttask.data.repositories.IJokesDbRepository
import com.example.paxfultesttask.data.repositories.IPrefsRepository

class JokesInteractor(
    val apiRepository: IApiRepository,
    val jokesDbRepository: IJokesDbRepository,
    val prefsDbRepository: IPrefsRepository
) : IJokesInteractor {
    override suspend fun addJoke(joke: Joke) {
        jokesDbRepository.addJoke(joke)
    }

    override suspend fun getRandomJoke(): List<Joke> {
            return jokesDbRepository.getRandomJoke()
    }

    override suspend fun likeJoke(joke: Joke) {
        jokesDbRepository.addLikedJoke(joke)
    }

    override suspend fun getAllLikedJokes():List<Joke> {
        return jokesDbRepository.getLikedJokes()
    }

    override suspend fun deleteJokeFromLiked(joke: Joke) {
        jokesDbRepository.deleteLikedJoke(joke)
    }

    override suspend fun writeFirstName(firstName: String) {
        prefsDbRepository.writeFirstName(firstName)
    }

    override suspend fun getPreferences(): JokesPreferences {
        return prefsDbRepository.getPreferences()
    }

    override suspend fun getFirstName(): String {
        return prefsDbRepository.getFirstName()
    }

    override suspend fun writeLastName(lastName: String) {
        prefsDbRepository.writeLastName(lastName)
    }

    override suspend fun getLastName(): String {
        return prefsDbRepository.getLastName()
    }

    override suspend fun writeOfflineMode(mode: Boolean) {
        prefsDbRepository.writeOfflineMode(mode)
    }

    override suspend fun getOfflineMode(): Boolean =
        prefsDbRepository.getOfflineMode()

    override suspend fun getAllJokesApi(
        firstName: String?,
        lastName: String?
    ): List<Joke> {
        return if (prefsDbRepository.getOfflineMode()) {
            val result = jokesDbRepository.getAllJokes()
            for(element in result){
                element.joke.replace("Chuck",firstName?:"Chuck")
                element.joke.replace("Norris",lastName?:"Norris")
            }
            result
        } else {
            val response = apiRepository.getNamedJokes(firstName?:"Chuck",lastName?:"Norris")
            when (response) {
                is ApiRepository.ResultWrapper.Success -> {
                    val result = response.value.value
                    for (element in result) {
                        element.joke.replace(firstName?:"Chuck","Chuck")
                        element.joke.replace(lastName?:"Norris","Norris")
                        jokesDbRepository.addJoke(element)
                    }
                    response.value.value
                }
                else -> emptyList()
            }
        }
    }
}