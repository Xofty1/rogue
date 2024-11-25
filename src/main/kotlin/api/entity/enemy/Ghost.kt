package api.entity.enemy

import api.entity.environment.Position
import api.util.EnemyType

data class Ghost(
    override val type: EnemyType,
    override val mapChar: Char,
    override var health: Int,
    override var agility: Int,
    override var strength: Int,
    override var pos: Position,
    override val hostility: Int,
    override val displayName: String,
    var isVisible: Boolean,
    var durationInvisible: Int,
) : Enemy(type, mapChar, health, agility, strength, displayName, hostility)