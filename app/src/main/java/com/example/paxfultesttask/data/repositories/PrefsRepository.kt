package com.example.paxfultesttask.data.repositories

import android.content.Context
import com.example.paxfultesttask.data.models.JokesPreferences
import com.example.paxfultesttask.domain.database.JokesDatabase

class PrefsRepository(val context: Context) : IPrefsRepository {

    val db = JokesDatabase.getInstance(context).myPrefsDao()

    override suspend fun getPreferences(): JokesPreferences {
        return db.getPrefs() ?: JokesPreferences()
    }

    override suspend fun writePreferences(prefs: JokesPreferences) {
        db.changePref(prefs)
    }

    override suspend fun getLastName(): String? {
        return db.getLastName()
    }

    override suspend fun getFirstName(): String? {
        return db.getFirstName()
    }

    override suspend fun getOfflineMode(): Boolean = db.getOfflineMod() ?: false

}