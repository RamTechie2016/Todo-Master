package com.ramarajan.todomastercompose.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

@Composable
fun getRandomLightBackgroundColor(): Color {
    val random = Random.Default
    val minLightness = 0.7f // Adjust as needed for lighter colors
    val maxLightness = 1.0f // Full lightness
    val red = (random.nextFloat() * 128 + 128).toInt()
    val green = (random.nextFloat() * 128 + 128).toInt()
    val blue = (random.nextFloat() * 128 + 128).toInt()
    return Color(red, green, blue)
}