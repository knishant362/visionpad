package dev.trendster.visionpad.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.trendster.visionpad.R


val ToggleSize = 24.dp
@Composable
internal fun Toggle(
    modifier: Modifier = Modifier,
    icon: Painter
){
    Image(
        painter = icon,
        contentDescription = "Toggle Button",
        modifier = modifier.size(ToggleSize)
    )

}

@Preview
@Composable
fun TogglePreview() {
    Toggle(icon = painterResource(id = R.drawable.ic_close), modifier = Modifier)
}