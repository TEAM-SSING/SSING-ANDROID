package com.ssing.core.ui.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import java.util.Locale

/**
 * 강습 상세 정보 카드 (Full).
 *
 * @param state 카드에 표시할 강습 상세 정보
 * @param onContinueClick "이어보기" 버튼 클릭 콜백
 */
@Composable
fun SsingClassDetailCard(
    state: ClassDetailUiState,
    onContinueClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SSINGTheme.colors.backgroundNormal),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "현재 단계",
                style = SSINGTheme.typography.caption.sb12,
                color = SSINGTheme.colors.textAlternative,
                modifier = Modifier.padding(bottom = 12.dp),
            )
            SsingTagChipRow(tags = state.tags)

            SsingClassTitleRow(
                name = "${state.nickname}님 팀 ${state.teamCount}명",
                totalCount = state.totalCount
            )

            HorizontalDivider(
                color = SSINGTheme.colors.backgroundAlternative,
                modifier = Modifier.padding(vertical = 4.dp),
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                SsingInfoRow(label = "강습 일시", value = state.classDateTime)
                SsingInfoRow(label = "강습 장소", value = state.location)
                SsingInfoRow(label = "강습 시간", value = state.duration)
                SsingInfoRow(label = "최대 인원", value = "${state.maxCapacity}명")
                SsingParticipantsRow(label = "강습 인원", participants = state.participants)
                SsingPriceRow(label = "예상 가격", isPaid = state.isPaid, price = state.price)
                SsingInfoRow(label = "장비상태", value = state.equipmentStatus)
            }
            SsingButton(
                text = "이어보기",
                onClick = onContinueClick,
                style = SsingButtonStyle.BLUE,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .height(52.dp),
            )
        }
    }
}

/**
 * 강습 상세 정보 카드 / Small (취소/이력 조회용).
 *
 * @param state 카드에 표시할 강습 상세 정보 (Small)
 */
@Composable
fun SsingClassDetailCardSmall(
    state: ClassDetailSmallUiState,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SSINGTheme.colors.backgroundNormal),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            SsingTagChipRowSmall(tags = state.tags)

            SsingClassTitleRow(
                name = "${state.nickname}님",
                totalCount = state.totalCount,
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                SsingInfoRow(label = "강습 인원", value = "총 ${state.teamCount}명")
                SsingInfoRow(label = "강습 시간", value = state.duration)
                SsingInfoRow(label = "실제 강습 시간", value = state.actualTimeRange)
                SsingPriceRow(label = "예상 가격", isPaid = state.isPaid, price = state.price)

                HorizontalDivider(
                    color = SSINGTheme.colors.backgroundAlternative,
                    modifier = Modifier.padding(vertical = 6.dp)
                )

                SsingInfoRow(label = "취소 일시", value = state.cancelDateTime)
                SsingInfoRow(label = "취소 주체", value = state.cancelSubject)
                SsingInfoRow(label = "취소 사유", value = state.cancelReason)
            }
        }
    }
}

/** 라벨-값 한 줄 표시 (강습 일시, 장소 등 공통 row). */
@Composable
private fun SsingInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            label,
            style = SSINGTheme.typography.caption.sb12,
            color = SSINGTheme.colors.textAlternative,
        )
        Text(
            value,
            style = SSINGTheme.typography.caption.sb14,
            color = SSINGTheme.colors.textNormal
        )
    }
}

/** 태그 칩 한 줄 나열. 기존 공용 Chip 컴포넌트 있으면 이걸로 교체. */
@Composable
private fun SsingTagChipRow(tags: List<ChipUiModel>) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        tags.forEach { chip ->
            SsingChip(text = chip.text, style = SsingChipStyle.DEEP_BLUE)
        }
    }
}

@Composable
private fun SsingTagChipRowSmall(tags: List<ChipUiModel>) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        tags.forEach { chip ->
            SsingChip(text = chip.text, style = SsingChipStyle.GRAY)
        }
    }
}

