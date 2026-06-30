package com.ssing.core.ui.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object SSINGTheme {
    val colors: SSINGColors
        @Composable
        @ReadOnlyComposable
        get() = LocalSSINGColors.current

    val typography: SSINGTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalSSINGTypography.current
}

@Composable
fun ProvideSSINGColorsAndTypography(
    colors: SSINGColors,
    typography: SSINGTypography,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalSSINGColors provides colors,
        LocalSSINGTypography provides typography,
        content = content,
    )
}

@Composable
fun SSINGTheme(
    content: @Composable () -> Unit,
) {
    ProvideSSINGColorsAndTypography(
        colors = defaultSSINGColors,
        typography = defaultSSINGTypography,
    ) {
        MaterialTheme(
            content = content,
        )
    }
}