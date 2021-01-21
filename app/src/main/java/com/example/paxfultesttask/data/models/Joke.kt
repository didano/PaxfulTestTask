package com.example.paxfultesttask.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jokes_table")
data class Joke(
    @PrimaryKey(autoGenerate = false)var id:Int,
    @ColumnInfo(name = "jokeText")var joke: String = ""
) {
    fun getMyJoke() = MyJoke(id=id,joke = joke)
}