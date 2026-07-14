package com.ssing.presentation.instructorlessondetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.designsystem.theme.SSINGTheme

@Composable
fun LessonDetailSsingButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SsingButton(
        text = text,
        onClick = onClick,
        style = SsingButtonStyle.BLUE,
        modifier = modifier
            .fillMaxWidth()
            .background(SSINGTheme.colors.backgroundNormal)
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
    )
}