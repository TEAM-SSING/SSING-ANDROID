package com.ssing.core.ui.extension

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.compose.ui.graphics.shadow.Shadow

/**
 * 리플 효과 없이 클릭 가능하게 만드는 Modifier
 *
 * Material3의 기본 리플 애니메이션을 제거합니다.
 *
 * @param onClick 클릭 시 실행될 콜백
 */

@Composable
inline fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    crossinline onClick: () -> Unit,
): Modifier =
    composed {
        this.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            onClick = { onClick() },
            enabled = enabled,
        )
    }

/**
 * 포커스된 컴포저블을 키보드에 가리지 않도록 컴포넌트 영역 안으로 이동시키는 함수
 * @param isFocused  bring-into-view 동작 실행 여부를 결정하는 상태 값
 * @param delayMillis  키보드 표시 이후 동작 실행까지 대기할 지연 시간 값(ms)
 */
fun Modifier.bringIntoViewOnFocus(
    isFocused: Boolean,
    extraBottom: Dp = 0.dp,
    delayMillis: Long = 200L,
): Modifier =
    composed {
        val bringIntoViewRequester = remember { BringIntoViewRequester() }
        var layoutCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }

        val density = LocalDensity.current
        val imeBottom = WindowInsets.ime.getBottom(density)
        val extraBottomPx = with(density) { extraBottom.toPx() }

        LaunchedEffect(isFocused, imeBottom) {
            if (!isFocused || imeBottom <= 0) return@LaunchedEffect
            val coords = layoutCoordinates ?: return@LaunchedEffect

            delay(delayMillis)

            val original = coords.boundsInParent()
            val targetRect =
                Rect(
                    left = original.left,
                    top = original.top,
                    right = original.right,
                    bottom = original.bottom + extraBottomPx,
                )

            bringIntoViewRequester.bringIntoView(targetRect)
        }

        this
            .bringIntoViewRequester(bringIntoViewRequester)
            .onGloballyPositioned { layoutCoordinates = it }
    }

/**
 * 화면의 빈 영역을 터치했을 때 포커스를 해제하는 함수
 * @param focusManager 현재 화면의 [FocusManager] 객체
 * @param doOnClear 포커스 해제 시 추가로 실행할 동작
 */
fun Modifier.clearFocus(
    focusManager: FocusManager,
    doOnClear: () -> Unit = {},
): Modifier =
    this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            doOnClear()
            focusManager.clearFocus()
        })
    }

/**
 * 텍스트 입력 시 커서 위치로 인한 자동 스크롤을 방지
 * BasicTextField의 기본 bringIntoView 동작을 차단
 */
@OptIn(ExperimentalFoundationApi::class)
fun Modifier.preventCursorScroll(): Modifier =
    this.then(
        object : androidx.compose.ui.layout.LayoutModifier {
            override fun androidx.compose.ui.layout.MeasureScope.measure(
                measurable: androidx.compose.ui.layout.Measurable,
                constraints: androidx.compose.ui.unit.Constraints,
            ): androidx.compose.ui.layout.MeasureResult {
                val placeable = measurable.measure(constraints)
                return layout(placeable.width, placeable.height) {
                    placeable.place(0, 0)
                }
            }
        },
    )

/**
 * 코너 둥근 배경 + 보더를 한 번에 설정
 *
 * @param shape Shape
 * @param backgroundColor 배경 색상
 * @param borderColor 보더 색상
 * @param borderWidth dp 단위 보더 굵기
 * @return
 */
fun Modifier.roundedBackgroundWithBorder(
    shape: Shape,
    backgroundColor: Color,
    borderColor: Color = Color.Transparent,
    borderWidth: Dp = 0.dp,
): Modifier {
    return this
        .clip(shape)
        .background(backgroundColor)
        .border(
            width = borderWidth,
            color = borderColor,
            shape = shape,
        )
}

/**
 * 피그마 드롭섀도우와 동일하게 구현하기 위한 확장함수
 *
 * @param shape 적용할 컴포저블의 shape
 * @param dpOffset 피그마 x, y 값을 DpOffset으로 작성
 * @param blur 피그마 blur 값을 Dp 단위로 작성
 * @param spread 피그마 shape 값을 Dp 단위로 작성
 * @param color 그림자 색상
 * @return
 */
fun Modifier.figmaDropShadow(
    shape: Shape,
    dpOffset: DpOffset,
    blur: Dp,
    spread: Dp,
    color: Color,
): Modifier {
    return this.dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = blur,
            color = color,
            offset = dpOffset,
            spread = spread,
        ),
    )
}
