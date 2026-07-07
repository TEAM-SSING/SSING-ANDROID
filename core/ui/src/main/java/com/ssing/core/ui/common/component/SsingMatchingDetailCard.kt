package com.ssing.core.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.R
import com.ssing.core.ui.designsystem.theme.Blue200
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**

 * @param stepLabel 현재 단계 라벨
 * @param tags 강습 태그 목록
 * @param nickname 팀장 닉네임
 * @param teamCount 팀장이 속한 팀의 인원 수
 * @param totalCount 전체 강습 인원 수
 * @param classDateTime 강습 일시
 * @param location 강습 장소
 * @param duration 강습 시간
 * @param maxCapacity 최대 인원
 * @param participants 강습 참여자 목록 (나이/성별 등)
 * @param isPaid 결제 완료 여부
 * @param price 예상 가격
 * @param equipmentStatus 장비 상태
 */
@Composable
fun SsingMatchingDetailCard(
    modifier: Modifier = Modifier,
    stepLabel: String? = null,
    stepLabelColor: Color = SSINGTheme.colors.textAlternative,
    tags: ImmutableList<String> = persistentListOf(),
    nickname: String = "",
    teamCount: Int? = null,
    totalCount: Int? = null,
    classDateTime: String = "",
    location: String = "",
    duration: String = "",
    maxCapacity: Int? = null,
    participants: ImmutableList<Participant> = persistentListOf(),
    isPaid: Boolean = false,
    price: Int? = null,
    equipmentStatus: String = "",
) {
    Column(
        modifier = modifier
            .border(width = 1.dp, color = Blue200, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(color = SSINGTheme.colors.backgroundNormal)
            .padding(16.dp),
    ) {
        if (stepLabel != null) {
            Text(
                text = stepLabel,
                style = SSINGTheme.typography.caption.sb12,
                color = stepLabelColor,
                modifier = Modifier.padding(bottom = 12.dp),
            )
        }

        if (tags.isNotEmpty()) {
            SsingTagChipRow(tags = tags, style = SsingChipStyle.DEEP_BLUE)
            Spacer(modifier = Modifier.height(4.dp))
        }

        if (nickname.isNotEmpty()) {
            SsingClassTitleRow(
                title = "${nickname}님 팀 ${teamCount ?: 0}명",
                totalCount = totalCount ?: 0,
            )
            HorizontalDivider(
                color = SSINGTheme.colors.borderDisabled,
                modifier = Modifier.padding(vertical = 8.dp),
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            if (classDateTime.isNotEmpty()) SsingInfoRow(label = "강습 일시", value = classDateTime)
            if (location.isNotEmpty()) SsingInfoRow(label = "강습 장소", value = location)
            if (duration.isNotEmpty()) SsingInfoRow(label = "강습 시간", value = duration)
            if (maxCapacity != null) SsingInfoRow(label = "최대 인원", value = "${maxCapacity}명")
            if (participants.isNotEmpty()) SsingParticipantsRow(participants = participants)
            if (price != null) SsingPriceRow(isPaid = isPaid, price = price)
            if (equipmentStatus.isNotEmpty()) SsingInfoRow(label = "장비상태", value = equipmentStatus)
        }
    }
}


/**
 * 강습 상세 정보 카드 / Small (취소/이력 조회용).
 *
 * @param tags 강습 태그 목록
 * @param teamNicknames 팀별 닉네임/인원 목록
 * @param totalCount 전체 강습 인원 수
 * @param duration 강습 시간
 * @param actualTimeRange 실제 강습 시간 범위
 * @param price 강습 가격
 * @param cancelDateTime 취소 일시
 * @param cancelSubject 취소 주체
 * @param cancelReason 취소 사유
 */
@Composable
fun SsingMatchingDetailCardSmall(
    tags: ImmutableList<String>,
    teamNicknames: ImmutableList<TeamNickname>,
    totalCount: Int,
    place: String,
    duration: String,
    actualTimeRange: String,
    price: Int,
    cancelDateTime: String,
    cancelSubject: String,
    cancelReason: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                color = SSINGTheme.colors.backgroundNormal,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(16.dp),
    ) {
        SsingTagChipRow(tags = tags, style = SsingChipStyle.GRAY)

        Spacer(modifier = Modifier.height(4.dp))

        val title = teamNicknames.joinToString(", ") {
            "${it.nickname}님 팀 ${it.teamCount}명"
        }
        SsingClassTitleRowSmall(title = title, totalCount = totalCount)

        Spacer(modifier = Modifier.height(4.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            SsingInfoRow(label = "강습 장소", value = place)
            SsingInfoRow(label = "강습 시간", value = duration)
            SsingInfoRow(label = "실제 강습 시간", value = actualTimeRange)
            SsingInfoRow(label = "강습 가격", value = "₩ ${"%,d".format(price)}")

            HorizontalDivider(
                color = SSINGTheme.colors.borderDisabled,
                modifier = Modifier.padding(vertical = 6.dp)
            )

            SsingInfoRow(label = "취소 일시", value = cancelDateTime)
            SsingInfoRow(label = "취소 주체", value = cancelSubject)
            SsingInfoRow(label = "취소 사유", value = cancelReason)
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
private fun SsingTagChipRow(
    tags: ImmutableList<String>,
    style: SsingChipStyle,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        tags.forEach { text ->
            SsingChip(text = text, style = style)
        }
    }
}

/** "결제완료" 뱃지 + 가격 표시 row. */
@Composable
private fun SsingPriceRow(isPaid: Boolean, price: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "예상 가격",
            style = SSINGTheme.typography.caption.sb12,
            color = SSINGTheme.colors.textAlternative
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isPaid) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_check_cirlcle_filled_sm_12),
                    contentDescription = null,
                    tint = SSINGTheme.colors.primaryNormal
                )

                Spacer(Modifier.width(4.dp))

                Text(
                    "결제완료",
                    style = SSINGTheme.typography.caption.sb12,
                    color = SSINGTheme.colors.primaryNormal,
                )

                Spacer(Modifier.width(8.dp))
            }
            Text(
                "₩ ${"%,d".format(price)}",
                style = SSINGTheme.typography.caption.sb14,
                color = SSINGTheme.colors.textNormal
            )
        }
    }
}

