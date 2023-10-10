package com.example.sleeptrackingapp.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sleeptrackingapp.model.db.dao.SleepTrackDao
import com.example.sleeptrackingapp.model.db.entity.SleepTrack

@Database(entities = [SleepTrack::class], version = 1)
abstract class DatabaseInit: RoomDatabase() {
    abstract fun sleepTrackDao(): SleepTrackDao

    companion object {
        @Volatile
        private var INSTANCE: DatabaseInit? = null;

        fun getDatabase(context : Context): DatabaseInit {
            if(INSTANCE != null) {
                return INSTANCE!!
            } else {
                synchronized(this) {
                    val inst = Room.databaseBuilder(context.applicationContext,
                        DatabaseInit::class.java, "SleepTrack6Oct2023_V1").build();
                    INSTANCE = inst
                    return inst;
                }
            }
        }
    }
}