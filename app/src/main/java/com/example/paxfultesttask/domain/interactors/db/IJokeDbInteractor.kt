package com.example.paxfultesttask.domain.interactors.db

import com.example.paxfultesttask.data.models.Joke
import com.example.paxfultesttask.data.models.JokesPreferences
import com.example.paxfultesttask.data.models.MyJoke

interface IJokeDbInteractor {
    suspend fun addJoke(joke: Joke)
    suspend fun getAllJokes():List<Joke>
    suspend fun getRandomJoke():List<Joke>

    suspend fun deleteMyJoke(myJoke: MyJoke)
    suspend fun getAllMyJokes():List<MyJoke>
    suspend fun likeJoke(joke: Joke)
    suspend fun addOwnJoke(myJoke: MyJoke)

    suspend fun getPreferences():JokesPreferences
    suspend fun writePreferences(prefs:JokesPreferences)
    suspend fun writeFirstName(firstName:String)
    suspend fun getFirstName():String
    suspend fun writeLastName(lastName:String)
    suspend fun getLastName():String
    suspend fun writeOfflineMode(mode:Int)
    suspend fun getOfflineMode():Int

}