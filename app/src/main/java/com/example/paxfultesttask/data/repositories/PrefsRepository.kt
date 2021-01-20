package com.example.paxfultesttask.data.repositories

import android.content.Context
import com.example.paxfultesttask.data.models.JokesPreferences
import com.example.paxfultesttask.domain.database.JokesDatabase

class PrefsRepository(val context: Context):IPrefsRepository {

    val db = JokesDatabase.getInstance(context).myPrefsDao()
    override suspend fun getPreferences(): JokesPreferences? {
        return db.getPrefs()
    }

    override suspend fun writePreferences(prefs: JokesPreferences) {
        db.changePref(prefs)
    }

    override suspend fun writeLastName(lastName: String) {
        db.setLastName(lastName)
    }

    override suspend fun getLastName(): String {
        return db.getLastName()
    }

    override suspend fun writeFirstName(firstName: String) {
        db.setFirstName(firstName)
    }

    override suspend fun getFirstName(): String {
        return db.getFirstName()
    }

    override suspend fun writeOfflineMode(mode: Int) {
        db.setOfflineMod(mode)
    }

    override suspend fun getOfflineMode(): Int {
        return db.getOfflineMod()
    }

}