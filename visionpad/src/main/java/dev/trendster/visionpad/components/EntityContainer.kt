package dev.trendster.visionpad.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import dev.trendster.visionpad.R


/**                                         EntityContainer

        +---------------------------------------------------------------------------------------+
        |                                                                                       |
        |  +-----------------+                                                                  |
        |  |    Toggle (X)   |                                                                  |
        |  |     (Delete)    |                                                                  |
        |  +-----------------+                                                                  |
        |                       +-----------------------------------------+                     |
        |                       |            Container Border             |                     |
        |                       |                                         |                     |
        |                       |     +-----------------------------+     |                     |
        |                       |     |                             |     |                     |
        |                       |     |           Entity            |     |                     |
        |                       |     |                             |     |                     |
        |                       |     +-----------------------------+     |                     |
        |                       |                                         |                     |
        |                       +-----------------------------------------+                     |
        |                                                                   +----------------+  |
        |                                                                   |   Toggle (⤢)   |  |
        |                                                                   |    (Resize)    |  |
        |                                                                   +----------------+  |
        |                                                                                       |
        +---------------------------------------------------------------------------------------+

 * */


/**
 * EntityContainer represents a container for an entity with toggle buttons.
 * The container can be selected, and toggle buttons are displayed accordingly.
 */

@Composable
internal fun EntityContainer(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    entity: @Composable () -> Unit,
    clickAction: () -> Unit
) {

    /** Composable for the delete toggle button. */
    val deleteToggle = @Composable {
        Toggle(
            icon = painterResource(id = R.drawable.ic_close),
            modifier = Modifier)
    }

    /** Composable for the scale toggle button. */
    val scaleToggle = @Composable {
        Toggle(icon = painterResource(id = R.drawable.ic_resize),
            modifier = Modifier)
    }

    /** Composable for the container's border.*/
    val containerBorder = @Composable {
        Box(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = if (isSelected) Color.DarkGray else Color.Transparent,
                    shape = RoundedCornerShape(1.dp),
                ).clickable (
                    onClick = {
                        clickAction()
                        Log.d("CoreEntity", "Single Click Here")
                    }
                )
        )
    }


    /** Define a Composable for rendering the toggle buttons based on selection.
     * [isSelected] = true : show Actual Toggle
     * [isSelected] = false : show Dummy Spacer
     * */

    val toggles = @Composable {
        if (isSelected) {
            repeat(2) { index ->
                when (index) {
                    0 -> deleteToggle()
                    1 -> scaleToggle()
                }
            }
        } else repeat(2) {
            Spacer(modifier = Modifier.size(ToggleSize))
        }
    }

    /** Define the layout using the Layout Composable function. */
    Layout(
        contents = listOf(entity, toggles, containerBorder),
        modifier = modifier
    ) { (entityMeasurables, toggleMeasurables, borderMeasurables), constraints ->

        // Ensure that the entity emits only one Composable.
        require(entityMeasurables.size == 1) {"entity should only emit one composable"}


        /** Measure the entity Composable and toggle buttons. */
        val entityPlaceable = entityMeasurables.first().measure(constraints)
        val togglePlaceables = toggleMeasurables.map { it.measure(constraints) }
        val borderPlaceable = borderMeasurables.first().measure(
            Constraints.fixed(
                entityPlaceable.width + togglePlaceables[0].width,
                entityPlaceable.height + togglePlaceables[0].height
            )
        )

        /** Calculate the total width and height of the layout. */
        val width = entityPlaceable.width + 2 * togglePlaceables[0].width
        val height = entityPlaceable.height + 2 + togglePlaceables[0].height

        /** Perform the layout positioning for the Composables. */
        layout(
            width = width,
            height = height
        ) {

            val toggleOne = togglePlaceables[0]
            val toggleTwo = togglePlaceables[1]

            /** Position the border and toggle buttons relative to the entity. as shown in Diagram */
            borderPlaceable.place(toggleOne.width / 2, toggleOne.height / 2)
            toggleOne.place(0, 0)
            toggleTwo.place(
                toggleOne.width + entityPlaceable.width,
                toggleOne.height + entityPlaceable.height
            )
            entityPlaceable.place(toggleOne.width, toggleOne.height)
        }
    }

}


@Composable
@Preview
internal fun EntityContainerPreview() {
    // Example usage of EntityContainer in a Compose hierarchy
    Column(
        modifier = Modifier
            .size(300.dp)
            .background(Color.Gray)
            .padding(16.dp)
    ) {
        EntityContainer(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            isSelected = true,
            entity = {
                // Your entity content goes here

            },
            clickAction = {}
        )
    }
}