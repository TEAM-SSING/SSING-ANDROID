package com.ssing.presentation.instructormatching.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.SsingCheckbox
import com.ssing.core.ui.designsystem.theme.SSINGTheme

@Composable
internal fun MatchingConditionNoticeSection(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = SSINGTheme.colors.backgroundAlternative,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top,
    ) {
        SsingCheckbox(
            isChecked = isChecked,
            onCheckedChange = onCheckedChange,
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "부츠와 장비를 착용했고 현재 스키장에 있어요",
                style = SSINGTheme.typography.caption.sb14,
                color = SSINGTheme.colors.textNormal,
            )
            Text(
                text = "허위 체크나 노쇼는 신고 및 운영 정책에 따라 제한될 수 있어요",
                style = SSINGTheme.typography.caption.md12,
                color = SSINGTheme.colors.textAlternative,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingConditionNoticeSectionPreview() {
    SSINGTheme {
        var isChecked by remember { mutableStateOf(false) }
        MatchingConditionNoticeSection(
            isChecked = isChecked,
            onCheckedChange = { isChecked = it },
        )
    }
}