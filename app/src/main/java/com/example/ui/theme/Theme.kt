package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val NovaDarkColorScheme =
    darkColorScheme(
        primary = NovaPrimaryNeonCyan,
        secondary = NovaSecondaryNeonPink,
        tertiary = NovaNeonPurple,
        background = NovaDarkBg,
        surface = NovaSurface,
        surfaceVariant = NovaSurfaceVariant,
        onPrimary = NovaDarkBg,
        onSecondary = NovaTextPrimary,
        onBackground = NovaTextPrimary,
        onSurface = NovaTextPrimary,
        onSurfaceVariant = NovaTextSecondary
    )

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = NovaDarkColorScheme,
        typography = Typography,
        content = content
    )
}
