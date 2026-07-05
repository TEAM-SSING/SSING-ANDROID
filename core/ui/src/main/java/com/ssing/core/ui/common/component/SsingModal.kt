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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.component.SsingBasicModal
import com.ssing.core.ui.designsystem.theme.SSINGTheme

/**
 * 제목, 설명, 버튼으로 구성된 모달 컴포넌트입니다.
 *
 * [secondaryText]와 [onSecondary]를 모두 전달하면 버튼 두 개(보조/주요)가 표시되고,
 * 생략하면 [primaryText] 버튼 하나만 표시됩니다.
 *
 * 버튼 없이 모달을 닫을 수 없어야 하는 경우(예: 강습 연결 취소 안내)에는
 * [dismissOnBackPress]와 [dismissOnClickOutside]를 false로 설정.
 *
 * @param onDismissRequest 모달을 닫는 콜백입니다. 버튼 클릭 및 외부 터치/뒤로가기 시 모두 호출됩니다.
 * @param title 모달 제목입니다.
 * @param text 모달 본문 설명입니다.
 * @param primaryText 주요 액션 버튼 텍스트입니다.
 * @param onPrimary 주요 액션 버튼 클릭 콜백입니다.
 * @param modifier
 * @param primaryStyle 주요 버튼 스타일입니다.
 * @param secondaryText 보조 액션 버튼 텍스트입니다.
 * @param onSecondary 보조 액션 버튼 클릭 콜백입니다.
 * @param secondaryStyle 보조 버튼 스타일입니다.
 * @param dismissOnBackPress 시스템 뒤로가기로 모달을 닫을 수 있는지 여부입니다.
 * @param dismissOnClickOutside 모달 바깥 영역 터치로 모달을 닫을 수 있는지 여부입니다.
 * @sample SsingModalPreview
 */
@Composable
fun SsingModal(
    onDismissRequest: () -> Unit,
    title: String,
    text: String,
    primaryText: String,
    onPrimary: () -> Unit,
    modifier: Modifier = Modifier,
    primaryStyle: SsingButtonStyle = SsingButtonStyle.BLUE,
    secondaryText: String? = null,
    onSecondary: (() -> Unit)? = null,
    secondaryStyle: SsingButtonStyle = SsingButtonStyle.GRAY,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
) {
    SsingBasicModal(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        dismissOnBackPress = dismissOnBackPress,
        dismissOnClickOutside = dismissOnClickOutside,
    ) {
        SsingModalBody(
            title = title,
            text = text,
            primaryText = primaryText,
            onPrimary = { onDismissRequest(); onPrimary() },
            primaryStyle = primaryStyle,
            secondaryText = secondaryText,
            onSecondary = onSecondary?.let { { onDismissRequest(); it() } },
            secondaryStyle = secondaryStyle,
        )
    }
}

@Composable
private fun SsingModalBody(
    title: String,
    text: String,
    primaryText: String,
    onPrimary: () -> Unit,
    primaryStyle: SsingButtonStyle,
    secondaryText: String?,
    onSecondary: (() -> Unit)?,
    secondaryStyle: SsingButtonStyle,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
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
            if (secondaryText != null && onSecondary != null) {
                SsingButton(
                    text = secondaryText,
                    onClick = onSecondary,
                    style = secondaryStyle,
                    modifier = Modifier.weight(1f),
                )
            }

            SsingButton(
                text = primaryText,
                onClick = onPrimary,
                style = primaryStyle,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

private data class SsingModalPreviewData(
    val title: String,
    val text: String,
    val primaryText: String,
    val primaryStyle: SsingButtonStyle = SsingButtonStyle.BLUE,
    val secondaryText: String? = null,
    val onSecondary: (() -> Unit)? = null,
    val secondaryStyle: SsingButtonStyle = SsingButtonStyle.GRAY,
)

private class SsingModalPreviewProvider : PreviewParameterProvider<SsingModalPreviewData> {
    override val values = sequenceOf(
        SsingModalPreviewData(
            title = "대기를 중지할까요?",
            text = "홈으로 이동해도 빠른 매칭 대기는 유지되요.\n대기를 중지하면 더 이상 요청을 받지 않아요.",
            primaryText = "대기 중지",
            secondaryText = "계속 대기",
            onSecondary = {},
        ),
        SsingModalPreviewData(
            title = "강습 대기를 취소할까요?",
            text = "강습 준비가 완료되어야 강습이 시작돼요",
            primaryText = "취소",
            primaryStyle = SsingButtonStyle.RED,
            secondaryText = "대기 유지",
            onSecondary = {},
            secondaryStyle = SsingButtonStyle.GRAY,
        ),
        SsingModalPreviewData(
            title = "강습연결이 취소됐어요",
            text = "3분 이내로 수락하지 않아\n강습연결이 자동으로 취소되었어요",
            primaryText = "홈으로 이동",
        ),
    )
}

@Preview(showBackground = true, backgroundColor = 0XFF8C8C8C)
@Composable
private fun SsingModalPreview(
    @PreviewParameter(SsingModalPreviewProvider::class) data: SsingModalPreviewData,
) {
    SSINGTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(SSINGTheme.colors.backgroundNormal)
                .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            SsingModalBody(
                title = data.title,
                text = data.text,
                primaryText = data.primaryText,
                onPrimary = {},
                primaryStyle = data.primaryStyle,
                secondaryText = data.secondaryText,
                onSecondary = data.onSecondary,
                secondaryStyle = data.secondaryStyle,
            )
        }
    }
}
