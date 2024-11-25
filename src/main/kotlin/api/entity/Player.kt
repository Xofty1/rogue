package api.entity

import api.entity.environment.Position
import api.entity.item.Weapon

data class Player(
    override val mapChar: Char,
    override var health: Int,
    override var agility: Int,
    override var strength: Int,
    override var pos: Position,
    override var displayName: String,
    var rating: Int,
    var maxHealth: Int,
    val visionRange: Int,
    val backpack: Backpack,
    var equippedWeapon: Weapon,
    var isSleeping: Boolean,
) : Entity(health, agility, strength, mapChar, displayName)