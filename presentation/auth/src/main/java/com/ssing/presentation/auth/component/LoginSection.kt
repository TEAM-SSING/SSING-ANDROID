package com.ssing.presentation.auth.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.KakaoButton
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.noRippleClickable

@Composable
internal fun LoginSection(
    onKakaoClick: () -> Unit,
    onConditionClick: () -> Unit,
    onPersonalInfoClick: () -> Unit,
    onServiceCenterClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = SSINGTheme.colors.textAlternative,
) {
    Column(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        KakaoButton(
            onClick = onKakaoClick,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            InfoText(
                text = "이용 약관",
                onClick = onConditionClick,
                textColor = textColor,
            )

            InfoText(
                text = "개인정보 처리방침",
                onClick = onPersonalInfoClick,
                textColor = textColor,
            )

            InfoText(
                text = "고객센터",
                onClick = onServiceCenterClick,
                textColor = textColor,
            )
        }
    }
}

@Composable
private fun InfoText(
    text: String,
    onClick: () -> Unit,
    textColor: Color,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = SSINGTheme.typography.caption.md12,
        color = textColor,
        modifier = modifier.noRippleClickable(
            onClick = onClick,
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun LoginSectionPreview() {
    SSINGTheme {
        LoginSection(
            onKakaoClick = {},
            onConditionClick = {},
            onPersonalInfoClick = {},
            onServiceCenterClick = {},
            textColor = SSINGTheme.colors.primaryAlternative,
        )
    }
}

