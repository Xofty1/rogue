package api.entity.enemy

import api.entity.environment.Position
import api.util.EnemyType

data class Zombie(
    override val type: EnemyType,
    override val mapChar: Char,
    override var health: Int,
    override var agility: Int,
    override var strength: Int,
    override var pos: Position,
    override val displayName: String,
    override val hostility: Int
) : Enemy(type, mapChar, health, agility, strength, displayName, hostility)