package api.entity.item

import api.entity.environment.Position
import api.util.item_type.FoodType

data class Food(
    val type: FoodType,
    override val name: String,
    override var pos: Position,
    val health: Int
) : Item(name)