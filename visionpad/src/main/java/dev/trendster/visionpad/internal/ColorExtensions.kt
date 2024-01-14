package dev.trendster.visionpad.internal

import dev.trendster.visionpad.model.core.Color
import androidx.compose.ui.graphics.Color as ComposeColor

fun Color.toColor(): ComposeColor {
    return ComposeColor(android.graphics.Color.argb(alpha, red, green, blue))
}