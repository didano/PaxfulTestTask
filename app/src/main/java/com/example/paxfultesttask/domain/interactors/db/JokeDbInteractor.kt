package com.example.paxfultesttask.domain.interactors.db

import com.example.paxfultesttask.data.models.Joke
import com.example.paxfultesttask.data.models.JokesPreferences
import com.example.paxfultesttask.data.models.MyJoke
import com.example.paxfultesttask.data.repositories.IJokesDbRepository
import com.example.paxfultesttask.data.repositories.IMyJokesDbRepository
import com.example.paxfultesttask.data.repositories.IPrefsRepository

class JokeDbInteractor(
    val myJokesDbRepository: IMyJokesDbRepository,
    val jokesDbRepository: IJokesDbRepository,
    val prefsDbRepository: IPrefsRepository
) : IJokeDbInteractor {
    override suspend fun addJoke(joke: Joke) {
        jokesDbRepository.addJoke(joke)
    }

    override suspend fun getAllJokes(): List<Joke> {
        return jokesDbRepository.getAllJokes()
    }

    override suspend fun getRandomJoke(): List<Joke> {
        return jokesDbRepository.getRandomJoke()
    }

    override suspend fun deleteMyJoke(myJoke: MyJoke) {
        myJokesDbRepository.deleteMyJoke(myJoke)
    }

    override suspend fun getAllMyJokes(): List<MyJoke> {
        return myJokesDbRepository.getAllLikedJokes()
    }

    override suspend fun likeJoke(joke: Joke) {
        val result = myJokesDbRepository.getAllLikedJokes()
        for (element in result){
            if(element.joke==joke.joke)
                return
        }
        for (element in result){
            if(element.id==joke.id)
                joke.id = result.size+1
        }
        myJokesDbRepository.addJokeToLiked(joke.getMyJoke())
    }

    override suspend fun addOwnJoke(myJoke: MyJoke) {
        val result = myJokesDbRepository.getAllLikedJokes()
        for (element in result){
            if(element.joke==myJoke.joke)
                return
        }
        for (element in result){
            if(element.id==myJoke.id)
                myJoke.id = result.size+1
        }
        myJokesDbRepository.addJokeToLiked(myJoke)
    }

    override suspend fun getPreferences(): JokesPreferences {
        val result = prefsDbRepository.getPreferences()
        if(result==null){
            return JokesPreferences()
        } else {
            return result
        }
    }

    override suspend fun writePreferences(prefs: JokesPreferences) {
        prefsDbRepository.writePreferences(prefs)
    }

    override suspend fun writeFirstName(firstName: String) {
        prefsDbRepository.writeFirstName(firstName)
    }

    override suspend fun getFirstName(): String {
        return  prefsDbRepository.getFirstName()
    }

    override suspend fun writeLastName(lastName: String) {
        prefsDbRepository.writeLastName(lastName)
    }

    override suspend fun getLastName(): String {
        return prefsDbRepository.getLastName()
    }

    override suspend fun writeOfflineMode(mode: Int) {
        prefsDbRepository.writeOfflineMode(mode)
    }

    override suspend fun getOfflineMode(): Int {
        return prefsDbRepository.getOfflineMode()
    }
}