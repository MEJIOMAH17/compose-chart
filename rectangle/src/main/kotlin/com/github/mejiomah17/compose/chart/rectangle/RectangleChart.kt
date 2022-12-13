package com.github.mejiomah17.compose.chart.rectangle

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.onClick
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import org.jetbrains.skia.Font
import org.jetbrains.skia.Paint
import org.jetbrains.skia.TextLine
import org.jetbrains.skia.Typeface
import java.util.concurrent.atomic.AtomicReference

@Composable
public fun RectangleChart(
    sectors: List<RectangleSector>,
    modifier: Modifier = Modifier,
    animationSpec: AnimationSpec<Float> = tween(0),
    minSectorHeight: Dp = 0.dp,
) {
    BoxWithConstraints(modifier = modifier) {
        val widthFactor = remember { Animatable(0f) }
        LaunchedEffect(Unit) {
            widthFactor.animateTo(1f, animationSpec = animationSpec)
        }
        RectangleChart(
            sectors,
            widthFactor.value,
            minSectorHeight
        )
    }
}

@Composable
public fun RectangleChart(
    sectors: List<RectangleSector>,
    height: Float,
    minSectorHeight: Dp
) {
    val spaceRequiredForMins = minSectorHeight * sectors.size
    BoxWithConstraints(Modifier.fillMaxHeight(height)) {
        val freeSpace = this.maxHeight - spaceRequiredForMins
        val totalWeight = sectors.sumOf { it.weight.toDouble() }
        val sectorHeights = sectors.associateWith {
            minSectorHeight + (freeSpace * (it.weight / totalWeight).toFloat())
        }
        println(sectorHeights)
        Column() {
            sectors.sortedBy { it.weight }.forEach { sector ->
                Row(modifier = Modifier
                    .background(sector.color)
                    .fillMaxWidth()
                    .height(sectorHeights[sector]!!)
                    // .weight(sector.weight)
                    .clickable {
                        sector.onClick()
                    }) {
                    sector.content()
                }
            }
        }

    }
}

@Preview
@Composable
private fun Preview() {
    RectangleChart(
        sectors = listOf(
            RectangleSector(Color.Black, 2f) {
                println("black")
            },
            RectangleSector(Color.White, 1f, { Text("adsf") }) {
                println("blue")
            },
            RectangleSector(Color.Yellow, 100f, { Text("adsf") }) {
                println("yellow")
            },
            RectangleSector(Color.Green, 100f, { Text("adsf") }) {
                println("green")
            },
        ),
        minSectorHeight = 30.dp,
        height = 1f
    )
}
