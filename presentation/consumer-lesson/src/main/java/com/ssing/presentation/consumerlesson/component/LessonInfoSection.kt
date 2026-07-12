package com.ssing.presentation.consumerlesson.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.ssing.core.ui.common.component.SsingMatchingDetailCardSmall
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.presentation.consumerlesson.model.LessonInfoUiModel
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun LessonInfoSection(
    lessonInfo: LessonInfoUiModel?,
    modifier: Modifier = Modifier,
    cancelDateTime: String = "",
    cancelSubject: String = "",
    cancelReason: String = "",
    actualTimeRange: String = "",
) {
    lessonInfo?.let { info ->
        ContentSection(
            titleText = "강습 정보",
            modifier = modifier,
        ) {
            SsingMatchingDetailCardSmall(
                tags = info.tags,
                teamNicknames = info.teamNicknames,
                totalCount = info.totalCount,
                place = info.place,
                duration = info.duration,
                actualTimeRange = actualTimeRange,
                price = info.price,
                cancelDateTime = cancelDateTime,
                cancelSubject = cancelSubject,
                cancelReason = cancelReason,
            )
        }
    }
}

private val sampleLessonInfo = LessonInfoUiModel(
    tags = persistentListOf("스노보드", "자격증이 있어요"),
    teamNicknames = persistentListOf("김멍멍", "김야옹"),
    totalCount = 2,
    place = "000 리조트",
    duration = "2시간",
    price = 500000,
)

private data class LessonInfoSectionPreviewParam(
    val actualTimeRange: String = "",
    val cancelDateTime: String = "",
    val cancelSubject: String = "",
    val cancelReason: String = "",
)

private class LessonInfoSectionPreviewParameterProvider :
    PreviewParameterProvider<LessonInfoSectionPreviewParam> {
    override val values = sequenceOf(
        LessonInfoSectionPreviewParam(),
        LessonInfoSectionPreviewParam(actualTimeRange = "14:00 - 16:00 (2시간)"),
        LessonInfoSectionPreviewParam(
            cancelDateTime = "2026.07.10 14:00",
            cancelSubject = "강습생",
            cancelReason = "일정 변경",
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun LessonInfoSectionPreview(
    @PreviewParameter(LessonInfoSectionPreviewParameterProvider::class) param: LessonInfoSectionPreviewParam,
) {
    SSINGTheme {
        LessonInfoSection(
            lessonInfo = sampleLessonInfo,
            actualTimeRange = param.actualTimeRange,
            cancelDateTime = param.cancelDateTime,
            cancelSubject = param.cancelSubject,
            cancelReason = param.cancelReason,
        )
    }
}