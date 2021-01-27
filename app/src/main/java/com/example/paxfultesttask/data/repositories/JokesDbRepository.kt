package com.example.paxfultesttask.data.repositories

import android.content.Context
import com.example.paxfultesttask.data.models.Joke
import com.example.paxfultesttask.domain.database.JokesDatabase

class JokesDbRepository(val context: Context) : IJokesDbRepository {

    val db = JokesDatabase.getInstance(context).jokesDao()

    override suspend fun addJoke(joke: Joke) {
        db.addJoke(joke)
    }

    override suspend fun getAllJokes(): List<Joke> {
        return db.getAllJokes()
    }

    override suspend fun getRandomJoke(): List<Joke> {
        return db.getRandomJoke()
    }

    override suspend fun addLikedJoke(joke: Joke) {
        db.addJoke(joke)
    }

    override suspend fun getLikedJokes(): List<Joke> {
        return db.getAllLikedJokes()
    }

    override suspend fun dislikeJokeRepo(joke: Joke) {
        db.addJoke(joke)
    }
}