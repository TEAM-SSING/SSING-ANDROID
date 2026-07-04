package com.ssing.core.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.ssing.core.ui.designsystem.theme.SSINGTheme

/**
 * SSING 공통 모달
 * @param title 제목
 * @param text 본문 설명
 * @param primaryText 오른쪽 파란 버튼 텍스트 (주요 액션, 예: "대기 중지", "강습 종료하기")
 * @param secondaryText 왼쪽 회색 버튼 텍스트 (보조 액션, 예: "계속 대기", "계속 진행하기")
 * @param onPrimary 파란 버튼 클릭 콜백
 * @param onSecondary 회색 버튼 클릭 콜백 (외부 터치 시에도 호출)
 */
@Composable
fun SsingModal(
    title: String,
    text: String,
    primaryText: String,
    secondaryText: String,
    onPrimary: () -> Unit,
    onSecondary: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = onSecondary,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        val dialogWindow = (LocalView.current.parent as? DialogWindowProvider)?.window
        SideEffect {
            dialogWindow?.setDimAmount(0.45f)
        }

        SsingModalContent(
            title = title,
            text = text,
            primaryText = primaryText,
            secondaryText = secondaryText,
            onPrimary = onPrimary,
            onSecondary = onSecondary,
            modifier = modifier,
        )
    }
}

@Composable
private fun SsingModalContent(
    title: String,
    text: String,
    primaryText: String,
    secondaryText: String,
    onPrimary: () -> Unit,
    onSecondary: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(SSINGTheme.colors.backgroundNormal)
            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = title,
                style = SSINGTheme.typography.body.sb16,
                color = SSINGTheme.colors.textNormal,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            Text(
                text = text,
                style = SSINGTheme.typography.caption.md14,
                color = SSINGTheme.colors.textAlternative,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SsingButton(
                text = secondaryText,
                onClick = onSecondary,
                style = SsingButtonStyle.GRAY,
                modifier = Modifier.weight(1f),
            )
            SsingButton(
                text = primaryText,
                onClick = onPrimary,
                style = SsingButtonStyle.BLUE,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0XFF7C7C7C)
@Composable
private fun SsingModalPreview() {
    SSINGTheme {
        SsingModalContent(
            title = "대기를 중지할까요?",
            text = "홈으로 이동해도 빠른 매칭 대기는 유지되요.\n대기를 중지하면 더 이상 요청을 받지 않아요.",
            primaryText = "대기 중지",
            secondaryText = "계속 대기",
            onPrimary = {},
            onSecondary = {},
        )
    }
}
