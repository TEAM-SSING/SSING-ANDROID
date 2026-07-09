package instructorlessondetail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.ConsumerInfoCard
import com.ssing.core.ui.common.component.LessonBanner
import com.ssing.core.ui.common.component.LessonBannerState
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingChip
import com.ssing.core.ui.common.component.SsingChipStyle
import com.ssing.core.ui.common.component.SsingModal
import com.ssing.core.ui.designsystem.theme.Blue50
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import instructorlessondetail.model.LessonDetailOngoingUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LessonDetailOngoingScreen(
    ongoing: LessonDetailOngoingUiModel,
    lessonBannerState: LessonBannerState,
    onCancelClassClick: () -> Unit,
    onChatRoomClick: () -> Unit,
    onEndClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    var headerHeightPx by remember { mutableIntStateOf(0) }
    var showReadyDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SSINGTheme.colors.backgroundNormal)
                    .padding(16.dp),
            ) {
                Text(
                    text = "강습 관리",
                    style = SSINGTheme.typography.caption.sb12,
                    color = SSINGTheme.colors.textAlternative,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    SsingButton(
                        text = "문제 신고",
                        onClick = onCancelClassClick,
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
                Spacer(modifier = Modifier.height(16.dp))
                SsingButton(
                    text = "강습 종료",
                    onClick = { onEndClick() },
                    style = SsingButtonStyle.BLUE,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Blue50),
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = with(density) { headerHeightPx.toDp() })
                    .background(
                        color = SSINGTheme.colors.backgroundNormal,
                        shape = RoundedCornerShape(
                            topStart = 12.dp,
                            topEnd = 12.dp,
                        ),
                    ),
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                item {
                    LessonBanner(
                        lessonBannerState = lessonBannerState,
                        lessonText = "남은 시간",
                        onBackClick = {},
                    )
                }

                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            SectionTitle(text = "강습 정보")
                            Spacer(modifier = Modifier.height(8.dp))
                            ClassInfoCard(
                                tags = ongoing.tags,
                                classTitle = ongoing.classTitle,
                                location = ongoing.location,
                                duration = ongoing.duration,
                                price = ongoing.price,
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Column {
                                SectionTitle(text = "강습생 정보")
                                Spacer(modifier = Modifier.height(8.dp))
                                ongoing.teams.forEachIndexed { index, team ->
                                    ConsumerInfoCard(
                                        isReady = team.isReady,
                                        nickname = team.teamNickname,
                                        participants = team.participants,
                                        price = team.price,
                                    )
                                    if (index != ongoing.teams.lastIndex) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    if (showReadyDialog) {
        SsingModal(
            onDismissRequest = { showReadyDialog = false },
            title = "강습을 종료할까요?",
            text = "강습을 종료하면 모든 참여자의 강습이\n종료 상태로 변경되어요",
            primaryText = "계속 진행하기",
            onPrimary = { showReadyDialog = false },
            secondaryText = "강습 종료하기",
            onSecondary = { showReadyDialog = false },  // 화면 넘어가도록 수정
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = SSINGTheme.typography.caption.sb12,
        color = SSINGTheme.colors.textAlternative,
    )
}

@Composable
private fun ClassInfoCard(
    tags: ImmutableList<String>,
    classTitle: String,
    location: String,
    duration: String,
    price: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = SSINGTheme.colors.backgroundNormal,
                shape = RoundedCornerShape(12.dp),
            )
            .border(
                width = 1.dp,
                color = SSINGTheme.colors.borderAlternative,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(16.dp),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            tags.forEach { tag ->
                SsingChip(text = tag, style = SsingChipStyle.GRAY)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = classTitle,
            style = SSINGTheme.typography.body.sb16,
            color = SSINGTheme.colors.textNormal,
        )
        Spacer(modifier = Modifier.height(8.dp))
        InfoRow(label = "강습 장소", value = location)
        InfoRow(label = "강습 시간", value = duration)
        InfoRow(label = "강습 가격", value = "₩ ${"%,d".format(price)}")
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = SSINGTheme.typography.caption.sb12,
            color = SSINGTheme.colors.textAlternative,
        )
        Text(
            text = value,
            style = SSINGTheme.typography.caption.sb14,
            color = SSINGTheme.colors.textNormal,
        )
    }
}

@Preview
@Composable
private fun LessonDetailOngoingScreenPreview() {
    SSINGTheme {
        LessonDetailOngoingScreen(
            ongoing = LessonDetailOngoingUiModel(
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
            onChatRoomClick = {},
            onEndClick = {},
            lessonBannerState = LessonBannerState.Ongoing(
                remainingTime = "2:59:59",
                elapsedTime = "59분",
            ),
        )
    }
}