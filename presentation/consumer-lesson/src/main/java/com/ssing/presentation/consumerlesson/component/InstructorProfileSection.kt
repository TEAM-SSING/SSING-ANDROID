package com.ssing.presentation.consumerlesson.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ssing.core.ui.common.component.InstructorProfileButton
import com.ssing.presentation.consumerlesson.model.InstructorProfileUiModel

@Composable
internal fun InstructorProfileSection(
    instructorProfile: InstructorProfileUiModel?,
    modifier: Modifier = Modifier,
) {
    instructorProfile?.let { info ->
        ContentSection(
            titleText = "강사 프로필",
            modifier = modifier,
        ) {
            InstructorProfileButton(
                name = info.name,
                age = info.age,
                gender = info.gender,
                level = info.level,
                imageUrl = info.imageUrl,
                onClick = {},
            )
        }
    }
}