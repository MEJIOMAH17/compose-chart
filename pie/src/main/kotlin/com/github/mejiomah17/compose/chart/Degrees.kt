package com.github.mejiomah17.compose.chart

public class Degrees(public val value: Float) {
    public constructor(value: Int) : this(value.toFloat())

    init {
        require(value <= 360) {
            "value must be lower than 360"
        }
        require(value >= 0) {
            "value must be non negative"
        }
    }
}
