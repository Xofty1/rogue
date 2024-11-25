package api.entity.item

import api.entity.environment.Position
import api.util.item_type.ElixirType

data class Elixir(
    val type: ElixirType,
    override val name: String,
    override var pos: Position,
    val agility: Int,
    val strength: Int,
    val maxHealth: Int,
    var duration: Int,
    var isUsed: Boolean,
) : Item(name)