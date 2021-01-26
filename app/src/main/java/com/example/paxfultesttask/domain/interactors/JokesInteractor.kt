package com.example.paxfultesttask.domain.interactors

import com.example.paxfultesttask.data.models.Joke
import com.example.paxfultesttask.data.models.JokesPreferences
import com.example.paxfultesttask.data.repositories.ApiRepository
import com.example.paxfultesttask.data.repositories.IApiRepository
import com.example.paxfultesttask.data.repositories.IJokesDbRepository
import com.example.paxfultesttask.data.repositories.IPrefsRepository
import com.example.paxfultesttask.utils.getNullIfBlank
import com.example.paxfultesttask.utils.replaceCustomNameToDefault
import com.example.paxfultesttask.utils.replaceDefaultNameToCustom

class JokesInteractor(
    private val apiRepository: IApiRepository,
    private val jokesDbRepository: IJokesDbRepository,
    private val prefsDbRepository: IPrefsRepository
) : IJokesInteractor {
    override suspend fun addJoke(joke: Joke) {
        jokesDbRepository.addJoke(joke)
    }

    override suspend fun getRandomJoke(): List<Joke> {
        val prefs = prefsDbRepository.getPreferences()
        val joke = jokesDbRepository.getRandomJoke()
        joke.map {
            it.joke = it.joke.replaceDefaultNameToCustom(prefs.firstName.getNullIfBlank(),
                prefs.lastName.getNullIfBlank())
        }
        return joke
    }

    override suspend fun likeJoke(joke: Joke) {
        jokesDbRepository.addLikedJoke(joke)
    }

    override suspend fun getAllLikedJokes(): List<Joke> {
        val preferences = prefsDbRepository.getPreferences()
        return jokesDbRepository.getLikedJokes().map {
            it.joke = it.joke.replaceDefaultNameToCustom(preferences.firstName.getNullIfBlank(),
                preferences.lastName.getNullIfBlank())
            it
        }
    }

    override suspend fun deleteJokeFromLiked(joke: Joke) {
        jokesDbRepository.deleteLikedJoke(joke)
    }

    override suspend fun writeFirstName(firstName: String?) {
        val prefs = prefsDbRepository.getPreferences()
        prefs.firstName = firstName ?: "Chuck"
        prefsDbRepository.writePreferences(prefs)
    }

    override suspend fun getPreferences(): JokesPreferences {
        return prefsDbRepository.getPreferences()
    }

    override suspend fun getFirstName(): String? {
        return prefsDbRepository.getFirstName()
    }

    override suspend fun writeLastName(lastName: String?) {
        val prefs = prefsDbRepository.getPreferences()
        prefs.lastName = lastName ?: "Norris"
        prefsDbRepository.writePreferences(prefs)
    }

    override suspend fun getLastName(): String? {
        return prefsDbRepository.getLastName()
    }

    override suspend fun writeOfflineMode(mode: Boolean) {
        val prefs = prefsDbRepository.getPreferences()
        prefs.offlineMode = mode
        prefsDbRepository.writePreferences(prefs)
    }

    override suspend fun getOfflineMode(): Boolean =
        prefsDbRepository.getOfflineMode()

    override suspend fun getAllJokesApi(
        firstName: String?,
        lastName: String?
    ): List<Joke> {
        return if (prefsDbRepository.getOfflineMode()) {
            val result = jokesDbRepository.getAllJokes()
            for (element in result) {
                element.joke = element.joke.replaceDefaultNameToCustom(firstName, lastName)
            }
            result
        } else {
            val response = apiRepository.getNamedJokes(firstName ?: "Chuck", lastName ?: "Norris")
            when (response) {
                is ApiRepository.ResultWrapper.Success -> {
                    val toDbList = response.value.value
                    val toViewList = ArrayList(toDbList.map { it.copy() })
                    val likedJokesIds = jokesDbRepository.getLikedJokes().map { it.id }
                    toDbList.map {
                        it.joke = it.joke.replaceCustomNameToDefault(firstName, lastName)
                        if (likedJokesIds.contains(it.id)) {
                            it.isLiked = true
                        }
                        jokesDbRepository.addJoke(it)
                    }
                    toViewList
                }
                else -> emptyList()
            }
        }
    }
}