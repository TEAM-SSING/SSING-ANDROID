package instructorlessondetail.screen

import androidx.compose.foundation.background
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.R
import com.ssing.core.ui.common.component.ConsumerInfoCard
import com.ssing.core.ui.common.component.LessonBanner
import com.ssing.core.ui.common.component.LessonBannerState
import com.ssing.core.ui.common.component.SsingButton
import com.ssing.core.ui.common.component.SsingButtonStyle
import com.ssing.core.ui.common.component.SsingMatchingDetailCardSmall
import com.ssing.core.ui.common.component.TeamNickname
import com.ssing.core.ui.designsystem.theme.Blue50
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import instructorlessondetail.model.LessonDetailCompletedUiModel
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LessonDetailCompletedScreen(
    completed: LessonDetailCompletedUiModel,
    lessonBannerState: LessonBannerState,
    onReviewClick: () -> Unit,
    onChatRoomClick: () -> Unit,
    onEndClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    var headerHeightPx by remember { mutableIntStateOf(0) }

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
                        text = "채팅방",
                        onClick = onChatRoomClick,
                        style = SsingButtonStyle.GRAY,
                        modifier = Modifier.weight(1f),
                    )
                    SsingButton(
                        text = "강습 후기",
                        onClick = onReviewClick,
                        style = SsingButtonStyle.GRAY,
                        modifier = Modifier.weight(1f),
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                SsingButton(
                    text = "정산 보기",
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
                            SsingMatchingDetailCardSmall(
                                tags = persistentListOf("스노보드", "자격증이 있어요"),
                                teamNicknames = persistentListOf(
                                    TeamNickname("김OO", 1),
                                    TeamNickname("홍지민", 1),
                                ),
                                totalCount = 4,
                                place = "000 리조트",
                                duration = "0시간",
                                price = 0,
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Column {
                                SectionTitle(text = "강습생 정보")
                                Spacer(modifier = Modifier.height(8.dp))
                                completed.teams.forEachIndexed { index, team ->
                                    ConsumerInfoCard(
                                        isReady = team.isReady ?: false,
                                        nickname = team.teamNickname,
                                        participants = team.participants,
                                        price = team.price,
                                    )
                                    if (index != completed.teams.lastIndex) {
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
}


@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = SSINGTheme.typography.caption.sb12,
        color = SSINGTheme.colors.textAlternative,
    )
}



@Preview
@Composable
private fun LessonDetailCompletedScreenPreview() {
    SSINGTheme {
        LessonDetailCompletedScreen(
            completed = LessonDetailCompletedUiModel(
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
            onReviewClick = {},
            onChatRoomClick = {},
            onEndClick = {},
            onBackClick = {},
            lessonBannerState = LessonBannerState.Completed(
                lessonDate = "2026년 12월 31일",
            ),
        )
    }
}