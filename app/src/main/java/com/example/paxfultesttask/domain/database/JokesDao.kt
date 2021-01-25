package com.example.paxfultesttask.domain.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.paxfultesttask.data.models.Joke

@Dao
interface JokesDao {

    @Insert(onConflict = REPLACE)
    suspend fun addJoke(joke: Joke)

    @Query("SELECT * FROM jokes_table")
    suspend fun getAllJokes(): List<Joke>

    @Query("SELECT * FROM jokes_table ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomJoke(): List<Joke>

    @Query("SELECT * FROM jokes_table WHERE liked > 0")
    suspend fun getAllLikedJokes(): List<Joke>

    @Update
    suspend fun updateJoke(joke:Joke)

}