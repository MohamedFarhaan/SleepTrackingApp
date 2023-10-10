package com.example.sleeptrackingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sleeptrackingapp.model.db.DatabaseInit
import com.example.sleeptrackingapp.model.navigation.getNav
import com.example.sleeptrackingapp.ui.theme.SleepTrackingAppTheme
import com.example.sleeptrackingapp.viewmodel.ActionModel
import com.example.sleeptrackingapp.viewmodel.ActionModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SleepTrackingAppTheme {
                val db = DatabaseInit.getDatabase(context = applicationContext)
                val navController = rememberNavController()
                val actionModel: ActionModel = viewModel(factory = ActionModelFactory(navController, db, applicationContext))
                App(actionModel);
            }
        }
    }
}

@Composable
fun App(actionModel: ActionModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        getNav(actionModel)
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    SleepTrackingAppTheme {
        val context = LocalContext.current
        App(viewModel(factory = ActionModelFactory(rememberNavController(), DatabaseInit.getDatabase(context), context)))
    }
}