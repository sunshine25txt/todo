package com.todapp.tod.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Scheme = lightColorScheme(
    primary = Teal,
    onPrimary = Color.White,
    background = MintBg,
    onBackground = Ink,
    surface = Color.White,
    onSurface = Ink
)

@Composable
fun TodTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = Scheme,
        content = content
    )
}
