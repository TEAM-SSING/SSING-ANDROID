package com.ssing.core.ui.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.R
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.designsystem.theme.White
import com.ssing.core.ui.extension.roundedBackgroundWithBorder
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * 강습생별 정보 카드 컴포넌트입니다.
 *
 * @param isReady 준비 완료 여부.
 * @param nickname 예약자 이름.
 * @param participants 참가자 목록.
 * @param price 결제 금액.
 * @param modifier Composable에 적용할 modifier.
 */


@Composable
fun ConsumerInfoCard(
    isReady: Boolean,
    nickname: String,
    participants: ImmutableList<String>,
    modifier: Modifier = Modifier,
    price: Int? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .roundedBackgroundWithBorder(
                shape = RoundedCornerShape(12.dp),
                backgroundColor = White,
                borderWidth = 1.dp,
                borderColor = if (isReady) SSINGTheme.colors.primaryNormal else SSINGTheme.colors.borderAlternative,
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            if (isReady) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_check_circle_filled_sm_12),
                    contentDescription = null,
                    tint = SSINGTheme.colors.primaryNormal,
                )
                Text(
                    text = "준비완료",
                    color = SSINGTheme.colors.primaryNormal,
                    style = SSINGTheme.typography.caption.sb12,
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = "${nickname}님 팀 ${participants.size}명",
                color = SSINGTheme.colors.textNormal,
                style = SSINGTheme.typography.body.sb16,
            )

            val isSingleLine = participants.size <= 3

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = if (isSingleLine) Alignment.Top else Alignment.Bottom,
            ) {
                FlowRow(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    maxItemsInEachRow = 3,
                ) {
                    participants.forEach { participant ->
                        SsingChip(
                            text = participant,
                            style = SsingChipStyle.GRAY,
                        )
                    }
                }

                price?.let {
                    Text(
                        text = "₩ ${"%,d".format(it)}",
                        color = SSINGTheme.colors.textNormal,
                        style = SSINGTheme.typography.caption.sb14,
                    )
                }
            }
        }
    }
}

private class ConsumerInfoCardPreviewProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}

@Preview(showBackground = true)
@Composable
private fun ConsumerInfoCardPreview(
    @PreviewParameter(ConsumerInfoCardPreviewProvider::class) isReady: Boolean
) {
    SSINGTheme {
        Column(
            modifier = Modifier
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ConsumerInfoCard(
                isReady = isReady,
                nickname = "김OO",
                participants = persistentListOf(
                    "38세 남",
                    "12세 여",
                    "9세 남",
                ),
                price = 50000,
            )

            ConsumerInfoCard(
                isReady = isReady,
                nickname = "김OO",
                participants = persistentListOf(
                    "38세 남",
                    "12세 여",
                    "9세 남",
                    "38세 남",
                    "12세 여",
                ),
                price = 50000,
            )
        }
    }
}
