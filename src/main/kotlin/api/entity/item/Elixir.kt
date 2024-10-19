package api.entity.item

import api.entity.behavior.Dropable
import api.entity.behavior.Useable
import api.entity.environment.Position
import api.util.item_type.ElixirType

data class Elixir(
    val type: ElixirType,
    override val name: String,
    override var pos: Position,
    val agility: Int,
    val strength: Int,
    val maxHealth: Int,
) : Item(name), Dropable, Useable