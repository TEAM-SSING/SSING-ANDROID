package com.ssing.instructor.matchingdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.R
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingChip
import com.ssing.core.ui.common.component.SsingChipStyle
import com.ssing.core.ui.designsystem.theme.Blue50
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * 강습 상세 (강습 전) 화면.
 *
 * @param progress 준비 완료한 인원 / 전체 인원 (예: 1 / 6)
 * @param totalProgress 전체 인원
 * @param tags 강습 태그
 * @param classTitle 팀 타이틀 (예: "김OO님 팀, 홍지민님 팀 총 5명")
 * @param location 강습 장소
 * @param duration 강습 시간
 * @param price 강습 가격
 * @param teams 강습생 정보 (팀별 카드 리스트)
 * @param onBackClick 뒤로가기 클릭
 * @param onCancelClassClick 강습 취소 클릭
 * @param onChatRoomClick 채팅방 클릭
 * @param onReadyClick 강습 준비 완료 클릭
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructorClassPreparationScreen(
    progress: Int,
    totalProgress: Int,
    tags: ImmutableList<String>,
    classTitle: String,
    location: String,
    duration: String,
    price: Int,
    teams: ImmutableList<TeamParticipantsInfo>,
    onBackClick: () -> Unit,
    onCancelClassClick: () -> Unit,
    onChatRoomClick: () -> Unit,
    onReadyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.background(Blue50),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "강습 상세",
                        style = SSINGTheme.typography.body.sb16,
                        color = SSINGTheme.colors.textNormal,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left),
                            contentDescription = "뒤로가기",
                            tint = SSINGTheme.colors.textNormal,
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Blue50,
                ),
            )
        },
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
                        text = "강습 취소",
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
                Spacer(modifier = Modifier.height(8.dp))
                SsingButton(
                    text = "강습 준비 완료",
                    onClick = onReadyClick,
                    style = SsingButtonStyle.BLUE,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            item {
                PreparationHeader(progress = progress, totalProgress = totalProgress)
            }

            item {
                SectionTitle(text = "강습 정보")
                Spacer(modifier = Modifier.height(8.dp))
                ClassInfoCard(
                    tags = tags,
                    classTitle = classTitle,
                    location = location,
                    duration = duration,
                    price = price,
                )
            }

            item {
                SectionTitle(text = "강습생 정보")
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(teams) { team ->
                TeamParticipantsCard(team = team)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun PreparationHeader(progress: Int, totalProgress: Int) {
    val isInstructorDone = progress >= 1
    val participantTotal = totalProgress - 1
    val participantDoneCount = (progress - 1).coerceAtLeast(0)
    val density = LocalDensity.current
    var instructorImageHeightPx by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = SSINGTheme.colors.backgroundNormal,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(16.dp),
    ) {
        Text(
            text = "강습을 준비해주세요",
            style = SSINGTheme.typography.body.sb20,
            color = SSINGTheme.colors.textNormal,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "강습생과 강사가 모두 강습 시작을 선택하면\n강습중 상태로 변경돼요",
            style = SSINGTheme.typography.caption.md14,
            color = SSINGTheme.colors.textAlternative,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.End) {

            Text(
                text = "$progress / $totalProgress",
                style = SSINGTheme.typography.caption.sb14,
                color = SSINGTheme.colors.primaryNormal,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                painter = painterResource(
                    id = if (isInstructorDone) {
                        R.drawable.img_instructor_default
                    } else {
                        R.drawable.img_instructor_ready
                    }
                ),
                contentDescription = "강사 준비 완료 여부",
                modifier = Modifier
                    .onSizeChanged { size ->
                    instructorImageHeightPx = size.height
                },
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(
                        with(density) {
                            (instructorImageHeightPx.toDp() - 8.dp).coerceAtLeast(0.dp)
                        }
                    )
                    .background(SSINGTheme.colors.primaryAlternative)
                    .align(Alignment.CenterVertically),
            )

            Spacer(modifier = Modifier.width(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(participantTotal) { index ->
                    val isParticipantDone = index < participantDoneCount

                    Image(
                        painter = painterResource(
                            id = if (isParticipantDone) {
                                R.drawable.img_waiting_default
                            } else {
                                R.drawable.img_waiting_ready
                            }
                        ),
                        contentDescription = "강습생 준비 완료 여부",
                    )
                }
            }
        }
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

/** 강습생 정보 화면에서 팀 단위로 보여줄 데이터. */
@Immutable
data class TeamParticipantsInfo(
    val teamNickname: String,
    val teamCount: Int,
    val participants: ImmutableList<String>,
    val price: Int,
)

@Composable
private fun TeamParticipantsCard(team: TeamParticipantsInfo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = SSINGTheme.colors.backgroundNormal,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "${team.teamNickname}님 팀 ${team.teamCount}명",
                style = SSINGTheme.typography.body.sb16,
                color = SSINGTheme.colors.textNormal,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = "₩ ${"%,d".format(team.price)}",
                style = SSINGTheme.typography.caption.sb14,
                color = SSINGTheme.colors.textNormal,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            team.participants.forEach { participant ->
                SsingChip(text = participant, style = SsingChipStyle.GRAY)
            }
        }
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
private fun InstructorClassPreparationScreenPreview() {
    SSINGTheme {
        InstructorClassPreparationScreen(
            progress = 1,
            totalProgress = 6,
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
                ),
                TeamParticipantsInfo(
                    teamNickname = "김OO",
                    teamCount = 0,
                    participants = persistentListOf("38세 남", "12세 여", "9세 남"),
                    price = 0,
                ),
            ),
            onBackClick = {},
            onCancelClassClick = {},
            onChatRoomClick = {},
            onReadyClick = {},
        )
    }
}