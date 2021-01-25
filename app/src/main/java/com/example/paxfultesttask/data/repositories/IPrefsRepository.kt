package com.example.paxfultesttask.data.repositories

import com.example.paxfultesttask.data.models.JokesPreferences

interface IPrefsRepository {
    suspend fun getPreferences(): JokesPreferences
    suspend fun writePreferences(prefs:JokesPreferences)
    suspend fun writeLastName(lastName:String)
    suspend fun getLastName(): String
    suspend fun writeFirstName(firstName:String)
    suspend fun getFirstName(): String
    suspend fun writeOfflineMode(mode:Boolean)
    suspend fun getOfflineMode(): Boolean
}