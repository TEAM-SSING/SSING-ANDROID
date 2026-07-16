package com.ssing.presentation.consumerlesson.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ssing.core.ui.common.component.InstructorProfileButton
import com.ssing.core.ui.designsystem.theme.SSINGTheme
import com.ssing.presentation.consumerlesson.model.InstructorProfileUiModel

@Composable
internal fun InstructorProfileSection(
    instructorProfile: InstructorProfileUiModel?,
    onInstructorProfileClick: () -> Unit,
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
                onClick = onInstructorProfileClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InstructorProfileSectionPreview() {
    SSINGTheme {
        InstructorProfileSection(
            instructorProfile = InstructorProfileUiModel(
                name = "김어흥 강사",
                age = 27,
                gender = "남",
                level = "grade1",
                imageUrl = "",
            ),
            onInstructorProfileClick = {},
        )
    }
}