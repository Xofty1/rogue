package api.entity.item

import api.entity.behavior.Dropable
import api.entity.behavior.Equipable
import api.entity.environment.Position
import api.util.item_type.WeaponType

data class Weapon(
    val type: WeaponType,
    override val name: String,
    override var pos: Position,
    val strength: Int,
) : Item(name), Equipable, Dropable