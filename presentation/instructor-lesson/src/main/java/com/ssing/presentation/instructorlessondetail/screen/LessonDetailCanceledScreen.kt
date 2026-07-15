package com.ssing.presentation.instructorlessondetail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.LessonBanner
import com.ssing.core.ui.common.component.LessonBannerState
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingTopBar
import com.ssing.core.ui.designsystem.theme.Blue50
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.presentation.instructorlessondetail.component.ConsumerInfoList
import com.ssing.presentation.instructorlessondetail.component.LessonDetailBodyContainer
import com.ssing.presentation.instructorlessondetail.component.LessonDetailSsingButton
import com.ssing.presentation.instructorlessondetail.component.LessonInfoSection
import com.ssing.presentation.instructorlessondetail.component.LessonManager
import com.ssing.presentation.instructorlessondetail.component.SectionTitle
import com.ssing.presentation.instructorlessondetail.model.LessonDetailCanceledUiModel
import com.ssing.presentation.instructorlessondetail.model.TeamParticipantsInfo
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun LessonDetailCanceledScreen(
    cancel: LessonDetailCanceledUiModel,
    lessonBannerState: LessonBannerState,
    onBack: () -> Unit,
    onCancelClassClick: () -> Unit,
    onEndClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .navigationBarsPadding()
            .background(Blue50),
    ) {
        SsingTopBar(
            title = "강습 상세",
            onBack = onBack,
            backgroundColor = Blue50,
            modifier = Modifier
                .background(Blue50)
                .statusBarsPadding(),
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .background(color = Blue50),
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                LessonBanner(
                    lessonBannerState = lessonBannerState
                )

                LessonDetailBodyContainer {
                    Spacer(modifier = Modifier.height(16.dp))
                    LessonInfoSection(
                        tags = cancel.tags,
                        nicknames = cancel.nicknames,
                        totalCount = cancel.teams.size,
                        place = cancel.location,
                        duration = cancel.duration,
                        price = cancel.price,
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    SectionTitle(text = "강습생 정보")

                    Spacer(modifier = Modifier.height(8.dp))

                    ConsumerInfoList(teams = cancel.teams)

                    LessonManager(
                        primaryText = "문제 신고",
                        onPrimaryClick = onCancelClassClick,
                        primaryStyle = SsingButtonStyle.RED,
                        secondaryText = "강습 내역 보기",
                        onSecondaryClick = onEndClick,
                        secondaryStyle = SsingButtonStyle.GRAY,
                    )
                }
            }
        }
        LessonDetailSsingButton(
            text = "씽 매칭으로 돌아가기",
            onClick = onEndClick,
        )
    }
}

@Preview
@Composable
private fun LessonDetailCanceledScreenPreview() {
    SSINGTheme {
        LessonDetailCanceledScreen(
            cancel = LessonDetailCanceledUiModel(
                tags = persistentListOf("스노보드", "자격증이 있어요"),
                classTitle = "김OO님 팀, 홍지민님 팀 총 5명",
                location = "OOO 리조트",
                duration = "0시간",
                price = 0,
                teams = persistentListOf(
                    TeamParticipantsInfo(
                        teamNickname = "김OO",
                        teamCount = 0,
                        participants = persistentListOf("38세 남", "12세 여", "9세 남"),
                        price = 0,
                        isReady = false,
                    ),
                    TeamParticipantsInfo(
                        teamNickname = "김OO",
                        teamCount = 0,
                        participants = persistentListOf("38세 남", "12세 여", "9세 남"),
                        price = 0,
                        isReady = false,
                    ),
                ),
            ),
            onCancelClassClick = {},
            onBack = {},
            onEndClick = {},
            lessonBannerState = LessonBannerState.Canceled,
        )
    }
}