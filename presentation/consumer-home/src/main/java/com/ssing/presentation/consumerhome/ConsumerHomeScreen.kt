package com.ssing.presentation.consumerhome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssing.core.ui.R
import com.ssing.core.ui.common.component.HomeLessonCardList
import com.ssing.core.ui.common.component.SsingChipStyle
import com.ssing.core.ui.common.component.SsingHomeTopBar
import com.ssing.core.ui.common.component.StartMatchingButton
import com.ssing.core.ui.common.component.StartMatchingCardStyle
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.toast
import com.ssing.core.ui.state.HomeLessonCardState
import com.ssing.core.ui.state.HomeLessonCardState.Reservation.Status
import com.ssing.core.ui.state.HomeSport
import com.ssing.core.ui.util.HandleUiEffects
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDateTime

@Composable
internal fun ConsumerHomeRoute(
    navigateToLessonDetail: (Long) -> Unit,
    contentPadding : PaddingValues,
    navigateToMatching: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ConsumerHomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    HandleUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            is ConsumerHomeContract.Effect.NavigateToLessonDetail -> navigateToLessonDetail(effect.lessonId)
            is ConsumerHomeContract.Effect.NavigateToMatching -> navigateToMatching()
            is ConsumerHomeContract.Effect.ShowToast -> context.toast(effect.message)
        }
    }

    ConsumerHomeScreen(
        state = state,
        onLessonClick = viewModel::onLessonClick,
        onMatchingClick = viewModel::onMatchingClick,
        onReservationClick = viewModel::onReservationClick,
        contentPadding = contentPadding,
        modifier = modifier,
    )
}

@Composable
private fun ConsumerHomeScreen(
    state: ConsumerHomeContract.State,
    onLessonClick: (HomeLessonCardState.Reservation) -> Unit,
    onMatchingClick: () -> Unit,
    onReservationClick: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            SsingHomeTopBar(
                logo = {
                    Image(
                        painter = painterResource( R.drawable.img_consumer_logo),
                        contentDescription = null,
                    )
                },
                onNotificationClick = {},
                modifier = Modifier.statusBarsPadding(),
            )
        },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(
                    color = SSINGTheme.colors.backgroundAlternative,
                )
                .padding(top = innerPadding.calculateTopPadding())
                .verticalScroll(rememberScrollState())
                .padding(bottom = contentPadding.calculateBottomPadding()),
        ){

            Spacer(modifier = Modifier.height(23.dp))

            HomeLessonCardList(
                states = state.lessonCards,
                onButtonClick = onLessonClick,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "새로운 강습 시작하기",
                style = SSINGTheme.typography.body.sb16,
                color = SSINGTheme.colors.textNormal,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                StartMatchingButton(
                    badgeText = "${state.matchingConsumerCount}명 매칭중",
                    title = "씽 매칭",
                    description = "준비된 강습생과\n바로 연결하기",
                    iconRes = R.drawable.img_fast_dark,
                    style = StartMatchingCardStyle.BLUE,
                    onClick = onMatchingClick,
                    chipStyle = SsingChipStyle.BLUE,
                    modifier = Modifier.weight(1f),
                )

                StartMatchingButton(
                    badgeText = "예약 모집중",
                    title = "예약 관리",
                    description = "강습 가능한\n시간표 관리하기",
                    iconRes = R.drawable.img_reservation,
                    style = StartMatchingCardStyle.WHITE,
                    onClick = onReservationClick,
                    chipStyle = SsingChipStyle.BLUE,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ConsumerHomeScreenPreview() {
    ConsumerHomeScreen(
        state = ConsumerHomeContract.State(
            matchingConsumerCount = 99,
            lessonCards = persistentListOf(
                HomeLessonCardState.Reservation(
                    lessonId = 1,
                    chip = "Now",
                    displayText = "김OO님 팀 3명",
                    location = "하이원",
                    date = LocalDateTime.of(2025, 7, 15, 19, 0),
                    sport = HomeSport.SKI,
                    status = Status.Matching,
                ),
                HomeLessonCardState.Reservation(
                    lessonId = 1,
                    chip = "D-3",
                    displayText = "김OO님 팀 3명",
                    location = "지산리조트",
                    date = LocalDateTime.of(2026, 7, 11, 19, 0),
                    sport = HomeSport.SNOWBOARD,
                    status = Status.Default,
                ),
            ),
        ),
        onLessonClick = {},
        onMatchingClick = {},
        onReservationClick = {},
        contentPadding = PaddingValues(0.dp),
    )
}

@Preview(showBackground = true)
@Composable
private fun ConsumerHomeScreen2Preview() {
    ConsumerHomeScreen(
        state = ConsumerHomeContract.State(
            matchingConsumerCount = 99,
            lessonCards = persistentListOf(
                HomeLessonCardState.Empty,
            ),
        ),
        onLessonClick = {},
        onMatchingClick = {},
        onReservationClick = {},
        contentPadding = PaddingValues(0.dp),
    )
}
