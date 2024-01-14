package dev.trendster.visionpad.model.core

/**
 * Data class representing a color with red, green, blue, and alpha components.
 *
 * @property red The red component of the color (0-255).
 * @property green The green component of the color (0-255).
 * @property blue The blue component of the color (0-255).
 * @property alpha The alpha component of the color, representing transparency (0-255).
 */
data class Color(
    val alpha: Int = 0,
    val blue: Int = 0,
    val green: Int = 0,
    val red: Int = 0
) {

    companion object {
        val White = Color(alpha = 255, red = 255, green = 255, blue = 255)
        val Black = Color(alpha = 255, red = 0, green = 0, blue = 0)
        // You can add more predefined colors here
    }
}