package com.example.paxfultesttask.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.paxfultesttask.data.models.Joke

@Dao
interface JokesDao {

    @Insert(onConflict = REPLACE)
    suspend fun addJoke(joke: Joke)

    @Query("SELECT * FROM jokes_table")
    suspend fun getAllJokes(): List<Joke>

    @Query("SELECT * FROM jokes_table ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomJoke(): List<Joke>
}