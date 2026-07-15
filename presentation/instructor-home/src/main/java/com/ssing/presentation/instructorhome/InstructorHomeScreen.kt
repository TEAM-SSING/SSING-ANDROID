package com.ssing.presentation.instructorhome

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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.ssing.core.ui.common.component.Empty
import com.ssing.core.ui.common.component.HomeLessonCardList
import com.ssing.core.ui.common.component.Reservation
import com.ssing.core.ui.common.component.Reservation.Status
import com.ssing.core.ui.common.component.SsingChipStyle
import com.ssing.core.ui.common.component.SsingHomeTopBar
import com.ssing.core.ui.common.component.StartMatchingButton
import com.ssing.core.ui.common.component.StartMatchingCardStyle
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.core.ui.extension.toast
import com.ssing.core.ui.util.HandleUiEffects
import com.ssing.presentation.instructorhome.component.InstructorHomeEmptyReviewCard
import com.ssing.presentation.instructorhome.component.InstructorHomeReviewCard
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDateTime

@Composable
internal fun InstructorHomeRoute(
    contentPadding: PaddingValues,
    navigateToLessonDetail: (Long) -> Unit,
    navigateToMatching: () -> Unit,
    navigateToNotification: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InstructorHomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    HandleUiEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            is InstructorHomeContract.Effect.NavigateToLessonDetail -> navigateToLessonDetail(effect.lessonId)
            is InstructorHomeContract.Effect.NavigateToMatching -> navigateToMatching()
            is InstructorHomeContract.Effect.ShowToast -> context.toast(effect.message)
        }
    }

    InstructorHomeScreen(
        state = state,
        onLessonClick = viewModel::onLessonClick,
        onMatchingClick = viewModel::onMatchingClick,
        onReservationClick = viewModel::onReservationClick,
        onReviewClick = viewModel::onReviewClick,
        onNotificationClick = navigateToNotification,
        contentPadding = contentPadding,
        hasReview = false,
        modifier = modifier,
    )
}

@Composable
private fun InstructorHomeScreen(
    state: InstructorHomeContract.State,
    onLessonClick: (Reservation) -> Unit,
    onMatchingClick: () -> Unit,
    onReservationClick: () -> Unit,
    onReviewClick: () -> Unit,
    onNotificationClick: () -> Unit,
    contentPadding: PaddingValues,
    hasReview: Boolean,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SsingHomeTopBar(
                logo = {
                    Image(
                        painter = painterResource(R.drawable.img_instructor_logo),
                        contentDescription = null,
                    )
                },
                onNotificationClick = onNotificationClick,
                modifier = Modifier.statusBarsPadding(),
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = SSINGTheme.colors.backgroundAlternative,
                )
                .padding(top = innerPadding.calculateTopPadding())
                .verticalScroll(rememberScrollState())
                .padding(bottom = contentPadding.calculateBottomPadding())
        ) {

            Spacer(modifier = Modifier.height(8.dp))

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
                    badgeText = "${state.matchingCount}명 매칭중",
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

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "${state.nickname}님의 강습 후기",
                    color = SSINGTheme.colors.textNormal,
                    style = SSINGTheme.typography.body.sb16,
                )

                if (hasReview) {
                    InstructorHomeReviewCard(
                        averageRating = state.averageRating,
                        grade = state.grade,
                        achievementRate = state.achievementRate,
                        onClick = onReviewClick,
                    )
                }
                else { InstructorHomeEmptyReviewCard() }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InstructorHomeScreenPreview() {
    SSINGTheme {
        InstructorHomeScreen(
            state = InstructorHomeContract.State(
                nickname = "김씽씽",
                matchingCount = 99,
                lessonCards = persistentListOf(
                    Reservation(
                        lessonId = 1,
                        chip = "Now",
                        displayText = "김OO님 팀 3명",
                        location = "하이원",
                        date = LocalDateTime.of(2025, 7, 15, 19, 0),
                        imageRes = R.drawable.img_ski_86,
                        status = Status.Matching,
                    ),
                    Reservation(
                        lessonId = 1,
                        chip = "D-3",
                        displayText = "김OO님 팀 3명",
                        location = "지산리조트",
                        date = LocalDateTime.of(2026, 7, 11, 19, 0),
                        imageRes = R.drawable.img_snowboard_86,
                        status = Status.Default,
                    ),
                ),
                averageRating = 3.0f,
                grade = Grade.GRADE4,
                achievementRate = 88,
            ),
            onLessonClick = {},
            onMatchingClick = {},
            onReservationClick = {},
            onReviewClick = {},
            onNotificationClick = {},
            hasReview = true,
            contentPadding = PaddingValues(0.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InstructorHomeScreen2Preview() {
    SSINGTheme {
        InstructorHomeScreen(
            state = InstructorHomeContract.State(
                nickname = "김씽씽",
                matchingCount = 99,
                lessonCards = persistentListOf(Empty),
                averageRating = 3.0f,
                grade = Grade.GRADE4,
                achievementRate = 88,
            ),
            onLessonClick = {},
            onMatchingClick = {},
            onReservationClick = {},
            onReviewClick = {},
            onNotificationClick = {},
            hasReview = false,
            contentPadding = PaddingValues(0.dp),
        )
    }
}
