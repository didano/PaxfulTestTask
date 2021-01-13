package com.example.paxfultesttask.framework.db

import androidx.room.*
import com.example.paxfultesttask.core.domain.Joke
import com.example.paxfultesttask.core.domain.MyJoke

@Dao
interface MyJokesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addJokeToLiked(likedJoke: MyJoke)

    @Query("SELECT * FROM my_jokes")
    suspend fun getAllLikedJokes(): List<MyJoke>

    @Delete
    suspend fun deleteMyJoke(myJoke: MyJoke)
}