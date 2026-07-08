package instructorlessondetail.screen

import android.R.attr.duration
import androidx.compose.foundation.Image
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import com.ssing.core.ui.common.component.ConsumerInfoCard
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingChip
import com.ssing.core.ui.common.component.SsingChipStyle
import com.ssing.core.ui.common.component.SsingModal
import com.ssing.core.ui.designsystem.theme.Blue50
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import instructorlessondetail.model.LessonDetailDuringUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LessonDetailDuringScreen(
    during: LessonDetailDuringUiModel,
    onBackClick: () -> Unit,
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
                    TimeHeader(
                        modifier = Modifier.onSizeChanged { size ->
                            headerHeightPx = size.height
                        },
                    )
                }

                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            SectionTitle(text = "강습 정보")
                            Spacer(modifier = Modifier.height(8.dp))
                            ClassInfoCard(
                                tags = during.tags,
                                classTitle = during.classTitle,
                                location = during.location,
                                duration = during.duration,
                                price = during.price,
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Column {
                                SectionTitle(text = "강습생 정보")
                                Spacer(modifier = Modifier.height(8.dp))
                                during.teams.forEachIndexed { index, team ->
                                    ConsumerInfoCard(
                                        isReady = team.isReady,
                                        nickname = team.teamNickname,
                                        participants = team.participants,
                                        price = team.price,
                                    )
                                    if (index != during.teams.lastIndex) {
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
private fun TimeHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = Blue50)
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier.background(color = Blue50)
            ) {
                Text(
                    text = "남은 시간",
                    style = SSINGTheme.typography.body.sb16,
                    color = SSINGTheme.colors.textNormal,
                )

                Text(
                    text = "02:59:59", // 수정
                    style = SSINGTheme.typography.title.sb32,
                    color = SSINGTheme.colors.textNormal,
                )

                Text(
                    text = "강습 시작 후 59분 경과", // 수정
                    style = SSINGTheme.typography.caption.md14,
                    color = SSINGTheme.colors.textAlternative,
                )
            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.img_clock),
                    contentDescription = null,
                )
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
private fun LessonDetailDuringScreenPreview() {
    SSINGTheme {
        LessonDetailDuringScreen(
            during = LessonDetailDuringUiModel(
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
            onBackClick = {},
            onCancelClassClick = {},
            onChatRoomClick = {},
            onEndClick = {},
        )
    }
}

@Preview
@Composable
private fun ReadyConfirmModalPreview() {
    SSINGTheme {
        SsingModal(
            onDismissRequest = {},
            title = "강습을 종료할까요?",
            text = "강습을 종료하면 모든 참여자의 강습이\n종료 상태로 변경되어요",
            primaryText = "계속 진행하기",
            secondaryText = "강습 종료하기",
            onPrimary = {},
            onSecondary = {},
        )
    }
}