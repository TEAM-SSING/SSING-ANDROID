package com.ssing.presentation.notification.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.theme.Blue50
import com.ssing.core.ui.designsystem.theme.SSINGTheme

enum class NotificationAlarmCardStyle {
    PRESSED,
    READ,
    UNREAD
}

private val NotificationAlarmCardStyle.backgroundColor: Color
    @Composable
    get() = when (this) {
        NotificationAlarmCardStyle.PRESSED -> SSINGTheme.colors.backgroundAlternative
        NotificationAlarmCardStyle.READ -> SSINGTheme.colors.backgroundNormal
        NotificationAlarmCardStyle.UNREAD -> Blue50
    }

@Composable
internal fun NotificationAlarmCard(
    category: String,
    content: String,
    date: String,
    isRead: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val style = when {
        isPressed -> NotificationAlarmCardStyle.PRESSED
        isRead -> NotificationAlarmCardStyle.READ
        else -> NotificationAlarmCardStyle.UNREAD
    }

    NotificationAlarmCardContent(
        category = category,
        content = content,
        date = date,
        style = style,
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
    )
}

@Composable
private fun NotificationAlarmCardContent(
    category: String,
    content: String,
    date: String,
    style: NotificationAlarmCardStyle,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = style.backgroundColor)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = category,
            style = SSINGTheme.typography.caption.sb12,
            color = SSINGTheme.colors.primaryNormal,
        )

        Text(
            text = content,
            style = SSINGTheme.typography.caption.md14,
            color = SSINGTheme.colors.textNormal,
        )

        Text(
            text = date,
            style = SSINGTheme.typography.caption.md12,
            color = SSINGTheme.colors.textAlternative,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NotificationAlarmCardPreview() {
    SSINGTheme {
        Column {
            NotificationAlarmCardContent(
                category = "씽 매칭 강습 도착",
                content = "새로운 강습이 도착했어요. 강습생 정보를 확인하고 강습을 수락해보세요.",
                date = "07.04 (토) 12:59",
                style = NotificationAlarmCardStyle.UNREAD,
            )
            NotificationAlarmCardContent(
                category = "씽 매칭 완료",
                content = "강습 매칭이 완료되었습니다. 상세 일정을 확인해주세요.",
                date = "07.03 (금) 10:00",
                style = NotificationAlarmCardStyle.READ,
            )
            NotificationAlarmCardContent(
                category = "씽 매칭 강습 도착",
                content = "새로운 강습이 도착했어요. 강습생 정보를 확인하고 강습을 수락해보세요.",
                date = "07.04 (토) 12:59",
                style = NotificationAlarmCardStyle.PRESSED,
            )
        }
    }
}