/** "결제완료" 뱃지 + 가격 표시 row. */
@Composable
private fun SsingPriceRow(label: String, isPaid: Boolean, price: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            label,
            style = SSINGTheme.typography.caption.sb12,
            color = SSINGTheme.colors.textAlternative
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isPaid) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = SSINGTheme.colors.primaryNormal,
                    modifier = Modifier.width(16.dp),
                )
                Text(
                    "  결제완료  ",
                    style = SSINGTheme.typography.caption.sb12,
                    color = SSINGTheme.colors.primaryNormal,
                )
            }
            Text(
                "₩ ${String.format(Locale.KOREA, "%,d", price)}",
                style = SSINGTheme.typography.caption.sb14,
                color = SSINGTheme.colors.textNormal
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SsingParticipantsRow(label: String, participants: List<String>) {
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
        FlowRow(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            maxItemsInEachRow = 3,
        ) {
            participants.forEach { participant ->
                SsingChip(text = participant, style = SsingChipStyle.GRAY)
            }
        }
    }
}

@Composable
private fun SsingClassTitleRow(name: String, totalCount: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
            style = SSINGTheme.typography.body.sb20,
            color = SSINGTheme.colors.textNormal,
            modifier = Modifier.padding(end = 8.dp, top = 4.dp),
        )
        Text(
            text = "총 ${totalCount}명",
            style = SSINGTheme.typography.caption.sb14,
            color = SSINGTheme.colors.textAlternative,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .weight(1f),
        )
    }
}


@Immutable
data class ChipUiModel(
    val text: String,
    val style: SsingChipStyle,
)

@Immutable
data class ClassDetailUiState(
    val tags: List<ChipUiModel>,
    val nickname: String,
    val teamCount: Int,
    val totalCount: Int,
    val classDateTime: String,
    val location: String,
    val duration: String,
    val maxCapacity: Int,
    val participants: List<String>,
    val isPaid: Boolean,
    val price: Int,
    val equipmentStatus: String,
)

@Immutable
data class ClassDetailSmallUiState(
    val tags: List<ChipUiModel>,
    val nickname: String,
    val totalCount: Int,
    val teamCount: Int,
    val duration: String,
    val actualTimeRange: String,
    val isPaid: Boolean,
    val price: Int,
    val cancelDateTime: String,
    val cancelSubject: String,
    val cancelReason: String,
)

@Preview
@Composable
private fun SsingClassDetailCardPreview() {
    SSINGTheme {
        SsingClassDetailCard(
            state = ClassDetailUiState(
                tags = listOf(
                    ChipUiModel("하이원", SsingChipStyle.DEEP_BLUE),
                    ChipUiModel("스노보드", SsingChipStyle.DEEP_BLUE),
                    ChipUiModel("처음타요", SsingChipStyle.DEEP_BLUE),
                ),
                nickname = "김OO",
                teamCount = 0,
                totalCount = 0,
                classDateTime = "0월 0일 오전 00:00",
                location = "OOO 리조트",
                duration = "0시간",
                maxCapacity = 0,
                participants = listOf("11세 남", "11세 남", "9세 여", "9세 여"),
                isPaid = true,
                price = 0, // 수정
                equipmentStatus = "착용 완료",
            ),
            onContinueClick = {},
        )
    }
}

@Preview
@Composable
private fun SsingClassDetailCardSmallPreview() {
    SSINGTheme {
        SsingClassDetailCardSmall(
            state = ClassDetailSmallUiState(
                tags = listOf(
                    ChipUiModel("지산포레스트", SsingChipStyle.GRAY),
                    ChipUiModel("스노보드", SsingChipStyle.GRAY),
                    ChipUiModel("자격증이 있어요", SsingChipStyle.GRAY),
                ),
                nickname = "김OO",
                totalCount = 0,
                teamCount = 0,
                duration = "0시간",
                actualTimeRange = "00:00~00:00 (0시간)",
                isPaid = true,
                price = 0,
                cancelDateTime = "00월 00일 O요일 00:00",
                cancelSubject = "취소 주체",
                cancelReason = "취소 사유",
            ),
        )
    }
}