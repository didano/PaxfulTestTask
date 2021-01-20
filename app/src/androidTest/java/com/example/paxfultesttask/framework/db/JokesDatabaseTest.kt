package com.example.paxfultesttask.framework.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.paxfultesttask.domain.database.JokesDao
import com.example.paxfultesttask.domain.database.JokesDatabase
import com.example.paxfultesttask.data.models.Joke
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class JokesDatabaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: JokesDatabase
    private lateinit var jokesDao: JokesDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            JokesDatabase::class.java
        ).allowMainThreadQueries().build()
        jokesDao = database.jokesDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertJoke() = runBlockingTest{
        val joke = Joke(1, "Joke text")
        jokesDao.addJoke(joke)
        val resultJoke = jokesDao.getAllJokes()
        assertThat(resultJoke).contains(joke)
    }

    @Test
    fun getRandomJoke() = runBlockingTest {
        val jokesArray = arrayOf(
            Joke(1, "Joke text"), Joke(2, "Joke text")
        , Joke(3, "Joke text"), Joke(4, "Joke text")
        )
        jokesDao.addJoke(jokesArray[0])
        jokesDao.addJoke(jokesArray[1])
        jokesDao.addJoke(jokesArray[2])
        jokesDao.addJoke(jokesArray[3])
        val resultJoke = jokesDao.getRandomJoke()
        assertThat(resultJoke).containsAnyIn(jokesArray)
    }
}