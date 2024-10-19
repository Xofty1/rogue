package api.entity.enemy

import api.entity.environment.Position
import api.util.EnemyType

data class Ogre(
    override val type: EnemyType,
    override var health: Int,
    override var agility: Int,
    override var strength: Int,
    override var pos: Position,
    override val hostility: Int
) : Enemy(type, health, agility, strength, hostility)