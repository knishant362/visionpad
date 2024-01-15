package dev.trendster.visionpad

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.trendster.visionpad.model.Element
import dev.trendster.visionpad.model.ElementType
import dev.trendster.visionpad.model.core.Position

@Composable
fun MainScreen() {

    val visionpadController = rememberVisionpadController()

    LaunchedEffect(visionpadController.elements){

        val elements = listOf(
            Element(
                id = 0,
                angle = 0f,
                isVisible = true,
                type = ElementType.Text,
                position = Position(),
                fontSize = 20F,
                text = "Nishant Kumar",
            ),

            Element(
                id = 1,
                angle = 0f,
                isVisible = true,
                type = ElementType.Text,
                position = Position(0.5f, 0.7f),
                fontSize = 20F,
                text = "Trendster",
            )
        )

        visionpadController.setEntities(elements)

    }

    Column {

        Visionpad(
            modifier = Modifier,
            controller = visionpadController,
            backgroundColor = Color.Gray
        )

    }


}