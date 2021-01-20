package com.example.paxfultesttask.data.repositories

import android.content.Context
import com.example.paxfultesttask.data.models.MyJoke
import com.example.paxfultesttask.domain.database.JokesDatabase

class MyJokesDbRepository(val context: Context):IMyJokesDbRepository {

    val db = JokesDatabase.getInstance(context).myJokesDao()

    override suspend fun addJokeToLiked(likedJoke: MyJoke) {
        db.addJokeToLiked(likedJoke)
    }

    override suspend fun getAllLikedJokes(): List<MyJoke> {
        return db.getAllLikedJokes()
    }

    override suspend fun deleteMyJoke(myJoke: MyJoke) {
        db.deleteMyJoke(myJoke)
    }
}