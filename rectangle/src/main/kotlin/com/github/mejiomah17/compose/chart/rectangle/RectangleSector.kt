package com.github.mejiomah17.compose.chart.rectangle

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

public class RectangleSector(
    internal val color: Color,
    internal val weight: Float,
    internal val content: @Composable () -> Unit = {},
    internal val onClick: () -> Unit = {}
) {
}