package com.ssing.core.ui.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
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

private fun SSINGTextStyle(
    fontFamily: FontFamily,
    fontSize: TextUnit,
    letterSpacing: TextUnit = (-0.005).em,
): TextStyle = TextStyle(
    fontFamily = fontFamily,
    fontSize = fontSize,
    lineHeight = fontSize * 1.2f,
    letterSpacing = letterSpacing,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None,
    )
)

val defaultSSINGTypography = SSINGTypography(
    titleSb28 = SSINGTextStyle(
        fontFamily = PretendardFont.Semibold,
        fontSize = 28.sp,
    ),
    titleSb24 = SSINGTextStyle(
        fontFamily = PretendardFont.Semibold,
        fontSize = 24.sp,
    ),
    titleMd24 = SSINGTextStyle(
        fontFamily = PretendardFont.Medium,
        fontSize = 24.sp,
    ),
    titleSb22 = SSINGTextStyle(
        fontFamily = PretendardFont.Semibold,
        fontSize = 22.sp,
    ),
    titleMd22 = SSINGTextStyle(
        fontFamily = PretendardFont.Medium,
        fontSize = 22.sp,
    ),

    bodySb20 = SSINGTextStyle(
        fontFamily = PretendardFont.Semibold,
        fontSize = 20.sp,
    ),
    bodyMd20 = SSINGTextStyle(
        fontFamily = PretendardFont.Medium,
        fontSize = 20.sp,
    ),
    bodySb16 = SSINGTextStyle(
        fontFamily = PretendardFont.Semibold,
        fontSize = 16.sp,
    ),
    bodyMd16 = SSINGTextStyle(
        fontFamily = PretendardFont.Medium,
        fontSize = 16.sp,
    ),

    captionSb14 = SSINGTextStyle(
        fontFamily = PretendardFont.Semibold,
        fontSize = 14.sp,
    ),
    captionMd14 = SSINGTextStyle(
        fontFamily = PretendardFont.Medium,
        fontSize = 14.sp,
    ),
    captionSb12 = SSINGTextStyle(
        fontFamily = PretendardFont.Semibold,
        fontSize = 12.sp,
    ),
    captionMd12 = SSINGTextStyle(
        fontFamily = PretendardFont.Medium,
        fontSize = 12.sp,
        letterSpacing = 0.em,
    )
)