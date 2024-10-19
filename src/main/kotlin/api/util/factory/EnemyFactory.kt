package api.util.factory

import api.entity.enemy.*
import api.entity.environment.Position
import api.util.EnemyType
import api.util.constant.EnemyStats

object EnemyFactory {
    fun createEnemy(type: EnemyType, pos: Position): Enemy {
        val stats = EnemyStats.getStats(type)
        return when (type) {
            EnemyType.ZOMBIE -> Zombie(
                type = type,
                health = stats.health,
                agility = stats.agility,
                strength = stats.strength,
                pos = pos,
                hostility = stats.hostility
            )

            EnemyType.VAMPIRE -> Vampire(
                type = type,
                health = stats.health,
                agility = stats.agility,
                strength = stats.strength,
                pos = pos,
                hostility = stats.hostility
            )

            EnemyType.GHOST -> Ghost(
                type = type,
                health = stats.health,
                agility = stats.agility,
                strength = stats.strength,
                pos = pos,
                hostility = stats.hostility
            )

            EnemyType.OGRE -> Ogre(
                type = type,
                health = stats.health,
                agility = stats.agility,
                strength = stats.strength,
                pos = pos,
                hostility = stats.hostility
            )

            EnemyType.SNAKE_MAGE -> SnakeMage(
                type = type,
                health = stats.health,
                agility = stats.agility,
                strength = stats.strength,
                pos = pos,
                hostility = stats.hostility
            )
        }
    }
}