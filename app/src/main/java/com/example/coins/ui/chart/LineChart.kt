package com.example.coins.ui.chart

import android.graphics.LinearGradient
import android.graphics.drawable.GradientDrawable
import android.view.MotionEvent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.ChartTouchListener
import commas.android.app.core.ui.components.compose.linechart.data.ColorDistribution
import commas.android.app.core.ui.components.compose.linechart.data.LineColorType
import commas.android.app.core.ui.components.compose.linechart.data.Orientation
import commas.android.app.core.ui.components.compose.linechart.data.SelectedData

private const val LINE_WIDTH: Float = 1.5f

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    chartData: ChartData,
    lineColor: List<Color> = defaultLineColors,
    orientation: Orientation = Orientation.Horizontal,
    lineColorDistribution: ColorDistribution = ColorDistribution.Uneven(defaultColorPosition),
    filledBackgroundGradientColor: List<Color>? = defaultGradientColor,
    chartLineEnabled: Boolean = false,
    gradientEnabled: Boolean = true,
    onValueSelected: ((SelectedData) -> Unit)? = null,
    onDragEnd: (() -> Unit)? = null,
    chartOnFullVerticalSpace: Boolean = false,
) {
    var heightPx by remember { mutableStateOf(0f) }
    var widthPx by remember { mutableStateOf(0f) }

    LineChart(
        modifier = modifier
            .onGloballyPositioned {
                heightPx = it.size.height.toFloat()
                widthPx = it.size.width.toFloat()
            },
        chartData = chartData,
        lineColorType = LineColorType.Gradient(
            colors = lineColor,
            colorDistribution = lineColorDistribution,
            orientation = orientation,
            heightPx = heightPx,
            widthPx = widthPx,
        ),
        filledBackgroundGradientColor = filledBackgroundGradientColor,
        chartLineEnabled = chartLineEnabled,
        gradientEnabled = gradientEnabled,
        onValueSelected = onValueSelected,
        onDragEnd = onDragEnd,
        chartOnFullVerticalSpace = chartOnFullVerticalSpace,
    )
}

/**
 * @param onValueSelected if set, on user drag value is sent, highlights value using cross,
 * hides highlights on drag gesture ends.
 * if not set, all touches are disabled.
 * @param chartOnFullVerticalSpace if true, then chart line touch top and bottom bound of the view.
 * if false, then there are 10% space on top and bottom between view bound and chart line.
 */
@Composable
private fun LineChart(
    modifier: Modifier = Modifier,
    chartData: ChartData,
    lineColorType: LineColorType,
    filledBackgroundGradientColor: List<Color>? = null,
    chartLineEnabled: Boolean = false,
    gradientEnabled: Boolean = true,
    onValueSelected: ((SelectedData) -> Unit)? = null,
    onDragEnd: (() -> Unit)? = null,
    chartOnFullVerticalSpace: Boolean = false,
) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context -> LineChart(context) },
        update = { lineChart ->
            val entries = chartData.entries.mapIndexed { index, entry ->
                Entry(index.toFloat(), entry.value.toFloat(), entry.data)
            }
            val dataSet = LineDataSet(entries, "").apply {
                setupLine(chartLineEnabled, lineColorType, lineChart)
                setupFilledBackground(gradientEnabled, filledBackgroundGradientColor)

                setDrawValues(false)
                setDrawCircles(false)

                mode = LineDataSet.Mode.HORIZONTAL_BEZIER
                cubicIntensity = 0.5f

                highLightColor = defaultLineColor.toArgb()
                setDrawHorizontalHighlightIndicator(false)
            }

            val lineData = LineData(dataSet)

            if (chartOnFullVerticalSpace) {
                lineChart.axisLeft.spaceTop = 0f
                lineChart.axisLeft.spaceBottom = 1f
            }
            lineChart.axisLeft.setDrawGridLines(false)
            lineChart.axisLeft.setDrawAxisLine(false)
            lineChart.axisLeft.setDrawLabels(false)

            lineChart.axisRight.setDrawGridLines(false)
            lineChart.axisRight.setDrawAxisLine(false)
            lineChart.axisRight.setDrawLabels(false)

            lineChart.xAxis.setDrawGridLines(false)
            lineChart.xAxis.setDrawAxisLine(false)
            lineChart.xAxis.setDrawLabels(false)

            lineChart.setDrawBorders(false)
            lineChart.setDrawGridBackground(false)

            lineChart.description.isEnabled = false
            lineChart.legend.isEnabled = false

            lineChart.setViewPortOffsets(0f, 0f, 0f, 0f)

            if (onValueSelected == null) {
                lineChart.setTouchEnabled(false)
            } else {
                lineChart.disableScroll()
                lineChart.isHighlightPerDragEnabled = true
                lineChart.isHighlightPerTapEnabled = false
                lineChart.setScaleEnabled(false)
                lineChart.setOnChartValueSelectedListener(object :
                    DefaultOnChartValueSelectedListener {
                    override fun onValueSelected(e: Entry?, h: Highlight?) {
                        onValueSelected(SelectedData(e?.y, e?.data))
                    }
                })
                lineChart.onChartGestureListener = object : DefaultOnChartGestureListener {
                    override fun onChartGestureEnd(
                        me: MotionEvent?,
                        lastPerformedGesture: ChartTouchListener.ChartGesture?
                    ) {
                        lineChart.highlightValues(null)
                        onDragEnd?.invoke()
                    }
                }
            }

            lineChart.data = lineData
            lineChart.invalidate()
        }
    )
}

