# Jetpack compose multiplatform charts

Jetpack compose charts with support android and desktop targets.

## Philosophy 

All charts
 * Work the same on desktop and android targets
 * Do not require any pixel work. All settings use shape related dimensions (Percents, Degrees, etc)
 * Uses standard compose library. No external dependencies required

## Charts
Chart usage example could be found under ```/example/target``` directories

### PieChart
 [How it looks](https://www.youtube.com/watch?v=8IdAERkgLoY&ab_channel=MarkEpshtein)

How to use:
 ``` kotlin
    PieChart(
                // size of center hole in percents from total chart size
                holeSize = Percent(holeSize),
                // list of sectors to draw
                sectors = listOf(
                    PieChartSector(
                        // weight of this sector. Size of sector at chart depends on weight/totalWeight
                        weight = 4,
                        color = Color(64, 89, 128),
                        // Jetpack compose painter. Highly recommended to use vector painters here.
                        icon = yourPainter,
                        // Size of icon in percents of sector size
                        iconSizeFromSector = Percent(40)
                    ){
                        // this code will be called on sector click
                    }
                    
                ),
                // empty space between sectors in degrees
                spaceBetweenSectors = Degrees(1),
                // Jetpack compose animationSpec
                animationSpec = tween(5500),
                modifier = Modifier.align(Alignment.Center)
     )
 ```

# Contribution
Any contribution is highly welcome. Don't shy for any pull request or issue. 