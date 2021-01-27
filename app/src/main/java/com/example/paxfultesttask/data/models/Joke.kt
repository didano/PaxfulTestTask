package com.example.paxfultesttask.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "jokes_table")
data class Joke(
    @PrimaryKey(autoGenerate = true)var id:Int = 0,
    @ColumnInfo(name = "jokeText")var joke: String = "",
    @ColumnInfo(name = "liked")var isLiked: Boolean = false,
    @Ignore var errorText: String? = ""
)