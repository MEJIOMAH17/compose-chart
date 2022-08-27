// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.mejiomah17.common.App
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        Chart()
    }
}

@Preview
@Composable
fun Chart() {
    var percentage by remember { mutableStateOf(1) }
    val scope = rememberCoroutineScope()
    LaunchedEffect("") {
        scope.launch {
            var direction = true
            while (true) {
                if (direction) {
                    percentage++
                    if (percentage > 99) {
                        direction = false
                    }
                } else {
                    percentage--
                    if (percentage < 1) {
                        direction = true
                    }
                }
                delay(25)
            }
        }
    }

    PieChart(percentage)
}

@Composable
fun PieChart(percentage: Int) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val sizeInPx = min(constraints.maxHeight, constraints.maxWidth)
        val paddingInPx = (sizeInPx.toFloat() / 2) * percentage / 100
        val sizeMinusPadding = sizeInPx - paddingInPx
        Canvas(modifier = Modifier.fillMaxSize()) {
            val sweep = 90f
            var cur = 0f
            fun Arc(color: Color) {
                drawArc(
                    color = color,
                    startAngle = cur,
                    sweepAngle = sweep,
                    useCenter = false,
                    size = Size(sizeMinusPadding, sizeMinusPadding),
                    style = Stroke(width = paddingInPx),
                    topLeft = Offset(paddingInPx / 2, paddingInPx / 2)
                )
                cur += sweep
            }
            Arc(Color.Green)
            Arc(Color.Gray)
            Arc(Color.Red)
            Arc(Color.Blue)
        }
    }
}

@Composable
private fun Int.toDp(): Dp {
    val value = this
    return LocalDensity.current.run {
        value.toDp()
    }
}