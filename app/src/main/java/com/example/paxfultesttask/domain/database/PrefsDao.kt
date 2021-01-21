package com.example.paxfultesttask.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paxfultesttask.data.models.JokesPreferences

@Dao
interface PrefsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun changePref(prefs: JokesPreferences)

    @Query("SELECT * FROM preferences")
    suspend fun getPrefs(): JokesPreferences?

    @Query("SELECT firstName FROM preferences")
    suspend fun getFirstName():String

    @Query("SELECT lastName FROM preferences")
    suspend fun getLastName():String

    @Query("SELECT offlineMode FROM preferences")
    suspend fun getOfflineMod():Int

    @Query("UPDATE preferences SET offlineMode = :value WHERE id = 0")
    suspend fun setOfflineMod(value:Int)

    @Query("UPDATE preferences SET firstName = :value WHERE id = 0")
    suspend fun setFirstName(value:String)

    @Query("UPDATE preferences SET lastName = :value WHERE id = 0")
    suspend fun setLastName(value:String)
}