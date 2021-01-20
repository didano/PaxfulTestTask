package com.example.paxfultesttask.data.repositories

import com.example.paxfultesttask.data.models.MyJoke

interface IMyJokesDbRepository {
    suspend fun addJokeToLiked(likedJoke: MyJoke)
    suspend fun getAllLikedJokes(): List<MyJoke>
    suspend fun deleteMyJoke(myJoke: MyJoke)
}