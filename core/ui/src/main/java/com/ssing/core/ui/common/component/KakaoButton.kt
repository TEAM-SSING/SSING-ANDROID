package com.ssing.core.ui.common.component

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.R
import com.ssing.core.ui.designsystem.component.SsingBasicButton
import com.ssing.core.ui.designsystem.theme.SSINGTheme

/**
 * 카카오 소셜 로그인 버튼.
 *
 * @param onClick 클릭 시 실행될 콜백
 * @param enabled 버튼 활성 여부
 */
@Composable
fun KakaoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    SsingBasicButton(
        defaultColor = Color(0xFFFFE700),
        pressedColor = Color(0xFFC7B50C),
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_login_kakao),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        Text(
            text = "카카오로 시작하기",
            color = SSINGTheme.colors.textStrong,
            style = SSINGTheme.typography.body.sb16,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Preview
@Composable
private fun KakaoButtonPreview() {
    SSINGTheme {
        KakaoButton(
            onClick = {},
            modifier = Modifier.width(328.dp),
        )
    }
}
