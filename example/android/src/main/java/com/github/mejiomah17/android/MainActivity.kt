package com.github.mejiomah17.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.github.mejiomah17.compose.chart.pie.Percent
import com.github.mejiomah17.compose.chart.pie.PieChart
import com.github.mejiomah17.compose.chart.pie.PieChartSector

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                App()
            }
        }
    }

    @Composable
    fun App() {
        Chart()
    }

    @Composable
    fun Chart() {
        var x by remember { mutableStateOf(1) }
        Box(Modifier.fillMaxSize()) {
            PieChart(
                holeSize = Percent(40),
                sectors = listOf(
                    PrintChartSector(4, Color(64, 89, 128), "1"),
                    PieChartSector(x, Color(123, 200, 123)) {
                        println(x)
                        x += 1
                    },
                    PrintChartSector(10, Color(78, 67, 138), "2"),
                    PrintChartSector(3, Color(22, 105, 240), "3"),
                    PrintChartSector(3, Color(242, 172, 82), "4"),
                    PrintChartSector(3, Color(189, 100, 36), "5"),
                ),
                animationSpec = tween(5500),
                modifier = Modifier.align(Alignment.TopCenter).padding(10.dp)
            )
        }
    }

    @Composable
    fun PrintChartSector(
        weight: Int,
        color: Color,
        text: String,
    ): PieChartSector {
        // val icon = rememberAsyncImagePainter(R.drawable.ic_android_black_24dp)
        val icon = rememberVectorPainter(ImageVector.vectorResource(R.drawable.ic_baseline_battery_full_24))
        return PieChartSector(
            weight = weight,
            color = color,
            icon = icon,
        ) {
            println(text)
        }
    }
}
