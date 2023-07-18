package commas.android.app.core.ui.components.compose.linechart.data

import androidx.compose.ui.graphics.Color

sealed interface LineColorType {
    data class Plain(val color: Color) : LineColorType
    data class Gradient(
        val colors: List<Color>,
        val colorDistribution: ColorDistribution,
        val orientation: Orientation,
        val heightPx: Float,
        val widthPx: Float,
    ) : LineColorType
}

sealed interface Orientation {
    object Vertical : Orientation
    object Horizontal : Orientation
}

sealed interface ColorDistribution {
    object Evenly : ColorDistribution
    data class Uneven(val colorPositions: List<Float>) : ColorDistribution
}
