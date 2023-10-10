package com.example.sleeptrackingapp.model.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sleeptrackingapp.view.HomeScreen
import com.example.sleeptrackingapp.view.ReportScreen
import com.example.sleeptrackingapp.viewmodel.ActionModel

@Composable
fun getNav(actionModel: ActionModel) {
    NavHost(navController = actionModel.getNavController(), startDestination = "home") {
        composable("home") {
            HomeScreen(actionModel = actionModel)
        }
        composable("reports") {
            ReportScreen(actionModel = actionModel)
        }
    }
}