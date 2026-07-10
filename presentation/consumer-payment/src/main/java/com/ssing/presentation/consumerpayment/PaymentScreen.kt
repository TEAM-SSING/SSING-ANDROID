package com.ssing.presentation.consumerpayment

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.ssing.core.ui.common.component.SsingModal
import com.ssing.core.ui.common.component.SsingTopBar
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.toast
import com.ssing.core.ui.util.toDecimalFormat
import com.ssing.core.ui.util.HandleUiEffects
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun PaymentRoute(
    popBackStack: () -> Unit,
    navigateToLesson: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PaymentViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    HandleUiEffects(viewModel.uiEffect) { effect ->
        if (effect is PaymentContract.Effect.Result) {
            when (effect) {
                PaymentContract.Effect.Result.NavigateToLesson -> navigateToLesson()
                PaymentContract.Effect.Result.PopBackStack -> popBackStack()
                PaymentContract.Effect.Result.NavigateToHome -> navigateToHome()
                is PaymentContract.Effect.Result.ShowToast -> context.toast(effect.message)
            }
        }
    }

    BackHandler {
        viewModel.showCancelModal()
    }

    if (state.showCancelModal) {
        SsingModal(
            onDismissRequest = viewModel::closeCancelModal,
            title = "매칭을 취소할까요?",
            text = "결제창에서 벗어나면 매칭이 취소돼요",
            primaryText = "대기 유지",
            onPrimary = viewModel::closeCancelModal,
            primaryStyle = SsingButtonStyle.GRAY,
            secondaryText = "취소",
            onSecondary = viewModel::confirmCancel,
            secondaryStyle = SsingButtonStyle.RED,
        )
    }

    PaymentScreen(
        state = state,
        onPaymentClick = viewModel::navigateToLesson,
        onBackClick = viewModel::showCancelModal,
        modifier = modifier,
    )
}
@Composable
internal fun PaymentScreen(
    state: PaymentContract.State,
    onPaymentClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.background(color = SSINGTheme.colors.backgroundAlternative),
        topBar = {
            SsingTopBar(
                onBack = onBackClick,
                title = "결제",
                backgroundColor = SSINGTheme.colors.backgroundAlternative,
            )
        },
        bottomBar = {
            SsingButton(
                text = "결제하기",
                onClick = onPaymentClick,
                style = SsingButtonStyle.BLUE,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
            )
        }
    ) { innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = SSINGTheme.colors.backgroundAlternative,)
                .padding(innerPadding)
        ){
            SsingHeader(
                title = "결제 정보를 확인해요",
                subText = "강습은 강사님과 만나 양측 확인 후 시작돼요",
                modifier = Modifier
                    .padding(vertical = 16.dp)
            )

            PayInfoSection(
                state = state,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            )
        }

    }
}

@Composable
private fun PayInfoSection(
    state: PaymentContract.State,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(state = rememberScrollState()),
    ) {
         SsingMatchingDetailCard(
            nickname = state.nickname,
            stepLabel = "결제 정보",
            stepLabelColor = SSINGTheme.colors.textAlternative,
            tags = state.tags,
            classDateTime = state.classDateTime,
            location = state.location,
            duration = state.duration,
            participants = state.participants,
            equipmentStatus = state.equipmentStatus,
        )
        
        Spacer(modifier = Modifier.height(8.dp))

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
                    text = "${state.lessonCost.toDecimalFormat()}원",
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
                    text = "${state.resortCost.toDecimalFormat()}원",
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
                    text = "${(state.lessonCost+state.resortCost).toDecimalFormat()}원",
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
            state = PaymentContract.State(
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