package api.util.factory

import api.entity.enemy.*
import api.entity.environment.Position
import api.util.EnemyType
import api.util.constant.EnemyStats
import api.util.constant.GHOST_INVISIBLE_DURATION
import api.util.constant.INVISIBLE_CHANCE
import api.util.constant.MapChar.ELIXIR
import api.util.constant.MapChar.GHOST
import api.util.constant.MapChar.MIMIC
import api.util.constant.MapChar.OGRE
import api.util.constant.MapChar.SCROLL
import api.util.constant.MapChar.SNAKE_MAGE
import api.util.constant.MapChar.TREASURE
import api.util.constant.MapChar.VAMPIRE
import api.util.constant.MapChar.WEAPON
import api.util.constant.MapChar.ZOMBIE
import api.util.constant.StatModifiers.AGILITY_MODIFIER
import api.util.constant.StatModifiers.HEALTH_MODIFIER
import api.util.constant.StatModifiers.STRENGTH_MODIFIER
import kotlin.random.Random

object EnemyFactory {
    private fun createEnemy(type: EnemyType, pos: Position, dungeonLevel: Int): Enemy {
        val stats = EnemyStats.getStats(type)

        val healthModifier = 1.0 + (dungeonLevel * HEALTH_MODIFIER)
        val agilityModifier = 1.0 + (dungeonLevel * AGILITY_MODIFIER)
        val strengthModifier = 1.0 + (dungeonLevel * STRENGTH_MODIFIER)

        return when (type) {
            EnemyType.ZOMBIE -> Zombie(
                type = type,
                mapChar = ZOMBIE,
                (stats.health * healthModifier).toInt(),
                (stats.agility * agilityModifier).toInt(),
                (stats.strength * strengthModifier).toInt(),
                pos = pos,
                displayName = EnemyType.ZOMBIE.displayName,
                hostility = stats.hostility
            )

            EnemyType.VAMPIRE -> Vampire(
                type = type,
                mapChar = VAMPIRE,
                (stats.health * healthModifier).toInt(),
                (stats.agility * agilityModifier).toInt(),
                (stats.strength * strengthModifier).toInt(),
                pos = pos,
                isProtected = true,
                displayName = EnemyType.VAMPIRE.displayName,
                hostility = stats.hostility
            )

            EnemyType.GHOST -> Ghost(
                type = type,
                mapChar = GHOST,
                (stats.health * healthModifier).toInt(),
                (stats.agility * agilityModifier).toInt(),
                (stats.strength * strengthModifier).toInt(),
                pos = pos,
                isVisible = Random.nextFloat() < INVISIBLE_CHANCE,
                durationInvisible = GHOST_INVISIBLE_DURATION,
                displayName = EnemyType.GHOST.displayName,
                hostility = stats.hostility
            )

            EnemyType.OGRE -> Ogre(
                type = type,
                mapChar = OGRE,
                (stats.health * healthModifier).toInt(),
                (stats.agility * agilityModifier).toInt(),
                (stats.strength * strengthModifier).toInt(),
                pos = pos,
                durationResting = 0,
                isResting = false,
                displayName = EnemyType.OGRE.displayName,
                hostility = stats.hostility
            )

            EnemyType.SNAKE_MAGE -> SnakeMage(
                type = type,
                mapChar = SNAKE_MAGE,
                (stats.health * healthModifier).toInt(),
                (stats.agility * agilityModifier).toInt(),
                (stats.strength * strengthModifier).toInt(),
                pos = pos,
                displayName = EnemyType.SNAKE_MAGE.displayName,
                hostility = stats.hostility
            )

            EnemyType.MIMIC -> Mimic(
                type = type,
                mapChar = listOf(ELIXIR, TREASURE, WEAPON, SCROLL).random(),
                (stats.health * healthModifier).toInt(),
                (stats.agility * agilityModifier).toInt(),
                (stats.strength * strengthModifier).toInt(),
                pos = pos,
                hostility = stats.hostility,
                trueMapChar = MIMIC,
                displayName = EnemyType.MIMIC.displayName,
                isImitation = true,
            )
        }
    }

    fun createRandomEnemy(pos: Position, dungeonLevel: Int): Enemy {
        return createEnemy(EnemyType.entries.toTypedArray().random(), pos, dungeonLevel)
    }
}