package api.entity.enemy

import api.entity.Entity
import api.util.EnemyType

abstract class Enemy(
    open val type: EnemyType,
    override var health: Int,
    override var agility: Int,
    override var strength: Int,
    open val hostility: Int,
) : Entity(health, agility, strength)
