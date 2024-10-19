package api.entity

import api.entity.environment.Position
import api.entity.item.Weapon

data class Player(
    override var health: Int,
    override var agility: Int,
    override var strength: Int,
    override var pos: Position,
    var maxHealth: Int,
    val backpack: Backpack,
    var equippedWeapon: Weapon,
) : Entity(health, agility, strength)