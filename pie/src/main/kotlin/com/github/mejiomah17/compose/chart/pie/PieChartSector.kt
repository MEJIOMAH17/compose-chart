package com.github.mejiomah17.compose.chart.pie

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

public class PieChartSector(
    internal val weight: Double,
    internal val color: Color,
    internal val icon: Painter? = null,
    internal val iconSizeFromSector: Percent = Percent(40),
    internal val onClick: () -> Unit = {},
) {
    public constructor(
        weight: Int,
        color: Color,
        icon: Painter? = null,
        iconSizeFromSector: Percent = Percent(40),
        onClick: () -> Unit = {}
    ) : this(
        weight = weight.toDouble(),
        color = color,
        icon = icon,
        iconSizeFromSector = iconSizeFromSector,
        onClick = onClick
    )
}
