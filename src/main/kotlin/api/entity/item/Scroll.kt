package api.entity.item

import api.entity.environment.Position
import api.util.item_type.ScrollType

data class Scroll(
    val type: ScrollType,
    override val name: String,
    override var pos: Position,
    val agility: Int,
    val strength: Int,
    val maxHealth: Int,
) : Item(name)