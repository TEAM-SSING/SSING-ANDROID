package com.ssing.presentation.instructorlessondetail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssing.core.ui.common.component.SsingMatchingDetailCardSmall
import kotlinx.collections.immutable.ImmutableList

@Composable
fun LessonInfoSection(
    tags: ImmutableList<String>,
    nicknames: ImmutableList<String>,
    totalCount: Int,
    place: String,
    duration: String,
    price: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        SectionTitle(text = "강습 정보")
        Spacer(modifier = Modifier.height(8.dp))
        SsingMatchingDetailCardSmall(
            tags = tags,
            teamNicknames = nicknames,
            totalCount = totalCount,
            place = place,
            duration = duration,
            price = price,
        )
    }
}