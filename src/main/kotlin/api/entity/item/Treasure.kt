package api.entity.item

import api.entity.behavior.Dropable
import api.entity.environment.Position
import api.util.item_type.TreasureType

data class Treasure(
    val type: TreasureType,
    override val name: String,
    override var pos: Position,
    val cost: Int,
) : Item(name), Dropable