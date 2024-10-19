package api.util.constant

import api.util.EnemyType

object EnemyStats {
    private val stats = mapOf(
        EnemyType.ZOMBIE to Stats(150, 3, 5, 4),
        EnemyType.VAMPIRE to Stats(200, 6, 10, 10),
        EnemyType.GHOST to Stats(80, 5, 7, 8),
        EnemyType.OGRE to Stats(300, 2, 15, 3),
        EnemyType.SNAKE_MAGE to Stats(120, 4, 8, 9)
    )

    data class Stats(val health: Int, val agility: Int, val strength: Int, val hostility: Int)

    fun getStats(type: EnemyType): Stats {
        return stats[type] ?: Stats(0, 0, 0, 0)
    }
}