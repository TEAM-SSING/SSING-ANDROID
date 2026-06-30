package com.ssing.core.ui.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ssing.core.ui.R

object PretendardFont {
    val Medium = FontFamily(Font(R.font.pretendard_medium))
    val Semibold = FontFamily(Font(R.font.pretendard_semibold))
}

@Immutable
data class SSINGTypography(
    val titleSb28: TextStyle,
    val titleSb24: TextStyle,
    val titleMd24: TextStyle,
    val titleSb22: TextStyle,
    val titleMd22: TextStyle,

    val bodySb20: TextStyle,
    val bodyMd20: TextStyle,
    val bodySb16: TextStyle,
    val bodyMd16: TextStyle,

    val captionSb14: TextStyle,
    val captionMd14: TextStyle,
    val captionSb12: TextStyle,
    val captionMd12: TextStyle,
)