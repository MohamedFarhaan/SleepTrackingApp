package com.example.sleeptrackingapp.model.db.dao;

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sleeptrackingapp.model.db.entity.SleepTrack

@Dao
interface SleepTrackDao {

    @Insert
    suspend fun insertSleepTrackData(sleepTrack: SleepTrack)

    @Query("SELECT SUM(HOURS) AS HOURS, DATE_STAMP " +
            "FROM SLEEP_TRACK WHERE DATE(SUBSTR(DATE_STAMP, 7) || '-' || SUBSTR(DATE_STAMP, 4, 2) || '-' || SUBSTR(DATE_STAMP, 1, 2)) " +
            "BETWEEN DATE(CURRENT_TIMESTAMP, '-7 days') AND DATE(CURRENT_TIMESTAMP, '+1 days') " +
            "GROUP BY DATE_STAMP")
    suspend fun get7DaysSleepTrackData(): List<SleepTrack>

    @Query("SELECT SUM(HOURS) AS HOURS, DATE_STAMP " +
            "FROM SLEEP_TRACK WHERE DATE(SUBSTR(DATE_STAMP, 7) || '-' || SUBSTR(DATE_STAMP, 4, 2) || '-' || SUBSTR(DATE_STAMP, 1, 2)) " +
            "BETWEEN DATE(CURRENT_TIMESTAMP, '-28 days') AND DATE(CURRENT_TIMESTAMP, '+1 days') " +
            "GROUP BY DATE_STAMP")
    suspend fun get28DaysSleepTrackData(): List<SleepTrack>
}