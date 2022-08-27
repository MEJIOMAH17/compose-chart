// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.unit.Density
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.mejiomah17.compose.chart.Degrees
import com.github.mejiomah17.compose.chart.pie.Percent
import com.github.mejiomah17.compose.chart.pie.PieChart
import com.github.mejiomah17.compose.chart.pie.PieChartSector

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        Chart()
    }
}

@Preview
@Composable
fun Chart() {
    var x by remember { mutableStateOf(1) }
    var enableIcons by remember { mutableStateOf(false) }
    var iconSize by remember { mutableStateOf(10) }
    var holeSize by remember { mutableStateOf(0) }
    var spaceInDegrees by remember { mutableStateOf(0) }
    Row {
        Column {
            Button({ enableIcons = !enableIcons }) {
                Text(
                    if (enableIcons) {
                        "disable icons"
                    } else {
                        "enable icons"
                    }
                )
            }
            Button({ iconSize += 1 }) {
                Text("increase iconSize")
            }
            Button({ holeSize += 1 }) {
                Text("increase hole size")
            }
            Button({ spaceInDegrees += 1 }) {
                Text("increase space between sectors")
            }
        }

        Box(Modifier.fillMaxSize()) {
            val icon = if (enableIcons) {
                IconProvider.icon
            } else {
                null
            }
            PieChart(
                holeSize = Percent(holeSize),
                sectors = listOf(
                    PieChartSector(4, Color(64, 89, 128), icon, Percent(iconSize)),
                    PieChartSector(x, Color(123, 200, 123), icon, Percent(iconSize)) {
                        x += 1
                    },
                    PieChartSector(10, Color(78, 67, 138), icon, Percent(iconSize)),
                    PieChartSector(3, Color(22, 105, 240), icon, Percent(iconSize)),
                    PieChartSector(3, Color(242, 172, 82), icon, Percent(iconSize)),
                    PieChartSector(3, Color(189, 100, 36), icon, Percent(iconSize)),
                ),
                spaceBetweenSectors = Degrees(spaceInDegrees),
                animationSpec = tween(5500),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

private object IconProvider {
    val icon: Painter get() = loadSvgPainter(this.javaClass.getResourceAsStream("image.svg"), Density(1f))
}
