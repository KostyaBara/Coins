package commas.android.app.core.ui.components.compose.linechart.data

import java.math.BigDecimal

data class ChartData(
    val entries: List<Entry> = emptyList(),
) {
    data class Entry(
        val value: BigDecimal,
        val data: Any? = null,
    )
}

