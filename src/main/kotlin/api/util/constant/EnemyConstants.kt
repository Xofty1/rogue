package api.util.constant

import api.util.EnemyType

object EnemyStats {
    private val stats = mapOf(
        EnemyType.ZOMBIE to Stats(50, 2, 5, 2),
        EnemyType.VAMPIRE to Stats(50, 8, 8, 6),
        EnemyType.GHOST to Stats(25, 8, 2, 3),
        EnemyType.OGRE to Stats(75, 2, 12, 3),
        EnemyType.SNAKE_MAGE to Stats(60, 10, 4, 6),
        EnemyType.MIMIC to Stats(65, 8, 2, 2),
    )

    data class Stats(val health: Int, val agility: Int, val strength: Int, val hostility: Int)

    fun getStats(type: EnemyType): Stats {
        return stats[type] ?: Stats(0, 0, 0, 0)
    }
}