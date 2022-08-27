package com.github.mejiomah17.compose.chart.pie

public class Percent(public val value: Float) {
    init {
        require(value >= 0) {
            "Value must be non-negative"
        }
        require(value <= 100) {
            "Value must be lower or equal 100"
        }
    }

    public constructor(value: Int) : this(value.toFloat())

    public companion object {
        public val ONE_HUNDRED: Percent = Percent(100f)
    }

    public operator fun minus(other: Percent): Percent {
        return Percent(this.value - other.value)
    }

    public operator fun times(value: Float): Float {
        return (value * this.value) / 100
    }
}
