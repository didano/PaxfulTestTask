package com.example.paxfultesttask.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_jokes")
data class MyJoke(
    @PrimaryKey(autoGenerate = true)var id:Int = 0,
    @ColumnInfo(name = "jokeText")var joke: String = ""
)