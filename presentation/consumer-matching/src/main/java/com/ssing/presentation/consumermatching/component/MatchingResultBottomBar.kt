package com.ssing.presentation.consumermatching.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.figmaDropShadow
import java.text.DecimalFormat

private val decimal = DecimalFormat("#,###")

@Composable
internal fun MatchingResultBottomBar(
    duration: String,
    estimatedFee: Int,
    onRematchingClick: () -> Unit,
    onAcceptClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .figmaDropShadow(
                shape = RectangleShape,
                dpOffset = DpOffset(0.dp, 2.dp),
                blur = 10.dp,
                spread = 0.dp,
                color = Color(0x26000000)
            )
            .background(SSINGTheme.colors.backgroundNormal)
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp, bottom = 14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = "예상 결제 금액",
                    color = SSINGTheme.colors.textNormal,
                    style = SSINGTheme.typography.body.sb16,
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "1:1 즉시 강습",
                        color = SSINGTheme.colors.textAlternative,
                        style = SSINGTheme.typography.caption.sb14,
                    )

                    Text(
                        text = "·",
                        color = SSINGTheme.colors.textAlternative,
                        style = SSINGTheme.typography.body.sb16,
                    )


                    Text(
                        text = "$duration 기준",
                        color = SSINGTheme.colors.textAlternative,
                        style = SSINGTheme.typography.caption.sb14,
                    )
                }
            }

            Text(
                text = decimal.format(estimatedFee) + "원",
                color = SSINGTheme.colors.primaryNormal,
                style = SSINGTheme.typography.body.sb20,
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SsingButton(
                text = "다른 강사님 찾기",
                onClick = onRematchingClick,
                style = SsingButtonStyle.GRAY,
                modifier = Modifier.weight(1f),
            )

            SsingButton(
                text = "수락하기",
                onClick = onAcceptClick,
                style = SsingButtonStyle.BLUE,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Preview
@Composable
private fun MatchingResultBottomBarPreview() {
    SSINGTheme {
        MatchingResultBottomBar(
            duration = "2시간",
            estimatedFee = 80000,
            onRematchingClick = {},
            onAcceptClick = {},
        )
    }
}
