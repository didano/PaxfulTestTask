package com.example.paxfultesttask.framework.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.paxfultesttask.domain.database.JokesDatabase
import com.example.paxfultesttask.domain.database.PrefsDao
import com.example.paxfultesttask.data.models.JokesPreferences
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
class PreferencesDatabaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: JokesDatabase
    private lateinit var jokesDao: PrefsDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            JokesDatabase::class.java
        ).allowMainThreadQueries().build()
        jokesDao = database.myPrefsDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertPreference() = runBlockingTest{
        val preference = JokesPreferences(1,"my","pref",0)
        jokesDao.changePref(preference)
        val resultJoke = jokesDao.getPrefs()
        Truth.assertThat(resultJoke).isEqualTo(preference)
    }

    @Test
    fun changePreference() = runBlockingTest{
        val originPreference = JokesPreferences(1,"my","pref",0)
        val preferenceToChange = JokesPreferences(1,"changed","pref",1)
        jokesDao.changePref(originPreference)
        jokesDao.changePref(preferenceToChange)
        val resultJoke = jokesDao.getPrefs()
        Truth.assertThat(resultJoke).isEqualTo(preferenceToChange)
    }
}