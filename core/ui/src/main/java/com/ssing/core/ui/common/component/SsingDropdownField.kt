package com.ssing.core.ui.common.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.ssing.core.ui.R
import com.ssing.core.ui.designsystem.theme.Blue50
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.figmaDropShadow
import com.ssing.core.ui.extension.noRippleClickable
import com.ssing.core.ui.extension.roundedBackgroundWithBorder
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * 드롭다운 필드 컴포넌트.
 * 필드 클릭 시 드롭다운 메뉴가 하단에 표시되고,
 * 드롭다운 메뉴 아이템 또는 외부 터치 시 드롭다운 메뉴가 닫힘.
 *
 * @param T 드롭다운 메뉴 아이템 타입
 * @param selectedItem 선택된 아이템 (없는 경우 null)
 * @param placeholder 선택된 아이템이 없을 때 필드 노출 문구
 * @param items 드롭다운 메뉴에 보여질 아이템 리스트
 * @param onItemClick 아이템 클릭 콜백 (선택된 아이템 호이스팅)
 * @param itemToString 아이템을 Text로 노출하기 위한 String 변환 함수
 * @param itemToKey 아이템 기반 LazyColumn key 설정하기 위한 변환 함수
 * @param modifier Composable에 적용할 Modifier
 */
@Composable
fun <T> SsingDropdownField(
    selectedItem: T?,
    placeholder: String,
    items: ImmutableList<T>,
    onItemClick: (T) -> Unit,
    itemToString: (T) -> String,
    itemToKey: (T) -> String,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    var anchorWidth by remember { mutableIntStateOf(0) }
    val expandedState = remember { MutableTransitionState(false) }
    expandedState.targetState = expanded

    Box(
        modifier = modifier,
    ) {
        SsingField(
            placeholder = placeholder,
            selectedValue = selectedItem?.let { itemToString(selectedItem) },
            expanded = expanded,
            onClick = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { anchorWidth = it.size.width },
        )

        SsingDropdownMenu(
            items = items,
            expandedState = expandedState,
            parentWidth = anchorWidth,
            onDismissRequest = { expanded = false },
            onItemClick = {
                onItemClick(it)
                expanded = false
            },
            itemToString = itemToString,
            itemToKey = itemToKey,
        )
    }
}

private const val ANIMATION_DURATION = 300

