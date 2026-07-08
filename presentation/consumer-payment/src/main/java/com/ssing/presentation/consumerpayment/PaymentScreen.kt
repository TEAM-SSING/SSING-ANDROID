package com.ssing.presentation.consumerpayment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingHeader
import com.ssing.core.ui.common.component.SsingTopBar
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.roundedBackgroundWithBorder

@Composable
internal fun PaymentRoute(
    modifier: Modifier = Modifier,
    viewModel: PaymentViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    PaymentScreen(
        modifier = modifier,
    )
}
@Composable
fun PaymentScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = SSINGTheme.colors.backgroundNormal),
        verticalArrangement = Arrangement.spacedBy(16.dp),

        ) {
        SsingTopBar(
            onBack = {},
            title = "결제",
        )

        SsingHeader(
            title = "결제 정보를 확인해요",
            subText = "강습은 강사님과 만나 양측 확인 후 시작돼요"
        )

        PayInfoSection()

        Spacer(modifier = Modifier.weight(1f))

        SsingButton(
            text = "결제하기",
            onClick = {},
            style = SsingButtonStyle.BLUE,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
private fun PayInfoSection(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .roundedBackgroundWithBorder(
                shape = RoundedCornerShape(12.dp),
                backgroundColor = SSINGTheme.colors.backgroundNormal,
                borderColor = SSINGTheme.colors.borderAlternative,
                borderWidth = 1.dp,
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
                text = "60,000원",
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
                text = "20,000원",
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
                text = "80,000원",
                style = SSINGTheme.typography.title.b16,
                color = SSINGTheme.colors.primaryNormal,
            )
        }

    }
}

@Preview
@Composable
private fun PaymentScreenPreview() {
    SSINGTheme {
        PaymentScreen(

        )
    }
}