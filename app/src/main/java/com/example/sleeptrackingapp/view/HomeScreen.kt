package com.example.sleeptrackingapp.view

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.sleeptrackingapp.App
import com.example.sleeptrackingapp.R
import com.example.sleeptrackingapp.model.db.DatabaseInit
import com.example.sleeptrackingapp.model.db.entity.SleepTrack
import com.example.sleeptrackingapp.ui.theme.SleepTrackingAppTheme
import com.example.sleeptrackingapp.viewmodel.ActionModel
import com.example.sleeptrackingapp.viewmodel.ActionModelFactory
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(actionModel: ActionModel) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        var showDatePicker by remember { mutableStateOf(false) }
        var datePickerDialog: DatePickerDialog = DatePickerDialog(context, {
                _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            actionModel.date.value = "${String.format("%0" + 2 + "d", mDayOfMonth.toInt())}/${String.format("%0" + 2 + "d", (mMonth+1).toInt())}/$mYear"
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONDAY), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        Text(text = "Sleep Tracking App", Modifier.padding(16.dp))
        TextField(
            value = actionModel.date.value,
            onValueChange = { actionModel.date.value = it },
            label = { Text(text = "Date") },
            modifier = Modifier.padding(16.dp),
            leadingIcon = { IconButton(onClick = { datePickerDialog.show() }) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Date picker")
            } }
        )
        TextField(
            value = actionModel.hours.value,
            onValueChange = { if(it.isDigitsOnly() && it.toInt() <= 24) actionModel.hours.value = it },
            label = { Text(text = "Hours of Sleep") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.padding(16.dp),
            leadingIcon = { IconButton(onClick = {  }) {
                Icon(imageVector = Icons.Rounded.DarkMode, contentDescription = "Date picker")
            } }
        )
        Row {
            Button(
                enabled = actionModel.hours.value.trim().length > 0 && actionModel.hours.value.toInt() <= 24 && actionModel.date.value.matches(Regex("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$")),
                modifier = Modifier.padding(16.dp),
                onClick = {
                    val sleepTrack = SleepTrack(id = null, dateStamp = actionModel.date.value, hours = actionModel.hours.value.toInt())
                    actionModel.insertSleepTrackData(sleepTrack)
                }
            ) {
                Text(text = "Save")
            }
            Button(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    actionModel.getNavController().navigate("reports")
                }
            ) {
                Text(text = "Navigate to Reports")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SleepTrackingAppTheme {
        val context = LocalContext.current
        HomeScreen(viewModel(factory = ActionModelFactory(rememberNavController(), DatabaseInit.getDatabase(context), context)))
    }
}