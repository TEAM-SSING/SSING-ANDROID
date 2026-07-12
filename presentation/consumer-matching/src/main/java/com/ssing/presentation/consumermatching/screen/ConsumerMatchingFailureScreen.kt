package com.ssing.presentation.consumermatching.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingHeader
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.toast
import com.ssing.core.ui.util.HandleUiEffects
import com.ssing.presentation.consumermatching.ConsumerMatchingContract
import com.ssing.presentation.consumermatching.ConsumerMatchingViewModel

@Composable
internal fun ConsumerMatchingFailureRoute(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ConsumerMatchingViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    HandleUiEffects(viewModel.uiEffect) { effect ->
        if (effect is ConsumerMatchingContract.Effect.Failure) {
            when (effect) {
                ConsumerMatchingContract.Effect.Failure.NavigateToHome -> navigateToHome()
                is ConsumerMatchingContract.Effect.Failure.ShowToast -> context.toast(effect.message)
            }
        }
    }

    BackHandler { }

    ConsumerMatchingFailureScreen(
        onReservationClick = viewModel::navigateToHome,
        modifier = modifier,
    )
}

@Composable
internal fun ConsumerMatchingFailureScreen(
    onReservationClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            SsingButton(
                text = "예약강습으로 요청하기",
                onClick = onReservationClick,
                style = SsingButtonStyle.BLUE,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SSINGTheme.colors.backgroundNormal)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SsingHeader(
                title = "조건에 맞는 강사님이 매칭되지 않았어요",
                subText = "현재 씽 매칭 가능한 강사님이 없어요\n예약강습으로 원하는 시간대에 맞춰 강습을 찾을 수 있어요",
                modifier = Modifier.padding(top = 80.dp, bottom = 32.dp),
            )

            Box(
                modifier = Modifier
                    .background(SSINGTheme.colors.primaryAlternative)
                    .size(217.dp),
            )
        }
    }
}

@Preview
@Composable
private fun ConsumerMatchingFailureScreenPreview() {
    SSINGTheme {
        ConsumerMatchingFailureScreen(
            onReservationClick = {},
        )
    }
}
