package com.ssing.core.ui.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
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
    val Bold = FontFamily(Font(R.font.pretendard_bold))
}

sealed interface TypographyTokens {
    @Immutable
    data class Title(
        val titleB24: TextStyle,
        val titleSb24: TextStyle,
        val titleB22: TextStyle,
        val titleSb22: TextStyle,
        val titleB20: TextStyle,
        val titleB18: TextStyle,
        val titleB16: TextStyle,
    ) : TypographyTokens

    @Immutable
    data class Body(
        val bodySb20: TextStyle,
        val bodyMd20: TextStyle,
        val bodySb16: TextStyle,
        val bodyMd16: TextStyle,
    ) : TypographyTokens

    @Immutable
    data class Caption(
        val captionSb14: TextStyle,
        val captionMd14: TextStyle,
        val captionSb12: TextStyle,
        val captionMd12: TextStyle,
    ) : TypographyTokens
}

private fun ssingTextStyle(
    fontFamily: FontFamily,
    fontSize: TextUnit,
    letterSpacing: TextUnit = (-0.005).em,
): TextStyle = TextStyle(
    fontFamily = fontFamily,
    fontSize = fontSize,
    lineHeight = 1.2.em,
    letterSpacing = letterSpacing,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None,
    )
)

@Immutable
data class SSINGTypography(
    val title: TypographyTokens.Title,
    val body: TypographyTokens.Body,
    val caption: TypographyTokens.Caption,
)

val defaultSsingTypography = SSINGTypography(
    title = TypographyTokens.Title(
        titleB24 = ssingTextStyle(
            fontFamily = PretendardFont.Bold,
            fontSize = 24.sp,
        ),
        titleSb24 = ssingTextStyle(
            fontFamily = PretendardFont.Semibold,
            fontSize = 24.sp,
        ),
        titleB22 = ssingTextStyle(
            fontFamily = PretendardFont.Bold,
            fontSize = 22.sp,
        ),
        titleSb22 = ssingTextStyle(
            fontFamily = PretendardFont.Semibold,
            fontSize = 22.sp,
        ),
        titleB20 = ssingTextStyle(
            fontFamily = PretendardFont.Bold,
            fontSize = 20.sp,
        ),
        titleB18 = ssingTextStyle(
            fontFamily = PretendardFont.Bold,
            fontSize = 18.sp,
        ),
        titleB16 = ssingTextStyle(
            fontFamily = PretendardFont.Bold,
            fontSize = 16.sp,
        ),
    ),
    body = TypographyTokens.Body(
        bodySb20 = ssingTextStyle(
            fontFamily = PretendardFont.Semibold,
            fontSize = 20.sp,
        ),
        bodyMd20 = ssingTextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 20.sp,
        ),
        bodySb16 = ssingTextStyle(
            fontFamily = PretendardFont.Semibold,
            fontSize = 16.sp,
        ),
        bodyMd16 = ssingTextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 16.sp,
        ),
    ),
    caption = TypographyTokens.Caption(
        captionSb14 = ssingTextStyle(
            fontFamily = PretendardFont.Semibold,
            fontSize = 14.sp,
        ),
        captionMd14 = ssingTextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 14.sp,
        ),
        captionSb12 = ssingTextStyle(
            fontFamily = PretendardFont.Semibold,
            fontSize = 12.sp,
        ),
        captionMd12 = ssingTextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 12.sp,
            letterSpacing = 0.em,
        ),
    ),
)

val localSsingTypography = staticCompositionLocalOf { defaultSsingTypography }
