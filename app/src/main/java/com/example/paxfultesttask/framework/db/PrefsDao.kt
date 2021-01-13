package com.example.paxfultesttask.framework.db

import androidx.room.*
import com.example.paxfultesttask.core.domain.JokesPreferences

@Dao
interface PrefsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun changePref(prefs: JokesPreferences)

    @Query("SELECT * FROM preferences")
    suspend fun getPrefs(): JokesPreferences?

    @Query("SELECT firstName FROM preferences")
    suspend fun getFirstName(): String
}