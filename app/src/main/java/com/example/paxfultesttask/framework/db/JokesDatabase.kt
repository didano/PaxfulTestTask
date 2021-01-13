package com.example.paxfultesttask.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.paxfultesttask.core.domain.Joke
import com.example.paxfultesttask.core.domain.MyJoke
import com.example.paxfultesttask.core.domain.JokesPreferences


@Database(
    entities = [Joke::class, MyJoke::class, JokesPreferences::class],
    version = 1,
    exportSchema = false
)
abstract class JokesDatabase:RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "jokes.db"

        private var instance: JokesDatabase? = null

        private fun create(context: Context): JokesDatabase =
            Room.databaseBuilder(context,JokesDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()

        fun getInstance(context: Context): JokesDatabase =
            (instance?: create(context)).also { instance = it }
    }

    abstract fun jokesDao():JokesDao
    abstract fun myJokesDao():MyJokesDao
    abstract fun myPrefsDao():PrefsDao
}