@Composable
private fun SsingField(
    placeholder: String,
    selectedValue: String?,
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val transition = updateTransition(targetState = expanded, label = "ExpandTransition")

    val rotationAngle by transition.animateFloat(
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        label = "Rotation"
    ) { isExpanded ->
        if (isExpanded) 180f else 0f
    }

    val iconColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        label = "Color"
    ) { isExpanded ->
        if (isExpanded) SSINGTheme.colors.primaryNormal else SSINGTheme.colors.textNormal
    }

    val borderColor by transition.animateColor(
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        label = "Color"
    ) { isExpanded ->
        if (isExpanded) SSINGTheme.colors.primaryNormal else SSINGTheme.colors.borderAlternative
    }

    Row(
        modifier = modifier
            .noRippleClickable(onClick = onClick)
            .roundedBackgroundWithBorder(
                shape = RoundedCornerShape(12.dp),
                backgroundColor = SSINGTheme.colors.backgroundNormal,
                borderColor = borderColor,
                borderWidth = 1.dp,
            )
            .padding(all = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        if (selectedValue != null) {
            Text(
                text = selectedValue,
                color = SSINGTheme.colors.textNormal,
                style = SSINGTheme.typography.caption.sb14,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        } else {
            Text(
                text = placeholder,
                color = SSINGTheme.colors.textDisabled,
                style = SSINGTheme.typography.caption.sb14,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_down),
            contentDescription = null,
            modifier = Modifier.rotate(rotationAngle),
            tint = iconColor,
        )
    }
}

private const val DEFAULT_MAX_HEIGHT = 207
private const val DEFAULT_ITEM_COUNT = 4

@Composable
private fun <T> SsingDropdownMenu(
    items: ImmutableList<T>,
    expandedState: MutableTransitionState<Boolean>,
    parentWidth: Int,
    onDismissRequest: () -> Unit,
    onItemClick: (T) -> Unit,
    itemToString: (T) -> String,
    itemToKey: (T) -> String,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current

    val popupPositionProvider = remember(density) {
        val offsetYPx = with(density) { 4.dp.roundToPx() }
        object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize,
            ): IntOffset {
                return IntOffset(
                    x = anchorBounds.left,
                    y = anchorBounds.bottom + offsetYPx,
                )
            }
        }
    }

    val targetCount = minOf(DEFAULT_ITEM_COUNT, items.size)
    var measureItemCount by remember { mutableIntStateOf(0) }
    var calculatedMaxHeight by remember { mutableStateOf(0.dp) }
    var isCalculationFinished by remember { mutableStateOf(false) }

    if (expandedState.currentState || expandedState.targetState) {
        Popup(
            onDismissRequest = onDismissRequest,
            popupPositionProvider = popupPositionProvider,
            properties = PopupProperties(focusable = true),
        ) {
            SsingDropdownMenuContent(
                expandedState = expandedState,
                parentWidth = parentWidth,
                maxHeight = if (isCalculationFinished) calculatedMaxHeight else DEFAULT_MAX_HEIGHT.dp,
                modifier = modifier,
            ) {
                itemsIndexed(
                    items = items,
                    key = { _, item -> itemToKey(item) }) { index, item ->
                    SsingDropdownMenuItem(
                        text = itemToString(item),
                        onClick = { onItemClick(item) },
                        isLastIndex = index == items.size - 1,
                        modifier = when (isCalculationFinished) {
                            true -> Modifier
                            else ->
                                Modifier
                                    .onGloballyPositioned { coordinates ->
                                        measureItemCount++
                                        val heightPx = coordinates.size.height
                                        val heightDp = with(density) { heightPx.toDp() }
                                        if (measureItemCount <= targetCount) {
                                            calculatedMaxHeight += heightDp
                                        }
                                        if (measureItemCount >= targetCount) {
                                            isCalculationFinished = true
                                        }
                                    }
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun SsingDropdownMenuContent(
    expandedState: MutableTransitionState<Boolean>,
    parentWidth: Int,
    maxHeight: Dp,
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit,
) {
    val density = LocalDensity.current
    val shape = RoundedCornerShape(12.dp)

    AnimatedVisibility(
        visibleState = expandedState,
        enter = expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(120, easing = LinearOutSlowInEasing),
        ) + fadeIn(animationSpec = tween(120, easing = LinearOutSlowInEasing)),
        exit = shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(75, easing = LinearOutSlowInEasing),
        ) + fadeOut(animationSpec = tween(75, easing = LinearOutSlowInEasing)),
    ) {
        Surface(
            modifier = modifier
                .width(with(density) { parentWidth.toDp() })
                .figmaDropShadow(
                    shape = shape,
                    dpOffset = DpOffset(0.dp, 2.dp),
                    blur = 10.dp,
                    spread = 0.dp,
                    color = Color(0x26000000),
                )
                .clip(shape),
        ) {
            LazyColumn(
                modifier = Modifier.height(maxHeight),
                content = content,
            )
        }
    }
}

@Composable
private fun SsingDropdownMenuItem(
    text: String,
    onClick: () -> Unit,
    isLastIndex: Boolean,
    modifier: Modifier = Modifier,
) {
    val borderColor = SSINGTheme.colors.borderDisabled
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Text(
        text = text,
        color = if (isPressed) SSINGTheme.colors.primaryNormal else SSINGTheme.colors.textNormal,
        style = SSINGTheme.typography.caption.md14,
        modifier = modifier
            .clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null,
            )
            .fillMaxWidth()
            .background(if (isPressed) Blue50 else SSINGTheme.colors.backgroundNormal)
            .then(
                if (!isLastIndex) {
                    Modifier.drawBehind {
                        val strokeWidth = 1.dp.toPx()
                        drawLine(
                            color = borderColor,
                            start = Offset(0f, size.height - strokeWidth / 2),
                            end = Offset(size.width, size.height - strokeWidth / 2),
                            strokeWidth = strokeWidth,
                        )
                    }
                } else {
                    Modifier
                },
            )
            .padding(all = 16.dp),
    )
}

@Preview(showBackground = true)
@Composable
private fun SsingDropdownFieldPreview() {
    val items = persistentListOf("하이원리조트", "휘닉스파크", "비발디파크", "웰리힐리파크", "엘리시안 강촌", "오크밸리", "알펜시아")
    var selectedItem by remember { mutableStateOf<String?>(null) }

    SSINGTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            SsingDropdownField(
                selectedItem = selectedItem,
                placeholder = "placeholder",
                items = items,
                onItemClick = { selectedItem = it },
                itemToString = { it },
                itemToKey = { it },
                modifier = Modifier.width(328.dp)
            )
        }
    }
}
