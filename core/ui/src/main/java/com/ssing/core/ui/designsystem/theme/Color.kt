package com.ssing.core.ui.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Blue50 = Color(0xFFEFF4FF)
val Blue100 = Color(0xFFDCE8FF)
val Blue200 = Color(0xFFB3D0FF)
val Blue300 = Color(0xFF82B4FF)
val Blue400 = Color(0xFF529FFF)
val Blue500 = Color(0xFF2C8BFF)
val Blue600 = Color(0xFF0051FF)
val Blue700 = Color(0xFF003EC2)
val Blue800 = Color(0xFF002B85)
val Blue900 = Color(0xFF00184A)

val Orange50 = Color(0xFFFFF6E6)
val Orange100 = Color(0xFFFFE6BE)
val Orange200 = Color(0xFFFFD08A)
val Orange300 = Color(0xFFFFB952)
val Orange400 = Color(0xFFFFAC2E)
val Orange500 = Color(0xFFFF9200)
val Orange600 = Color(0xFFDB7200)
val Orange700 = Color(0xFFB35300)
val Orange800 = Color(0xFF853600)
val Orange900 = Color(0xFF521E00)

val Green50 = Color(0xFFE5FAF0)
val Green100 = Color(0xFFC0F4D9)
val Green200 = Color(0xFF86ECB6)
val Green300 = Color(0xFF42E28C)
val Green400 = Color(0xFF00CD55)
val Green500 = Color(0xFF00A845)
val Green600 = Color(0xFF008F3A)
val Green700 = Color(0xFF006B2B)
val Green800 = Color(0xFF00471D)
val Green900 = Color(0xFF002910)

val Red50 = Color(0xFFFFF1F1)
val Red100 = Color(0xFFFFE2E2)
val Red200 = Color(0xFFFFC2C2)
val Red300 = Color(0xFFFFA1A1)
val Red400 = Color(0xFFFF6B6B)
val Red500 = Color(0xFFFD3535)
val Red600 = Color(0xFFDE1F1F)
val Red700 = Color(0xFFB60C0C)
val Red800 = Color(0xFF860000)
val Red900 = Color(0xFF4A0000)

val Gray50 = Color(0xFFF7F7F8)
val Gray75 = Color(0xFFF2F3F5)
val Gray100 = Color(0xFFEEEEEF)
val Gray150 = Color(0xFFE4E4E4)
val Gray200 = Color(0xFFCBCCCC)
val Gray300 = Color(0xFFB1B3B4)
val Gray400 = Color(0xFF8E9092)
val Gray500 = Color(0xFF787A7D)
val Gray600 = Color(0xFF56595C)
val Gray700 = Color(0xFF3D3F41)
val Gray800 = Color(0xFF2F3133)
val Gray900 = Color(0xFF242527)

val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)

@Immutable
data class SSINGColors(
    val backgroundNormal: Color,
    val backgroundAlternative: Color,

    val textNormal: Color,
    val textStrong: Color,
    val textAlternative: Color,
    val textDisabled: Color,

    val borderNormal: Color,
    val borderStrong: Color,
    val borderAlternative: Color,
    val borderDisabled: Color,

    val primaryNormal: Color,
    val primaryAlternative: Color,
    val primaryStrong: Color,

    val accentRedNormal: Color,
    val accentRedAlternative: Color,

    val accentOrangeNormal: Color,
    val accentOrangeAlternative: Color,

    val accentGreenNormal: Color,
    val accentGreenAlternative: Color,

    val statusError: Color,
    val statusSuccess: Color,
)

val defaultSsingColors = SSINGColors(
    backgroundNormal = White,
    backgroundAlternative = Gray75,

    textNormal = Gray700,
    textStrong = Gray900,
    textAlternative = Gray400,
    textDisabled = Gray200,

    borderNormal = Gray300,
    borderStrong = Gray600,
    borderAlternative = Gray200,
    borderDisabled = Gray75,

    primaryNormal = Blue500,
    primaryAlternative = Blue200,
    primaryStrong = Blue600,

    accentRedNormal = Red500,
    accentRedAlternative = Red100,

    accentOrangeNormal = Orange500,
    accentOrangeAlternative = Orange100,

    accentGreenNormal = Green500,
    accentGreenAlternative = Green100,

    statusError = Red500,
    statusSuccess = Green600,
)

val localSsingColors = staticCompositionLocalOf { defaultSsingColors }
