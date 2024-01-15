package dev.trendster.visionpad

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import dev.trendster.visionpad.components.CoreEntity


/**
 * Composable function representing a Visionpad, a container for displaying a layout of entities.
 *
 * @param modifier Modifier for styling and layout adjustments.
 * @param controller VisionpadController responsible for managing entities and their positions.
 * @param backgroundColor Background color of the Visionpad.
 */
@Composable
fun Visionpad(
    modifier: Modifier,
    controller: VisionpadController,
    backgroundColor: Color = Color.Transparent
) {

    // Retrieve the elements from the controller using rememberSaveable.
    val elements by rememberSaveable { mutableStateOf(controller.elements) }

    Log.e("Visionpad", "ElementsCount: ${elements.value}")

    /** Use [VisionpadLayout] to create a layout for entities. */
    VisionpadLayout(
        modifier = Modifier.background(backgroundColor),
        controller = controller,
        entitiesCount = elements.value.size,
        entity = { index ->

            /** Use [CoreEntity] to render individual entities within the Visionpad. */
            CoreEntity(
                index = index,
                isSelected = controller.selectedEntityId.value == index,
                element = elements.value[index],
                modifier = Modifier,
                clickAction = {
                    controller.selectEntity(index)
                    Log.d("CoreEntity", "Single Click Here")
                }
            )
        }
    )

}






/**
 * Composable function representing a VisionpadLayout, a container for positioning and rendering entities.
 *
 * @param modifier Modifier for styling and layout adjustments.
 * @param entitiesCount The number of entities to be displayed in the Visionpad.
 * @param entity Composable function that defines the content of an individual entity based on its index.
 * @param controller VisionpadController responsible for managing entity positions.
 */
@Composable
fun VisionpadLayout(
    modifier: Modifier = Modifier,
    entitiesCount: Int,
    entity: @Composable (Int) -> Unit,
    controller: VisionpadController
)  {

    // Retrieve the current positions of entities from the controller using rememberSaveable.
    val positions by rememberSaveable { mutableStateOf(controller.positions) }

    // Define a composable for rendering entities based on the specified count.
    val entities = @Composable { repeat(entitiesCount) { entity(it) } }

    Log.e("VisionpadLayout", "EntitiesCount: $entitiesCount")

    Box(modifier = modifier
        .fillMaxSize()
        .background(Color.Blue)
        .background(color = Color.LightGray)

    ){


        /** Custom layout using Layout composable to position entities based on controller positions. */
        Layout(
            contents = listOf(entities),
            modifier = modifier,
            measurePolicy = { (entityMeasurables), constraints ->

                // Measure the entities to get their placeables.
                val entityPlaceables = entityMeasurables.map { it.measure(constraints) }

                // Perform the layout calculation based on specified positions.
                layout(
                    width = constraints.maxWidth,
                    height = constraints.maxHeight
                ){

                    // Iterate through entities and place them at calculated positions.
                    entityPlaceables.forEachIndexed { index, placeable ->
                        val xPos = (constraints.maxWidth * positions.value[index].x).toInt() - (placeable.width / 2)
                        val yPos = (constraints.maxHeight * positions.value[index].y).toInt() - (placeable.height / 2)
                        placeable.place(xPos,yPos)
                    }
                }
            }
        )

    }


}