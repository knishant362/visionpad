package dev.trendster.visionpad.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.trendster.visionpad.model.Element
import dev.trendster.visionpad.model.ElementType
import dev.trendster.visionpad.model.core.Position

@Composable
internal fun CoreEntity(
    modifier: Modifier = Modifier,
    index: Int,
    isSelected: Boolean,
    element: Element,
    clickAction: () -> Unit
) {

    val textEntity = @Composable { TextEntity(element = element) }
    val imageEntity = @Composable { TODO("Image Entity pending here") }

    val entity = when (element.type) {
        ElementType.Text -> textEntity
        ElementType.Image -> imageEntity
    }

    EntityContainer(
        isSelected = isSelected,
        entity = entity,
        modifier = modifier,
        clickAction = { clickAction()}
    )

}


@Preview
@Composable
fun CoreEntityPreview(){

    val demoElement = Element(
        id = 0,
        angle = 0f,
        isVisible = true,
        type = ElementType.Text,
        position = Position(),
        fontSize = 20F,
        text = "Nishant Kumar",
    )

    Column(modifier = Modifier.size(300.dp).background(color = Color.Blue)) {
        CoreEntity(modifier = Modifier, index = 0, isSelected = true, element = demoElement, clickAction = {})
    }

}

