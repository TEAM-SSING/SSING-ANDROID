package com.ssing.core.ui.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.ssing.core.ui.designsystem.theme.SSINGTheme

/**
 * 기본 모달 컴포넌트
 *
 * @param onDismissRequest 모달을 닫는 콜백
 * @param modifier
 * @param dismissOnBackPress 시스템 뒤로가기 시 모달을 닫는 여부
 * @param dismissOnClickOutside 모달 바깥 영역 터치 시 모달을 닫는 여부
 * @param content 모달 카드 내부 콘텐츠입니다.
 */
@Composable
internal fun SsingBasicModal(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    val dialogProperties = remember(dismissOnBackPress, dismissOnClickOutside) {
        DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside,
            usePlatformDefaultWidth = false,
        )
    }
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = dialogProperties,
    ) {
        val dialogWindow = (LocalView.current.parent as? DialogWindowProvider)?.window
        SideEffect {
            dialogWindow?.setDimAmount(0.45f)
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(SSINGTheme.colors.backgroundNormal)
                .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = content,
        )
    }
}
