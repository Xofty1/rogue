package api.service.updater

import api.entity.Entity
import api.entity.Player
import api.entity.enemy.Enemy
import api.entity.enemy.Ghost
import api.entity.enemy.Ogre
import api.entity.environment.DungeonMap
import api.entity.item.Elixir
import api.service.game_mechanic.BackpackService
import api.service.game_mechanic.DropService
import api.service.game_mechanic.ItemProcessing
import api.util.constant.GHOST_INVISIBLE_DURATION
import api.util.constant.INVISIBLE_CHANCE
import api.util.constant.OGRE_RESTING_DURATION
import api.util.constant.SymbolConstants.INNER_AREA_CHAR
import kotlin.random.Random

object StateUpdater {
    private val itemProcessing = ItemProcessing
    private val backpackService = BackpackService
    private val dropService = DropService

    fun updateEnemies(dungeonMap: DungeonMap) {
        wakeUpOgres(dungeonMap)
        visibleGhosts(dungeonMap)
    }

    fun updateGhostsAndOgresWhenAggressiveAction(entity: Entity) {
        if (entity is Ogre) {
            entity.isResting = true
            entity.durationResting = OGRE_RESTING_DURATION
        }

        if (entity is Ghost) {
            entity.isVisible = true
        }
    }

    fun processDeadEnemies(dungeonMap: DungeonMap): List<String> {
        val actionResults = mutableListOf<String>()
        val remainingEnemies = mutableListOf<Entity>()
        val player = dungeonMap.player as Player

        dungeonMap.enemies.forEach {
            it as Entity
            if (it.health > 0) {
                remainingEnemies.add(it)
            } else {
                val pos = it.pos
                dungeonMap.playground[pos.y][pos.x] = INNER_AREA_CHAR
                val drop = dropService.calculateTreasureDropFromEnemy(it as Enemy)
                player.rating += drop
                actionResults.add("${it.displayName} погибает и роняет $drop сокровищ")
            }
        }

        dungeonMap.enemies.clear()
        dungeonMap.enemies.addAll(remainingEnemies)

        return actionResults
    }

    private fun wakeUpOgres(dungeonMap: DungeonMap) {
        dungeonMap.enemies.filter { it is Ogre && it.isResting }.forEach {
            it as Ogre
            if (it.durationResting > 0) {
                it.durationResting--
            } else {
                it.isResting = false
            }
        }
    }

    private fun visibleGhosts(dungeonMap: DungeonMap) {
        dungeonMap.enemies.filterIsInstance<Ghost>().forEach {
            if (!it.isVisible && it.durationInvisible > 0) {
                it.durationInvisible--
            } else {
                it.isVisible = true
                if (Random.nextFloat() < INVISIBLE_CHANCE) {
                    it.isVisible = false
                    it.durationInvisible = GHOST_INVISIBLE_DURATION
                }
            }
        }
    }

    fun checkItemDurationInPlayerBackpack(player: Player): List<String> {
        val actionResults = mutableListOf<String>()
        val backpack = player.backpack
        val expiredElixirs = mutableListOf<Elixir>()

        backpack.elixirs.forEach { elixir ->
            if (elixir != null && elixir.isUsed) {
                elixir.duration -= 1
                if (elixir.duration <= 0) {
                    itemProcessing.decreasePlayerStats(
                        elixir.agility,
                        elixir.strength,
                        elixir.maxHealth,
                        player
                    )
                    expiredElixirs.add(elixir)
                }
            }
        }

        expiredElixirs.forEach { elixir ->
            backpackService.removeItem(backpack, elixir)
            actionResults.add("Действие эликсира: ${elixir.type.value} закончилось")
        }

        return actionResults
    }
}