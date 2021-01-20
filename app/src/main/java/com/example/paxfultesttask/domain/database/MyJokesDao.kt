package com.example.paxfultesttask.domain.database

import androidx.room.*
import com.example.paxfultesttask.data.models.MyJoke

@Dao
interface MyJokesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addJokeToLiked(likedJoke: MyJoke)

    @Query("SELECT * FROM my_jokes")
    suspend fun getAllLikedJokes(): List<MyJoke>

    @Delete
    suspend fun deleteMyJoke(myJoke: MyJoke)
}