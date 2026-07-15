package com.ssing.presentation.instructorlessondetail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.CancelReason
import com.ssing.core.ui.common.component.LessonBanner
import com.ssing.core.ui.common.component.LessonBannerState
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingTopBar
import com.ssing.core.ui.designsystem.theme.Blue50
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.presentation.instructorlessondetail.component.CancelReasonBottomSheet
import com.ssing.presentation.instructorlessondetail.component.ConsumerInfoList
import com.ssing.presentation.instructorlessondetail.component.LessonDetailBodyContainer
import com.ssing.presentation.instructorlessondetail.component.LessonDetailSsingButton
import com.ssing.presentation.instructorlessondetail.component.LessonInfoSection
import com.ssing.presentation.instructorlessondetail.component.LessonManager
import com.ssing.presentation.instructorlessondetail.component.SectionTitle
import com.ssing.presentation.instructorlessondetail.model.LessonDetailBeforeUiModel
import com.ssing.presentation.instructorlessondetail.model.TeamParticipantsInfo
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class CancelReasonState(
    val visible: Boolean = false,
    val selectedReason: CancelReason? = null,
)

/**
 * 강습 상세 (강습 전) 화면.
 *
 * @param before 강습 전 화면에 필요한 데이터
 * @param onChatRoomClick 채팅방 클릭
 * @param onReadyClick 강습 준비 완료 클릭
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LessonDetailBeforeScreen(
    before: LessonDetailBeforeUiModel,
    lessonBannerState: LessonBannerState,
    cancelReasonState: CancelReasonState,
    onBack: () -> Unit,
    onCancelSheetOpen: () -> Unit,
    onCancelSheetDismiss: () -> Unit,
    onCancelReasonSelect: (CancelReason) -> Unit,
    onCancelConfirmClick: (etcReasonText: String?) -> Unit,
    onChatRoomClick: () -> Unit,
    onReadyClick: () -> Unit,
    onReadyButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val etcState = rememberTextFieldState()
    LaunchedEffect(cancelReasonState.visible) {
        if (cancelReasonState.visible) {
            etcState.edit { replace(0, length, "") }
        }
    }

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
                    lessonBannerState = lessonBannerState,
                    beforeLessonText = "강습을 준비해주세요",
                )

                LessonDetailBodyContainer {
                    Spacer(modifier = Modifier.height(16.dp))
                    LessonInfoSection(
                        tags = before.tags,
                        nicknames = before.nicknames,
                        totalCount = before.teams.size,
                        place = before.location,
                        duration = before.duration,
                        price = before.price,
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    SectionTitle(text = "강습생 정보")

                    Spacer(modifier = Modifier.height(8.dp))

                    ConsumerInfoList(teams = before.teams)

                    LessonManager(
                        primaryText = "강습 취소",
                        onPrimaryClick = onCancelSheetOpen,
                        primaryStyle = SsingButtonStyle.RED,
                        secondaryText = "채팅방",
                        onSecondaryClick = onChatRoomClick,
                        secondaryStyle = SsingButtonStyle.GRAY,
                    )
                }
            }
        }
        LessonDetailSsingButton(
            text = if (before.isInstructorReady) "강습 대기중" else "강습 준비 완료",
            onClick = onReadyButtonClick,
        )
    }

    CancelReasonBottomSheet(
        cancelReasonState = cancelReasonState,
        onReasonClick = onCancelReasonSelect,
        onConfirmClick = onCancelConfirmClick,
        onDismissRequest = onCancelSheetDismiss,
    )
}

@Preview
@Composable
private fun LessonDetailBeforeScreenPreview() {
    SSINGTheme {
        LessonDetailBeforeScreen(
            before = LessonDetailBeforeUiModel(
                isInstructorReady = false,
                participantReadyCount = 2,
                participantTotalCount = 5,
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
                        isReady = true,
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
            onBack = {},
            onChatRoomClick = {},
            onReadyClick = {},
            onReadyButtonClick = {},
            onCancelSheetOpen = {},
            onCancelSheetDismiss = {},
            onCancelReasonSelect = {},
            onCancelConfirmClick = {},
            cancelReasonState = CancelReasonState(),
            lessonBannerState = LessonBannerState.Before(
                isInstructorReady = false,
                participantReadyCount = 2,
                participantTotalCount = 5,
            ),
        )
    }
}