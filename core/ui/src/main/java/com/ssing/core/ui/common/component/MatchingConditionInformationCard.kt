package com.ssing.core.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.theme.SSINGTheme

@Composable
fun MatchingConditionInformationCard(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                color = SSINGTheme.colors.borderDisabled,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(all = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SsingCheckbox(
            isChecked = isChecked,
            onCheckedChange = onCheckedChange,
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "부츠와 장비를 착용했고 현재 스키장에 있어요",
                color = SSINGTheme.colors.textNormal,
                style = SSINGTheme.typography.caption.sb14,
            )

            Text(
                text = "허위 체크나 노쇼는 신고 및 운영 정책에 따라 제한될 수 있어요",
                color = SSINGTheme.colors.textAlternative,
                style = SSINGTheme.typography.caption.md12,
            )
        }
    }
}

private class MatchingConditionInformationCardPreviewParameter :
    PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}

@Preview
@Composable
private fun MatchingConditionInformationCardPreveiw(
    @PreviewParameter(MatchingConditionInformationCardPreviewParameter::class) initialChecked: Boolean,
) {
    var isChecked by remember { mutableStateOf(initialChecked) }
    SSINGTheme {
        MatchingConditionInformationCard(
            isChecked = isChecked,
            onCheckedChange = { isChecked = it },
            modifier = Modifier.width(328.dp)
        )
    }
}
