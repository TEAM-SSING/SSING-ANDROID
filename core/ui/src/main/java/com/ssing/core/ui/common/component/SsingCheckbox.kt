package com.ssing.core.ui.common.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.R
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.designsystem.theme.White

/**
 * 체크박스 컴포넌트입니다.
 *
 * @param checked 현재 체크 여부.
 * @param onCheckedChange 체크 상태가 변경될 때 호출되는 콜백.
 * @param modifier Composable에 적용할 Modifier.
 */
@Composable
fun SsingCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (checked) SSINGTheme.colors.primaryNormal else SSINGTheme.colors.backgroundNormal,
        animationSpec = tween(100),
    )

    val borderColor by animateColorAsState(
        targetValue = if (checked) SSINGTheme.colors.primaryAlternative else SSINGTheme.colors.borderNormal,
        animationSpec = tween(100),
    )

    val shape = RoundedCornerShape(6.dp)

    Box(
        modifier = modifier
            .size(24.dp)
            .background(
                color = backgroundColor,
                shape = shape,
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shape,
            )
            .toggleable(
                role = Role.Checkbox,
                value = checked,
                onValueChange = onCheckedChange,
            ),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedVisibility(
            visible = checked,
            enter = fadeIn(tween(100)),
            exit = fadeOut(tween(100)),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_check),
                contentDescription = null,
                tint = White,
            )
        }
    }
}

@Preview
@Composable
private fun SsingCheckboxPreview() {
    SSINGTheme {
        var checked by remember { mutableStateOf(false) }

        SsingCheckbox(
            checked = checked,
            onCheckedChange = { checked = it }
        )
    }
}