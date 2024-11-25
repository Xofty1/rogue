package api.entity.enemy

import api.entity.Entity
import api.util.EnemyType

abstract class Enemy(
    open val type: EnemyType,
    override val mapChar: Char,
    override var health: Int,
    override var agility: Int,
    override var strength: Int,
    override val displayName: String,
    open val hostility: Int,
) : Entity(health, agility, strength, mapChar, displayName)
