package com.example.sleeptrackingapp.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.sleeptrackingapp.model.db.DatabaseInit
import com.example.sleeptrackingapp.model.db.entity.SleepTrack
import kotlinx.coroutines.launch

class ActionModel(private val navController: NavHostController,
                  private val db: DatabaseInit,
                  private val context: Context) : ViewModel() {

    var date =  mutableStateOf<String>("")
    var hours = mutableStateOf<String>("")

    fun insertSleepTrackData(sleepTrack: SleepTrack) {
        viewModelScope.launch {
            db.sleepTrackDao().insertSleepTrackData(sleepTrack)
            Toast.makeText(context, "Sleep data of ${hours.value} inserted on ${date.value}", Toast.LENGTH_SHORT).show()
            date.value = ""
            hours.value = ""
        }
    }

    suspend fun get7daysTrackDetails(): List<SleepTrack> {
        return db.sleepTrackDao().get7DaysSleepTrackData()
    }

    suspend fun get28daysTrackDetails(): List<SleepTrack> {
        return db.sleepTrackDao().get28DaysSleepTrackData()
    }

    fun getContext(): Context {
        return context
    }
    fun getDb(): DatabaseInit {
        return db
    }
    fun getNavController(): NavHostController {
        return navController
    }
}


class ActionModelFactory(private val navController: NavHostController,
                         private val db: DatabaseInit,
                         private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActionModel::class.java)) {
            return ActionModel(navController, db, context) as T
        }
        throw RuntimeException("View model factory creation error")
    }
}