package com.example.sleeptrackingapp.model.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "SLEEP_TRACK")
data class SleepTrack(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID", )
    val id: Int?,

    @ColumnInfo(name = "HOURS")
    val hours: Int,

    @ColumnInfo(name = "DATE_STAMP")
    val dateStamp: String
)
