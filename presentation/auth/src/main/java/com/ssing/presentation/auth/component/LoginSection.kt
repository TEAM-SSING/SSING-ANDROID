package com.ssing.presentation.auth.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.KakaoButton

@Composable
internal fun LoginSection(
    onKakaoClick: () -> Unit,
    onConditionClick: () -> Unit,
    onPersonalInfoClick: () -> Unit,
    onServiceCenterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
            )

            InfoText(
                text = "개인정보 처리방침",
                onClick = onPersonalInfoClick,
            )

            InfoText(
                text = "고객센터",
                onClick = onServiceCenterClick,
            )
        }
    }
}
