package com.ssing.presentation.instructorlessondetail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.CancelReason
import com.ssing.core.ui.common.component.ConsumerInfoCard
import com.ssing.core.ui.common.component.LessonBanner
import com.ssing.core.ui.common.component.LessonBannerState
import com.ssing.core.ui.common.component.MatchingCancelBottomSheet
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingMatchingDetailCardSmall
import com.ssing.core.ui.common.component.SsingTopBar
import com.ssing.core.ui.common.component.UserRole
import com.ssing.core.ui.designsystem.theme.Blue50
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.presentation.instructorlessondetail.component.SectionTitle
import com.ssing.presentation.instructorlessondetail.model.LessonDetailBeforeUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class CancelReasonState(
    val selectedReason: CancelReason? = null,
    val etcReasonText: String = "",
)

/**
 * 강습 상세 (강습 전) 화면.
 *
 * @param before 강습 전 화면에 필요한 데이터
 * @param onCancelClassClick 강습 취소 클릭
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
    onCancelClassClick: () -> Unit,
    onChatRoomClick: () -> Unit,
    onReadyClick: () -> Unit,
    onReadyButtonClick: () -> Unit,
    onCancelReasonSelect: (CancelReason) -> Unit,
    modifier: Modifier = Modifier,
) {

    var showSheet by remember { mutableStateOf(false) }
    val etcState = rememberTextFieldState(initialText = cancelReasonState.etcReasonText)
    LaunchedEffect(cancelReasonState.etcReasonText) {
        if (etcState.text.toString() != cancelReasonState.etcReasonText) {
            etcState.edit { replace(0, length, cancelReasonState.etcReasonText) }
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
                .fillMaxSize()
                .background(
                    color = SSINGTheme.colors.backgroundNormal,
                    shape = RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 12.dp,
                    ),
                ),
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
            ) {
                item {
                    LessonBanner(
                        lessonBannerState = lessonBannerState,
                        beforeLessonText = "강사님과 만난 후\n강습 시작을 눌러주세요",
                    )
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {}
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    ) {
                        SectionTitle(text = "강습 정보")
                        Spacer(modifier = Modifier.height(8.dp))
                        SsingMatchingDetailCardSmall(
                            tags = before.tags,
                            teamNicknames = before.nicknames,
                            totalCount = before.teams.size,
                            place = before.location,
                            duration = before.duration,
                            price = before.price,
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    ) {
                        SectionTitle(text = "강습생 정보")
                        Spacer(modifier = Modifier.height(8.dp))
                        before.teams.forEachIndexed { index, team ->
                            ConsumerInfoCard(
                                isReady = team.isReady ?: false,
                                nickname = team.teamNickname,
                                participants = team.participants,
                                price = team.price,
                            )
                            if (index != before.teams.lastIndex) {
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text(
                            text = "강습 관리",
                            style = SSINGTheme.typography.caption.sb12,
                            color = SSINGTheme.colors.textAlternative,
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        SsingButton(
                            text = "강습 취소",
                            onClick = { showSheet = true },
                            style = SsingButtonStyle.RED,
                            modifier = Modifier.weight(1f),
                        )

                        SsingButton(
                            text = "채팅방",
                            onClick = onChatRoomClick,
                            style = SsingButtonStyle.GRAY,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SSINGTheme.colors.backgroundNormal)
                    .padding(16.dp),
            ) {
                SsingButton(
                    text = if (before.isInstructorReady) "강습 대기중" else "강습 준비 완료",
                    onClick = {
                        if (!before.isInstructorReady) {
                            onReadyButtonClick()
                        }
                    },
                    style = if (before.isInstructorReady) SsingButtonStyle.GRAY else SsingButtonStyle.BLUE,
                    enabled = !before.isInstructorReady,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
    if (showSheet) {
        MatchingCancelBottomSheet(
            userRole = UserRole.INSTRUCTOR,
            selectedReason = cancelReasonState.selectedReason,
            onReasonClick = onCancelReasonSelect,
            etcState = etcState,
            onConfirmClick = { onCancelClassClick() },
            onDismissRequest = { onCancelClassClick()  },
        )
    }
}

/** 강습생 정보 화면에서 팀 단위로 보여줄 데이터. */
@Immutable
data class TeamParticipantsInfo(
    val teamNickname: String,
    val teamCount: Int,
    val participants: ImmutableList<String>,
    val price: Int,
    val isReady: Boolean? = false,
)

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
            onCancelClassClick = {},
            onBack = {},
            onChatRoomClick = {},
            onReadyClick = {},
            onReadyButtonClick = {},
            onCancelReasonSelect = {},
            cancelReasonState = CancelReasonState(),
            lessonBannerState = LessonBannerState.Before(
                isInstructorReady = false,
                participantReadyCount = 2,
                participantTotalCount = 5,
            ),
        )
    }
}