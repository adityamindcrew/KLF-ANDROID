package com.vanshika.klf.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun AttendanceScannerTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false, // Not used in your snippet but kept for API parity
    content: @Composable () -> Unit
) {
    // Choose color scheme based on darkTheme flag
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // assuming you have Typography defined in TypeKt
        content = content
    )
}
