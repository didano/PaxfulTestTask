package com.example.paxfultesttask.framework.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.paxfultesttask.domain.database.JokesDatabase
import com.google.common.truth.Truth
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
class MyJokesDatabaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: JokesDatabase
    private lateinit var jokesDao: MyJokesDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            JokesDatabase::class.java
        ).allowMainThreadQueries().build()
        jokesDao = database.myJokesDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertMyJoke() = runBlockingTest{
        val joke = MyJoke(1,"MyJoke text")
        jokesDao.addJokeToLiked(joke)
        val resultJoke = jokesDao.getAllLikedJokes()
        Truth.assertThat(resultJoke).contains(joke)
    }

    @Test
    fun deleteMyJoke() = runBlockingTest{
        val joke = MyJoke(1,"MyJoke text")
        jokesDao.addJokeToLiked(joke)
        jokesDao.deleteMyJoke(joke)
        val resultJoke = jokesDao.getAllLikedJokes()
        Truth.assertThat(resultJoke).doesNotContain(joke)
    }
}