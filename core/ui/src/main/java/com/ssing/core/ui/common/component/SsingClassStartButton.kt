package com.ssing.core.ui.common.component

import android.R.attr.enabled
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.R
import com.ssing.core.ui.designsystem.theme.Blue200
import com.ssing.core.ui.designsystem.theme.Blue600
import com.ssing.core.ui.designsystem.theme.SSINGTheme

/**
 * 강습 시작 버튼용 카드.
 *
 * @param badgeText 상단 배지 텍스트
 * @param title 타이틀
 * @param description 설명 텍스트
 * @param background 카드 배경 (단색/그라데이션 등 카드마다 다르므로 파라미터로 받음)
 * @param tone 아이콘/텍스트 색상 세트 (라이트/다크 두 가지로 닫혀 있어 enum으로 관리)
 * @param chipStyle 배지 칩 스타일
 */

enum class StartMatchingCardStyle {
    WHITE, BLUE
}

private val SsingButtonStyle.defaultColor: Color

@Composable
fun StartMatchingCard(
    badgeText: String,
    title: String,
    description: String,
    background: Brush,
    tone: StartMatchingCardStyle,
    pressedColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    chipStyle: SsingChipStyle = SsingChipStyle.BLUE,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val shape = RoundedCornerShape(12.dp)

    Box(
        modifier = modifier
            .clip(shape)
            .background(brush = if (isPressed) SolidColor(pressedColor) else background)
            .border(
                width = 1.dp,
                color = SSINGTheme.colors.primaryAlternative,
                shape = shape,
            )
            .clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null,
            ),
    ) {
        Image(
            painter = painterResource(id = tone.iconRes),
            contentDescription = null,
            modifier = Modifier
                .size(width = 90.dp, height = 143.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 20.dp, y = 27.dp)
        )
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
                .aspectRatio(0.8f),
        ) {
            SsingChip(text = badgeText, style = chipStyle)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                style = SSINGTheme.typography.body.sb16,
                color = tone.titleColor(),
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = description,
                style = SSINGTheme.typography.caption.sb12,
                color = tone.descriptionColor(),
            )
        }
    }
}

@Preview
@Composable
private fun SsingStartClassCardPreview() {
    SSINGTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(17.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StartMatchingCard(
                    badgeText = "text", title = "title", description = "text",
                    background = Brush.linearGradient(listOf(Color.White, Color(0xFFEFF2F8))),
                    tone = StartMatchingCardStyle.WHITE,
                    chipStyle = SsingChipStyle.BLUE,
                    modifier = Modifier.weight(1f),
                )

                StartMatchingCard(
                    badgeText = "text", title = "title", description = "text",
                    background = SolidColor(SSINGTheme.colors.borderDisabled),
                    tone = StartMatchingCardStyle.WHITE,
                    chipStyle = SsingChipStyle.BLUE,
                    modifier = Modifier.weight(1f),
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StartMatchingCard(
                    badgeText = "text", title = "title", description = "text",
                    background = Brush.linearGradient(
                        listOf(
                            Color(0xFF64AAFF),
                            Color(0xFF3184EA),
                            Color(0xFF357DD5),
                        )
                    ),
                    tone = StartMatchingCardStyle.BLUE,
                    chipStyle = SsingChipStyle.BLUE,
                    modifier = Modifier.weight(1f),
                )

                StartMatchingCard(
                    badgeText = "text", title = "title", description = "text",
                    background = SolidColor(Blue600),
                    tone = StartMatchingCardStyle.BLUE,
                    chipStyle = SsingChipStyle.BLUE,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}