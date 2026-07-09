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
        val sb32: TextStyle,
        val b24: TextStyle,
        val sb24: TextStyle,
        val b22: TextStyle,
        val sb22: TextStyle,
        val b20: TextStyle,
        val b18: TextStyle,
        val b16: TextStyle,
    ) : TypographyTokens

    @Immutable
    data class Body(
        val sb20: TextStyle,
        val md20: TextStyle,
        val sb16: TextStyle,
        val md16: TextStyle,
    ) : TypographyTokens

    @Immutable
    data class Caption(
        val sb14: TextStyle,
        val md14: TextStyle,
        val sb12: TextStyle,
        val md12: TextStyle,
        val sb11: TextStyle,
        val md11: TextStyle,
    ) : TypographyTokens
}

private fun ssingTextStyle(
    fontFamily: FontFamily,
    fontSize: TextUnit,
    letterSpacing: TextUnit = (-0.005).em,
): TextStyle = TextStyle(
    fontFamily = fontFamily,
    fontSize = fontSize,
    lineHeight = 1.4.em,
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
        sb32 = ssingTextStyle(
            fontFamily = PretendardFont.Semibold,
            fontSize = 32.sp,
        ),
        b24 = ssingTextStyle(
            fontFamily = PretendardFont.Bold,
            fontSize = 24.sp,
        ),
        sb24 = ssingTextStyle(
            fontFamily = PretendardFont.Semibold,
            fontSize = 24.sp,
        ),
        b22 = ssingTextStyle(
            fontFamily = PretendardFont.Bold,
            fontSize = 22.sp,
        ),
        sb22 = ssingTextStyle(
            fontFamily = PretendardFont.Semibold,
            fontSize = 22.sp,
        ),
        b20 = ssingTextStyle(
            fontFamily = PretendardFont.Bold,
            fontSize = 20.sp,
        ),
        b18 = ssingTextStyle(
            fontFamily = PretendardFont.Bold,
            fontSize = 18.sp,
        ),
        b16 = ssingTextStyle(
            fontFamily = PretendardFont.Bold,
            fontSize = 16.sp,
        ),
    ),
    body = TypographyTokens.Body(
        sb20 = ssingTextStyle(
            fontFamily = PretendardFont.Semibold,
            fontSize = 20.sp,
        ),
        md20 = ssingTextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 20.sp,
        ),
        sb16 = ssingTextStyle(
            fontFamily = PretendardFont.Semibold,
            fontSize = 16.sp,
        ),
        md16 = ssingTextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 16.sp,
        ),
    ),
    caption = TypographyTokens.Caption(
        sb14 = ssingTextStyle(
            fontFamily = PretendardFont.Semibold,
            fontSize = 14.sp,
        ),
        md14 = ssingTextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 14.sp,
        ),
        sb12 = ssingTextStyle(
            fontFamily = PretendardFont.Semibold,
            fontSize = 12.sp,
        ),
        md12 = ssingTextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 12.sp,
        ),
        sb11 = ssingTextStyle(
            fontFamily = PretendardFont.Semibold,
            fontSize = 11.sp,
        ),
        md11 = ssingTextStyle(
            fontFamily = PretendardFont.Medium,
            fontSize = 11.sp,
        ),
    ),
)

val localSsingTypography = staticCompositionLocalOf { defaultSsingTypography }
