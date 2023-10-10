package com.example.sleeptrackingapp.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import co.yml.charts.axis.AxisData
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.common.model.Point
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarChartType
import co.yml.charts.ui.barchart.models.BarData
import com.example.sleeptrackingapp.model.db.DatabaseInit
import com.example.sleeptrackingapp.model.db.entity.SleepTrack
import com.example.sleeptrackingapp.ui.theme.SleepTrackingAppTheme
import com.example.sleeptrackingapp.viewmodel.ActionModel
import com.example.sleeptrackingapp.viewmodel.ActionModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Objects

@Composable
fun ReportScreen(actionModel: ActionModel) {
    var weekBarData by remember {  mutableStateOf<List<BarData>>(listOf()) }
    var monthBarData by remember {  mutableStateOf<List<BarData>>(listOf()) }
    LaunchedEffect(key1 = "weekData") {
        this.launch {
            actionModel.get7daysTrackDetails().forEachIndexed { index, item ->
                var barData: BarData = BarData(
                    Point(index.toFloat(), item.hours.toFloat()),
                    Color.Gray,
                    label = item.dateStamp
                )
                Log.d("Week ${index+1}  ${item.dateStamp}", item.hours.toString())
                weekBarData = weekBarData.toMutableList().apply {
                    add(barData)
                }.toList()
            }
            actionModel.get28daysTrackDetails().forEachIndexed { index, item ->
                var barData: BarData = BarData(
                    Point(index.toFloat(), item.hours.toFloat()),
                    Color.Gray,
                    label = item.dateStamp
                )
                Log.d("Month ${index+1}  ${item.dateStamp}", item.hours.toString())
                monthBarData = monthBarData.toMutableList().apply {
                    add(barData)
                }.toList()
            }
        }
    }
    Column(
        Modifier
            .fillMaxSize()) {
        //Last 7 days
        if(weekBarData.size > 0) {
            Column(
                Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth()
                    .padding(16.dp)) {
                Text(text= "Last 7 days")
                Chart(barsData = weekBarData)
            }
        }
        if(monthBarData.size > 0) {
            //Last 28 days
            Column(
                Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth()
                    .padding(16.dp)) {
                Text(text= "Last 28 days")
                Chart(barsData = monthBarData)
            }
        }
    }
}

@Composable
fun Chart(barsData: List<BarData>) {
    Column(
        Modifier.fillMaxSize()) {

        val maxRange: Int =8;
        val yStepSize: Int = 8
        val xAxisData = AxisData.Builder()
            .axisStepSize(20.dp)
            .steps(barsData.size - 1)
            .bottomPadding(90.dp)
            .axisLabelAngle(85f)
            .labelData { index -> barsData[index].label }
            .build()

        val yAxisData = AxisData.Builder()
            .steps(yStepSize)
            .labelAndAxisLinePadding(20.dp)
            .axisOffset(20.dp)
            .labelData { index -> (index * (maxRange / yStepSize)).toString() }
            .build()

        val barChartData = BarChartData(
            chartData = barsData,
            xAxisData = xAxisData,
            yAxisData = yAxisData,
            backgroundColor = MaterialTheme.colorScheme.background
        )
        BarChart(modifier = Modifier.fillMaxSize(),
            barChartData = barChartData)
    }
}

@Preview(showBackground = true)
@Composable
fun ReportScreenPreview() {
    SleepTrackingAppTheme {
        val context = LocalContext.current
        ReportScreen(viewModel(factory = ActionModelFactory(rememberNavController(), DatabaseInit.getDatabase(context), context)))
    }
}

