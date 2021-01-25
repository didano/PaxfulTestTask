package com.example.paxfultesttask.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.paxfultesttask.data.models.Joke
import com.example.paxfultesttask.data.models.JokesPreferences


@Database(
    entities = [Joke::class, JokesPreferences::class],
    version = 1,
    exportSchema = false
)
abstract class JokesDatabase:RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "jokes.db"

        private var instance: JokesDatabase? = null

        private fun create(context: Context): JokesDatabase =
            Room.databaseBuilder(context, JokesDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()

        fun getInstance(context: Context): JokesDatabase =
            (instance ?: create(context)).also { instance = it }
    }

    abstract fun jokesDao(): JokesDao
    abstract fun myPrefsDao(): PrefsDao
}