@Composable
private fun SsingParticipantsRow(participants: ImmutableList<Participant>) {
    val verticalAlignment = if (participants.size <= 3) {
        Alignment.CenterVertically
    } else {
        Alignment.Top
    }
    val maxItemsInEachRow = when (participants.size) {
        4 -> 2
        else -> 3
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = verticalAlignment,
    ) {
        Text(
            text = "강습 인원",
            style = SSINGTheme.typography.caption.sb12,
            color = SSINGTheme.colors.textAlternative,
        )
        FlowRow(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            maxItemsInEachRow = maxItemsInEachRow,
        ) {
            participants.forEach { participant ->
                SsingChip(
                    text = "${participant.age}세 ${participant.gender.label}",
                    style = SsingChipStyle.GRAY,
                )
            }
        }
    }
}

private val Gender.label: String
    get() = when (this) {
        Gender.MALE -> "남"
        Gender.FEMALE -> "여"
    }

@Composable
private fun SsingClassTitleRow(title: String, totalCount: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = SSINGTheme.typography.body.sb20,
            color = SSINGTheme.colors.textNormal,
        )
        Text(
            text = "총 ${totalCount}명",
            style = SSINGTheme.typography.caption.sb14,
            color = SSINGTheme.colors.textAlternative,
        )
    }
}

@Composable
private fun SsingClassTitleRowSmall(title: String, totalCount: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = SSINGTheme.typography.caption.sb14,
            color = SSINGTheme.colors.textNormal,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = "총 ${totalCount}명",
            style = SSINGTheme.typography.caption.sb14,
            color = SSINGTheme.colors.textAlternative,
        )
    }
}

/** 팀 단위 닉네임/인원 정보. Small 카드에서 여러 팀을 나열할 때 사용. */
@Immutable
data class TeamNickname(
    val nickname: String,
    val teamCount: Int,
)

@Immutable
data class Participant(
    val age: Int,
    val gender: Gender,
)

enum class Gender { MALE, FEMALE }

@Preview
@Composable
private fun SsingClassDetailCardPreview() {
    SSINGTheme {
        SsingMatchingDetailCard(
            tags = persistentListOf("하이원", "스노보드", "처음타요"),
            nickname = "김OO",
            teamCount = 0,
            totalCount = 0,
            classDateTime = "0월 0일 오전 00:00",
            location = "OOO 리조트",
            duration = "0시간",
            maxCapacity = 0,
            participants = persistentListOf(
                Participant(11, Gender.MALE),
                Participant(11, Gender.MALE),
                Participant(9, Gender.FEMALE),
            ),
            isPaid = true,
            price = 0,
            equipmentStatus = "착용 완료",
            stepLabel = "현재 단계",
        )
    }
}

@Preview
@Composable
private fun SsingClassDetailCardSmallPreview() {
    SSINGTheme {
        SsingMatchingDetailCardSmall(
            tags = persistentListOf("스노보드", "자격증이 있어요"),
            teamNicknames = persistentListOf(
                TeamNickname("김남자", 1),
                TeamNickname("김여자", 1),
                TeamNickname("김야웅이", 1),
                TeamNickname("강아지", 1),
            ),
            totalCount = 4,
            place = "000 리조트",
            duration = "0시간",
            actualTimeRange = "00:00~00:00 (0시간)",
            price = 0,
            cancelDateTime = "00월 00일 O요일 00:00",
            cancelSubject = "취소 주체",
            cancelReason = "취소 사유",
        )
    }
}
