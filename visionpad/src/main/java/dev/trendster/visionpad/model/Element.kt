package dev.trendster.visionpad.model

import dev.trendster.visionpad.model.core.Color
import dev.trendster.visionpad.model.core.Position

data class Element(
    val id: Int,
    var isVisible: Boolean = true,
    val type: ElementType,
    val position: Position,
    val angle: Float,
    val opacity: Float? = 100f,

    /** Text Properties */

    val fontPath: String? = null,
    val fontSize: Float? = null,
    val text: String? = null,
    val textColor: Color = Color.Black,
)
