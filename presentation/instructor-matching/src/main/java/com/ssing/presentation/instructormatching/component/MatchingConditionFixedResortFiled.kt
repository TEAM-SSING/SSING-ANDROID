package com.ssing.presentation.instructormatching.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.roundedBackgroundWithBorder


@Composable
fun MatchingConditionFixedResortField(
    resortName: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .roundedBackgroundWithBorder(
                shape = RoundedCornerShape(12.dp),
                backgroundColor = SSINGTheme.colors.backgroundNormal,
                borderColor = SSINGTheme.colors.borderAlternative,
                borderWidth = 1.dp,
            )
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = resortName,
            style = SSINGTheme.typography.caption.sb14,
            color = SSINGTheme.colors.textDisabled,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchingConditionFixedResortFieldPreview() {
    SSINGTheme {
        MatchingConditionFixedResortField(resortName = "하이원 리조트")
    }
}
