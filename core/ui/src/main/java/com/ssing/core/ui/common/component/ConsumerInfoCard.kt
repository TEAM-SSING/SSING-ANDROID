package com.ssing.core.ui.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
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

@Composable
fun ConsumerInfoCard(
    isReady: Boolean,
    nickname: String,
    participants: ImmutableList<String>,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .roundedBackgroundWithBorder(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = White,
                borderWidth = 1.dp,
                borderColor = if (isReady) SSINGTheme.colors.primaryNormal else SSINGTheme.colors.borderAlternative,
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        if (isReady) {
            Row (
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Icon (
                    imageVector = ImageVector.vectorResource(R.drawable.ic_check_circle_filled_sm_12),
                    contentDescription = null,
                    tint = SSINGTheme.colors.primaryNormal,
                )

                Text (
                    text = "준비완료",
                    color = SSINGTheme.colors.primaryNormal,
                    style = SSINGTheme.typography.caption.sb12,
                )
            }
        }

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "${nickname}님 팀 ${participants.size}명",
                color = SSINGTheme.colors.textNormal,
                style = SSINGTheme.typography.body.sb16,
            )

            Row (
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                participants.forEach { participant ->
                    SsingChip(
                        text = participant,
                        style = SsingChipStyle.GRAY,
                    )
                }
            }
        }
    }
}

private class ConsumerInfoCardPreviewProvider: PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}

@Preview(showBackground = true)
@Composable
private fun ConsumerInfoCardPreview(
    @PreviewParameter(ConsumerInfoCardPreviewProvider::class) isReady: Boolean
) {
    SSINGTheme {
        Box(
            modifier = Modifier
                .padding(20.dp),
        ) {
            ConsumerInfoCard(
                isReady = isReady,
                nickname = "김OO",
                participants = persistentListOf(
                    "38세 남",
                    "12세 여",
                    "9세 남",
                ),
            )
        }
    }
}
