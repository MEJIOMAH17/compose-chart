package com.github.mejiomah17.compose.chart.pie

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.github.mejiomah17.compose.chart.Degrees
import java.util.concurrent.atomic.AtomicReference
import kotlin.math.absoluteValue
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
public fun PieChart(
    holeSize: Percent,
    sectors: List<PieChartSector>,
    spaceBetweenSectors: Degrees = Degrees(1),
    animationSpec: AnimationSpec<Float> = tween(0),
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        val size = min(constraints.maxWidth, constraints.maxHeight)
        BoxWithConstraints(modifier = Modifier.width(size.dp / 2).height(size.dp / 2)) {
            val totalAngle = remember { Animatable(0f) }
            LaunchedEffect(Unit) {
                totalAngle.animateTo(360f, animationSpec = animationSpec)
            }
            val sizeInPx = min(constraints.maxHeight, constraints.maxWidth)
            val chart = DrawableChart(
                sizeInPx = sizeInPx,
                holeSize = holeSize,
                totalAngle = totalAngle,
                spaceBetweenSectors = spaceBetweenSectors,
                sectors = sectors
            )
            // Modifier capture variable only once. After recomposition detectTapGestures works with first version of chart
            // for fixing that I use remembered AtomicReference
            val chartReference by remember { mutableStateOf(AtomicReference(chart)) }
            chartReference.set(chart)
            Canvas(
                modifier = Modifier.fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { tapOffset ->
                                chartReference.get().determineSector(tapOffset)?.onClick?.invoke()
                            }
                        )
                    }
            ) {
                chart.draw(this)
            }
        }
    }
}

private class DrawableChart(
    sizeInPx: Int,
    holeSize: Percent,
    private val totalAngle: Animatable<Float, AnimationVector1D>,
    spaceBetweenSectors: Degrees,
    sectors: List<PieChartSector>,
) {
    private val sectorSizeInPx: Float = (Percent.ONE_HUNDRED - holeSize) * (sizeInPx.toFloat() / 2)
    private val externalDiameter = sizeInPx.toFloat()
    private val externalRadius = externalDiameter / 2
    private val internalDiameter = holeSize * sizeInPx.toFloat()
    private val internalRadius = internalDiameter / 2
    private val center = Offset(externalRadius, externalRadius)
    private val distanceFromChartCenterToSectorCenter = (externalRadius + internalRadius) / 2
    private val holeSizeInPx = (sizeInPx - sectorSizeInPx)
    private val adjustedSpaceBetweenSectors = spaceBetweenSectors.value * (totalAngle.value / 360f)
    private val totalWeight = sectors.sumOf { it.weight }
    private val drawableSectors: List<DrawableSector>

    init {
        var cur = -90f
        drawableSectors = sectors.map { sector ->
            val sweep =
                (((sector.weight / totalWeight) * totalAngle.value) - adjustedSpaceBetweenSectors).toFloat()
            val sectorForDraw = DrawableSector(
                source = sector,
                startAngleForCanvas = cur,
                sweep = sweep,
                iconAlpha = iconAlpha(totalAngle)
            )
            cur += sweep
            cur += adjustedSpaceBetweenSectors
            sectorForDraw
        }
    }

    fun draw(scope: DrawScope) {
        drawableSectors.forEach { sector ->
            sector.drawArc(scope)
        }
        drawableSectors.forEach { sector ->
            sector.drawIcon(scope)
        }
    }

    fun determineSector(
        point: Offset,
    ): PieChartSector? {
        if (inDonut(point)) {
            val center = Offset(externalDiameter / 2, externalDiameter / 2)
            val angle = (center - point).angle()
            val lastSectorBefore: Int = drawableSectors.indexOfLast {
                it.startAngle <= angle && it.endAngle >= angle
            }
            return drawableSectors.getOrElse(lastSectorBefore) { null }?.source
        } else {
            return null
        }
    }

    private fun Offset.angle(): Double {
        val rad = atan2(x, y)
        return if (rad < 0) {
            rad.absoluteValue * 180 / Math.PI
        } else {
            360 - (rad * 180 / Math.PI)
        }
    }

    private fun inDonut(point: Offset): Boolean {
        val offsetFromCenter = center - point
        val distance = offsetFromCenter.getDistance()
        return distance < externalRadius && distance > internalRadius
    }

    inner class DrawableSector(
        val source: PieChartSector,
        val startAngleForCanvas: Float,
        val sweep: Float,
        val iconAlpha: Float,
    ) {
        val startAngle = startAngleForCanvas + 90
        val endAngle = startAngle + sweep
        val directionToTheCenterOfSector = startAngle + (sweep / 2)

        fun drawArc(scope: DrawScope) = scope.run {
            drawArc(
                color = source.color,
                startAngle = startAngleForCanvas,
                sweepAngle = sweep,
                useCenter = false,
                size = Size(holeSizeInPx, holeSizeInPx),
                style = Stroke(width = sectorSizeInPx),
                topLeft = Offset(sectorSizeInPx / 2, sectorSizeInPx / 2)
            )
        }

        fun drawIcon(scope: DrawScope) = scope.run {
            val angleInRad = (directionToTheCenterOfSector * Math.PI / 180).toFloat()
            val iconSize = source.iconSizeFromSector * (externalRadius - internalRadius)
            if (source.icon != null) {
                withTransform(
                    transformBlock = {
                        this.translate(
                            left = this@DrawableChart.center.x - (iconSize / 2) + (sin(angleInRad) * distanceFromChartCenterToSectorCenter),
                            top = this@DrawableChart.center.y - (iconSize / 2) - (cos(angleInRad) * distanceFromChartCenterToSectorCenter)
                        )
                    }
                ) {
                    with(source.icon) {
                        draw(Size(iconSize, iconSize), alpha = iconAlpha)
                    }
                }
            }
        }
    }
}

private fun iconAlpha(totalAngle: Animatable<Float, AnimationVector1D>): Float {
    return if (totalAngle.value > 90) {
        (totalAngle.value - 90) / (360 - 90)
    } else {
        0f
    }
}
