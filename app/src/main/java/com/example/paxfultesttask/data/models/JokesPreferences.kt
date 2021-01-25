package com.example.paxfultesttask.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "preferences")
data class JokesPreferences(
    @PrimaryKey(autoGenerate = false) var id:Int =0,
    @ColumnInfo(name = "firstName")var firstName:String ="",
    @ColumnInfo(name = "lastName")var lastName:String ="",
    @ColumnInfo(name = "offlineMode")var offlineMode:Boolean = false
)