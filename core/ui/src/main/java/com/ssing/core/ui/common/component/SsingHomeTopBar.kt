package com.ssing.core.ui.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.R
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.noRippleClickable

/**
 * 홈 전용 상단바 컴포넌트입니다.
 *
 * 앱별로 로고가 다르므로 [logo] 슬롯으로 주입합니다.
 * @param logo 좌측에 표시할 로고 컴포저블입니다.
 * @param onNotificationClick 알림 버튼 클릭 콜백입니다.
 * @param modifier
 */
@Composable
fun SsingHomeTopBar(
    logo: @Composable () -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = SSINGTheme.colors.backgroundNormal)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        logo()
        Icon(
            painter = painterResource(R.drawable.ic_bell),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .noRippleClickable(onClick = onNotificationClick)
                .padding(vertical = 14.dp)
                .size(24.dp)
        )
    }
}

private class SsingHomeTopBarPreviewProvider : PreviewParameterProvider<Int> {
    override val values = sequenceOf(
        R.drawable.img_consumer_logo,
        R.drawable.img_instructor_logo,
    )
}

@Preview(showBackground = true)
@Composable
private fun SsingHomeTopBarPreview(
    @PreviewParameter(SsingHomeTopBarPreviewProvider::class) logoRes: Int,
) {
    SSINGTheme {
        SsingHomeTopBar(
            logo = {
                Image(
                    painter = painterResource(logoRes),
                    contentDescription = null,
                )
            },
            onNotificationClick = {},
        )
    }
}
