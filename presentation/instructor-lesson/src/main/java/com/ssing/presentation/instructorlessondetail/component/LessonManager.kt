package com.ssing.presentation.instructorlessondetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle

@Composable
fun LessonManager(
    primaryText: String,
    onPrimaryClick: () -> Unit,
    primaryStyle: SsingButtonStyle,
    secondaryText: String,
    onSecondaryClick: () -> Unit,
    secondaryStyle: SsingButtonStyle,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(24.dp))
        SectionTitle(text = "강습 관리")
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            SsingButton(
                text = primaryText,
                onClick = onPrimaryClick,
                style = primaryStyle,
                modifier = Modifier.weight(1f),
            )

            SsingButton(
                text = secondaryText,
                onClick = onSecondaryClick,
                style = secondaryStyle,
                modifier = Modifier.weight(1f),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}