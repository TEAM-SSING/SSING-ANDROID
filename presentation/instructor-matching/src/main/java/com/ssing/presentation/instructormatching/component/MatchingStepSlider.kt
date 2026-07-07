package com.ssing.presentation.instructormatching.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import kotlin.math.roundToInt

private val SliderTouchAreaHeight = 20.dp
private val TrackHeight = 4.dp
private val MarkerWidth = 6.dp
private val MarkerHeight = 12.dp
private val ThumbSize = 20.dp

@Composable
internal fun MatchingStepSlider(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    valueRange: IntRange = 1..5,
    label: (Int) -> String = { "${it}명" },
) {
    require(valueRange.count() >= 2) { "valueRange는 2개 이상의 스텝이 필요합니다." }

    val stepCount = valueRange.count()
    val coercedValue = value.coerceIn(valueRange)
    val fraction = (coercedValue - valueRange.first).toFloat() / (stepCount - 1)
    val animatedFraction by animateFloatAsState(
        targetValue = fraction,
        label = "thumbFraction",
    )

    val primaryColor = SSINGTheme.colors.primaryNormal
    val inactiveColor = SSINGTheme.colors.borderDisabled

    val currentValue by rememberUpdatedState(coercedValue)
    val currentOnValueChange by rememberUpdatedState(onValueChange)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(SliderTouchAreaHeight)
                .pointerInput(valueRange) {
                    fun stepFrom(x: Float): Int {
                        val trackOffset = MarkerWidth.toPx() / 2
                        val trackWidth = (size.width - MarkerWidth.toPx()).coerceAtLeast(1f)
                        val rawFraction = ((x - trackOffset) / trackWidth).coerceIn(0f, 1f)
                        return valueRange.first + (rawFraction * (stepCount - 1)).roundToInt()
                    }

                    awaitEachGesture {
                        val down = awaitFirstDown()
                        down.consume()
                        stepFrom(down.position.x)
                            .takeIf { it != currentValue }
                            ?.let(currentOnValueChange)

                        drag(down.id) { change ->
                            stepFrom(change.position.x)
                                .takeIf { it != currentValue }
                                ?.let(currentOnValueChange)
                            change.consume()
                        }
                    }
                }
                .drawBehind {
                    val thumbRadius = ThumbSize.toPx() / 2
                    val trackOffset = MarkerWidth.toPx() / 2
                    val trackWidth = size.width - MarkerWidth.toPx()
                    val centerY = size.height / 2
                    val thumbX = trackOffset + trackWidth * animatedFraction
                    val markerCorner = CornerRadius(MarkerWidth.toPx())
                    val trackCorner = CornerRadius(TrackHeight.toPx())

                    drawRoundRect(
                        color = inactiveColor,
                        topLeft = Offset(trackOffset, centerY - TrackHeight.toPx() / 2),
                        size = Size(trackWidth, TrackHeight.toPx()),
                        cornerRadius = trackCorner,
                    )
                    drawRoundRect(
                        color = primaryColor,
                        topLeft = Offset(trackOffset, centerY - TrackHeight.toPx() / 2),
                        size = Size(thumbX - trackOffset, TrackHeight.toPx()),
                        cornerRadius = trackCorner,
                    )
                    repeat(stepCount) { index ->
                        val markerX = trackOffset + if (stepCount > 1) {
                            trackWidth * index / (stepCount - 1)
                        } else {
                            0f
                        }
                        drawRoundRect(
                            color = if (markerX <= thumbX) primaryColor else inactiveColor,
                            topLeft = Offset(
                                x = markerX - MarkerWidth.toPx() / 2,
                                y = centerY - MarkerHeight.toPx() / 2,
                            ),
                            size = Size(MarkerWidth.toPx(), MarkerHeight.toPx()),
                            cornerRadius = markerCorner,
                        )
                    }
                    drawCircle(
                        color = primaryColor,
                        radius = thumbRadius,
                        center = Offset(thumbX, centerY),
                    )
                },
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = label(valueRange.first),
                style = SSINGTheme.typography.caption.sb12,
                color = SSINGTheme.colors.textAlternative,
            )
            Text(
                text = label(valueRange.last),
                style = SSINGTheme.typography.caption.sb12,
                color = SSINGTheme.colors.textAlternative,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SsingStepSliderPreview() {
    SSINGTheme {
        var value by remember { mutableIntStateOf(3) }

        MatchingStepSlider(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.padding(20.dp),
        )
    }
}
