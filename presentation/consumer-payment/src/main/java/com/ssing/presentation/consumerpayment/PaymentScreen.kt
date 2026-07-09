package com.ssing.presentation.consumerpayment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssing.core.ui.common.component.Gender
import com.ssing.core.ui.common.component.Participant
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingHeader
import com.ssing.core.ui.common.component.SsingMatchingDetailCard
import com.ssing.core.ui.common.component.SsingTopBar
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.util.DecimalFormatter
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun PaymentRoute(
    modifier: Modifier = Modifier,
    viewModel: PaymentViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    PaymentScreen(
        paymentInfo = state.paymentInfo,
        onPaymentClick = { viewModel.onLessonClick() },
        onBackClick = {},
        modifier = modifier,
    )
}
@Composable
fun PaymentScreen(
    paymentInfo: PaymentInfo,
    onPaymentClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = SSINGTheme.colors.backgroundAlternative),

        ) {
        SsingTopBar(
            onBack = onBackClick,
            title = "결제",
            backgroundColor = SSINGTheme.colors.backgroundAlternative,
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        SsingHeader(
            title = "결제 정보를 확인해요",
            subText = "강습은 강사님과 만나 양측 확인 후 시작돼요"
        )

        PayInfoSection(
            paymentInfo = paymentInfo,
        )

        Spacer(modifier = Modifier.weight(1f))

        SsingButton(
            text = "결제하기",
            onClick = onPaymentClick,
            style = SsingButtonStyle.BLUE,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
    }
}

@Composable
private fun PayInfoSection(
    paymentInfo: PaymentInfo,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
         SsingMatchingDetailCard(
            nickname = paymentInfo.nickname,
            stepLabel = "결제 정보",
            stepLabelColor = SSINGTheme.colors.textAlternative,
            tags = paymentInfo.tags,
            classDateTime = paymentInfo.classDateTime,
            location = paymentInfo.location,
            duration = paymentInfo.duration,
            participants = paymentInfo.participants,
            equipmentStatus = paymentInfo.equipmentStatus,
        )

        Column(
            modifier = Modifier
                .background(
                    color = SSINGTheme.colors.backgroundNormal,
                    shape = RoundedCornerShape(12.dp),
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "강습금액",
                    style = SSINGTheme.typography.caption.sb12,
                    color = SSINGTheme.colors.textNormal,
                )

                Text(
                    text = "${paymentInfo.lessonCost.DecimalFormatter()}원",
                    style = SSINGTheme.typography.caption.sb14,
                    color = SSINGTheme.colors.textStrong,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "리조트 패찰비",
                    style = SSINGTheme.typography.caption.sb12,
                    color = SSINGTheme.colors.textNormal,
                )

                Text(
                    text = "${paymentInfo.resortCost.DecimalFormatter()}원",
                    style = SSINGTheme.typography.caption.sb14,
                    color = SSINGTheme.colors.textStrong,
                )
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = SSINGTheme.colors.borderDisabled,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "총 결제금액",
                    style = SSINGTheme.typography.caption.sb14,
                    color = SSINGTheme.colors.textNormal,
                )

                Text(
                    text = "${(paymentInfo.lessonCost+paymentInfo.resortCost).DecimalFormatter()}원",
                    style = SSINGTheme.typography.title.b16,
                    color = SSINGTheme.colors.primaryNormal,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PaymentScreenPreview() {
    SSINGTheme {
        PaymentScreen(
            paymentInfo = PaymentInfo(
                nickname = "김OO",
                tags = persistentListOf("스노보드", "처음타요"),
                classDateTime = "7월 9일 오후 04:40",
                location = "지산리조트",
                duration = "3시간",
                participants = persistentListOf(
                    Participant(11, Gender.MALE),
                    Participant(11, Gender.MALE),
                    Participant(9, Gender.FEMALE),
                ),
                equipmentStatus = "착용 완료",
                lessonCost = 60000,
                resortCost = 20000,
            ),
            onPaymentClick = {},
            onBackClick = {},
        )
    }
}