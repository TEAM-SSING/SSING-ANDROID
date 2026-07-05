package com.ssing.core.ui.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.activity.compose.LocalActivity

object SSINGTheme {
    val colors: SSINGColors
        @Composable
        @ReadOnlyComposable
        get() = localSsingColors.current

    val typography: SSINGTypography
        @Composable
        @ReadOnlyComposable
        get() = localSsingTypography.current
}

@Composable
fun ProvideSSINGColorsAndTypography(
    colors: SSINGColors,
    typography: SSINGTypography,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        localSsingColors provides colors,
        localSsingTypography provides typography,
        content = content,
    )
}

@Composable
fun SSINGTheme(
    content: @Composable () -> Unit,
) {
    val view = LocalView.current
    val activity = LocalActivity.current

    SideEffect {
        activity?.window?.let {
            WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = true
        }
    }

    ProvideSSINGColorsAndTypography(
        colors = defaultSsingColors,
        typography = defaultSsingTypography,
    ) {
        MaterialTheme(
            colorScheme = lightColorScheme(
                background = defaultSsingColors.backgroundNormal
            ),
            content = content,
        )
    }
}
