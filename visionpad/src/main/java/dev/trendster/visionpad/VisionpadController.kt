package dev.trendster.visionpad

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import dev.trendster.visionpad.model.Element
import dev.trendster.visionpad.model.ElementType
import dev.trendster.visionpad.model.core.Color
import dev.trendster.visionpad.model.core.Position


/** Creates and remembers a [VisionpadController] on the current composer. */
@Composable
public fun rememberVisionpadController(): VisionpadController {
    return remember {VisionpadController()}
}

const val TAG = "VisionpadController"

/**
 * VisionpadController interacts with [Visionpad] and it allows you to control the canvas and
 * all of its components with it.
 * */
public class VisionpadController {

    var elements: MutableState<List<Element>> = mutableStateOf(emptyList())
    private var elementStack: MutableState<Map<Int, List<Element>>> = mutableStateOf(emptyMap())

    var aspectRatio: MutableState<Float> = mutableStateOf(0F)


    var positions: MutableState<List<Position>> = mutableStateOf(emptyList())

    var selectedEntityId: MutableState<Int> = mutableStateOf(-1)

    fun selectEntity(entityId: Int) {
        try {
            selectedEntityId.value = entityId
            Log.e(TAG, "SelectEntity >> Id:$entityId")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    var selectedEntityType: MutableState<ElementType?> = mutableStateOf(null)


    fun setEntities(newElements: List<Element>) {
        Log.d("setEntities", "EntitiesSize: ${newElements.size}")
        elements.value = newElements
        updateStack(newElements)
        positions.value = newElements.map { it.position }
    }

    public fun releaseEntity() {
        selectedEntityId.value = -1
        selectedEntityType.value = null
    }

    private fun updateStack(elements: List<Element>) {
        elementStack.value = hashMapOf(elementStack.value.size to elements)
    }

    fun updateEntityPosition(x: Float, y: Float) {
        val entityId = selectedEntityId.value
        if (entityId == -1 && elements.value.isEmpty()) {
            Log.e(
                TAG,
                "updateEntityPosition1 >> Return Block: Position: x:$x, y:$y, EntityId: $entityId"
            )
            return
        }
        val frameSize = frameSize[entityId] ?: return
        val maxWidth = frameSize.first
        val maxHeight = frameSize.second

        val xCord = (x) / maxWidth.toFloat()
        val yCord = (y) / maxHeight.toFloat()

        Log.e(TAG, "updateEntityPosition2 >> EntityId: $entityId, updatedPosition : xCord:$xCord, yCord:$yCord")

        Log.e(
            TAG,
            "updateEntityPosition2 >> EntityId: $entityId, updatedPosition : xCord:$xCord, yCord:$yCord"
        )

        val updatedEntities = elements.value.mapIndexed { index, _entity ->
            if (index == entityId) {
                val position = _entity.position
                val updatedPosition = _entity.position.copy(
                    x = position.x + xCord.coerceIn(-0.999F, 0.999F),
                    y = position.y + yCord.coerceIn(-0.999F, 0.999F)
                )
                _entity.copy(position = updatedPosition)
            } else _entity
        }
        elements.value = updatedEntities
        positions.value = updatedEntities.map { it.position }

    }

    private val frameSize: HashMap<Int, Pair<Int, Int>> = hashMapOf()



    fun deleteTextEntity() {
        Log.e(TAG, "createNewTextEntity >> createNewTextEntity: 1")
        val currentElements = elements.value
        val lastText = currentElements.lastOrNull { it.type == ElementType.Text }
        lastText?.let {
            Log.e(TAG, "createNewTextEntity >> createNewTextEntity: inside")
            val updatedEntities = currentElements.filterNot { it.id == selectedEntityId.value }
            val newList = mutableListOf<Element>()
            newList.addAll(updatedEntities)
            Log.d(TAG, "createNewTextEntity >> UpdatedListSize: ${newList}")
            setEntities(newList)
//            updateStack(updatedList)
            releaseEntity()
        }
    }

    fun createNewTextEntity() {
        Log.e(TAG, "createNewTextEntity >> createNewTextEntity: 1")
        val currentElements = elements.value
        Log.e(TAG, "createNewTextEntity >> createNewTextEntity: inside")
        val newTextElement = Element(
            id = currentElements.size,
            isVisible = true,
            type = ElementType.Text,
            position = Position(x = 0.5f, y = 0.22f),
            angle = 0f,
            fontPath = "",
            fontSize = 20f,
            text = "DEFAULT_TEXT",
            textColor = Color.White
        )
        val updatedList = currentElements.toMutableList()
        val newList = mutableListOf<Element>()
        newList.addAll(updatedList)
        newList.add(newTextElement)
        Log.d(TAG, "createNewTextEntity >> UpdatedListSize: ${newList}")
        setEntities(newList)
        selectEntity(newTextElement.id)

    }

}