private fun LineDataSet.setupLine(
    chartLineEnabled: Boolean,
    lineColorType: LineColorType,
    lineChart: LineChart,
) {
    lineWidth = LINE_WIDTH
    if (chartLineEnabled) {
        when (lineColorType) {
            is LineColorType.Gradient -> {
                val colors: IntArray = lineColorType.colors.map { it.toArgb() }.toIntArray()
                require(colors.size > 1) { "Using Gradient with ${colors.size} colors makes no sense. Use Plain type for single color." }
                val positions = when (lineColorType.colorDistribution) {
                    is ColorDistribution.Evenly -> {
                        val step = 1f / (colors.size - 1)
                        List(lineColorType.colors.size) { index -> (index * step) }.toFloatArray()
                    }
                    is ColorDistribution.Uneven -> {
                        require(lineColorType.colorDistribution.colorPositions.size == colors.size) {
                            "Chart, setupLine. Colors and positions of colors must have same size."
                        }
                        lineColorType.colorDistribution.colorPositions.toFloatArray()
                    }
                }
                val (x1, y1) = when (lineColorType.orientation) {
                    is Orientation.Horizontal -> lineColorType.widthPx to 0f
                    is Orientation.Vertical -> 0f to lineColorType.heightPx
                }

                val gradient = LinearGradient(
                    0f, 0f, x1, y1,
                    colors, positions,
                    android.graphics.Shader.TileMode.CLAMP,
                )
                lineChart.renderer.paintRender.shader = gradient
            }
            is LineColorType.Plain -> {
                color = lineColorType.color.toArgb()
            }
        }
    } else {
        enableDashedLine(0f, 1f, 0f)
    }
}

private fun LineDataSet.setupFilledBackground(
    gradientEnabled: Boolean,
    gradientColor: List<Color>? = null,
) {
    if (gradientEnabled && gradientColor != null) {
        setDrawFilled(true)
        fillDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            gradientColor.map(Color::toArgb).toIntArray()
        )
    }
}

private val defaultLineColor = Color(0, 122, 255).copy(alpha = 0.6f)

private val defaultLineColors =
    listOf(
        Color(235, 244, 255),
        Color(0, 122, 255).copy(alpha = 0.6f),
        Color(235, 244, 255),
    )

private val defaultGradientColor =
    listOf(
        Color(0, 122, 255).copy(alpha = 0.15f),
        Color(255, 255, 255)
    )

private val defaultColorPosition =  listOf(0f, 0.6f, 1f)
