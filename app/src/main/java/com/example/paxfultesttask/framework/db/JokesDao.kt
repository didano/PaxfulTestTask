package com.example.paxfultesttask.framework.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.paxfultesttask.core.domain.Joke
import com.example.paxfultesttask.core.domain.MyJoke

@Dao
interface JokesDao {

    @Insert(onConflict = REPLACE)
    suspend fun addJoke(joke:Joke)

    @Query("SELECT * FROM jokes_table")
    suspend fun getAllJokes(): List<Joke>

    @Query("SELECT * FROM jokes_table WHERE jokeText = :jokeText")
    suspend fun getJoke(jokeText:String): List<Joke>

    @Query("SELECT * FROM jokes_table ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomJoke(): List<Joke>

    @Delete
    suspend fun removeJoke(joke: Joke)
}