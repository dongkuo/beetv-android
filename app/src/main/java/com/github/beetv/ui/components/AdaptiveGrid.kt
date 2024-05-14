package com.github.beetv.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AdaptiveGrid(
    modifier: Modifier = Modifier,
    minWidth: Dp,
    horizontalSpace: Dp = 0.dp,
    verticalSpace: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        if (measurables.isEmpty()) {
            return@Layout layout(0, 0) {}
        }
        // w * n + s * (n - 1) = l
        // => (w + s) * n - s = l
        // => n = (s + l) / (s + w)
        val width = constraints.maxWidth
        val hSpaceWidth = horizontalSpace.roundToPx()
        val vSpaceHeight = verticalSpace.roundToPx()
        val itemMinWidth = minWidth.roundToPx()
        val totalColNum = (hSpaceWidth + width) / (hSpaceWidth + itemMinWidth)
        val remainderSpace = width - itemMinWidth * totalColNum - hSpaceWidth * (totalColNum - 1)
        val itemWidth = itemMinWidth + (remainderSpace / totalColNum)

        var height = 0
        var maxRowHeight = 0
        val placeables = measurables.mapIndexed() { i, measurable ->
            val itemConstraints = constraints.copy(minWidth = itemWidth, maxWidth = itemWidth)
            val placeable = measurable.measure(itemConstraints)
            val isLastInGrid = (i + 1) == measurables.size
            val isLastInRow = isLastInGrid || (i + 1) % totalColNum == 0
            maxRowHeight = maxRowHeight.coerceAtLeast(placeable.height)
            if (isLastInRow) {
                height += maxRowHeight
                maxRowHeight = 0
            }
            if (isLastInRow && !isLastInGrid) {
                height += vSpaceHeight
            }
            placeable
        }

        layout(width, height) {
            var xPosition = 0
            var yPosition = 0
            placeables.forEachIndexed() { i, placeable ->
                placeable.placeRelative(x = xPosition, y = yPosition)
                val isNeedNewLine = (i + 1) % totalColNum == 0
                if (isNeedNewLine) {
                    xPosition = 0
                    yPosition += placeables[i].height + vSpaceHeight
                } else {
                    xPosition += placeable.width + hSpaceWidth
                }
            }
        }
    }
}