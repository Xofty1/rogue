package api.service


import api.entity.Entity
import api.entity.Player
import api.entity.enemy.Ghost
import api.entity.enemy.Mimic
import api.entity.enemy.Ogre
import api.entity.environment.DungeonMap
import api.entity.environment.Position
import api.entity.item.Elixir
import api.entity.item.Exit
import api.entity.item.Item
import api.entity.item.Treasure
import api.service.game_mechanic.*
import api.service.updater.PlaygroundUpdater
import api.service.updater.StateUpdater
import api.util.Direction
import api.util.checks.PlaygroundChecks

object GameCycle {
    private val movementService = MovementService
    private val fightService = FightService
    private val observeService = ObserveService
    private val backpackService = BackpackService
    private val itemProcessing = ItemProcessing
    private val playgroundChecks = PlaygroundChecks
    private val playgroundUpdater = PlaygroundUpdater
    private val stateUpdater = StateUpdater

    fun makePlayerMove(dungeonMap: DungeonMap, direction: Direction): List<String> {
        val player = dungeonMap.player as Player
        val playerPos = player.pos
        val actionResults = mutableListOf<String>()

        checkIsPlayerAlive(player)

        if (player.isSleeping) return handleSleepingPlayer(player, actionResults)

        playgroundChecks.findEnemyForward(playerPos, dungeonMap, direction)?.let { enemy ->
            actionResults.addAll(handleEnemyEncounter(dungeonMap, enemy))
        } ?: handleMovement(dungeonMap, direction, actionResults)

        actionResults.addAll(stateUpdater.checkItemDurationInPlayerBackpack(player))

        return actionResults
    }

    private fun handleSleepingPlayer(
        player: Player,
        actionResults: MutableList<String>
    ): List<String> {
        player.isSleeping = false
        actionResults.add("${player.displayName} спит")
        return actionResults
    }

    private fun handleEnemyEncounter(dungeonMap: DungeonMap, enemy: Entity): List<String> {
        val results = mutableListOf<String>()
        results.addAll(fightService.aggressiveAction(dungeonMap.player as Entity, enemy))
        results.addAll(stateUpdater.processDeadEnemies(dungeonMap))
        return results
    }

    private fun handleMovement(
        dungeonMap: DungeonMap,
        direction: Direction,
        actionResults: MutableList<String>
    ) {
        val player = dungeonMap.player as Player
        val playground = dungeonMap.playground
        val nextPos = movementService.calculatePlayerNextPos(player.pos, direction, playground)

        if (nextPos == null) {
            actionResults.add("${player.displayName} упирается в стену")
            return
        }

        player.pos = nextPos
        if (playgroundChecks.isItemPosition(nextPos, playground)) {
            handleItemPickup(dungeonMap, nextPos, actionResults)
        }

        playgroundUpdater.update(dungeonMap)
    }

    private fun handleItemPickup(
        dungeonMap: DungeonMap,
        position: Position,
        actionResults: MutableList<String>
    ) {
        val player = dungeonMap.player as Player
        val item = dungeonMap.items.first { it.pos == position } as Item

        when (item) {
            is Exit -> {
                dungeonMap.isExitFound = true
                actionResults.add("Обнаружен выход")
            }

            is Treasure -> {
                val result = itemProcessing.useItemOnPlayer(player, item)
                result?.let {
                    dungeonMap.items.remove(item)
                    actionResults.add(it)
                }
            }

            else -> {
                val pickedUpItem = backpackService.pickUpItem(player.backpack, item)
                if (pickedUpItem != null) {
                    dungeonMap.items.remove(item)
                    actionResults.add("Подобран предмет: $pickedUpItem")
                } else {
                    actionResults.add("Рюкзак полон")
                }
            }
        }
    }

    fun useItemFromBackPack(player: Player, rowNumber: Int, cellNumber: Int): List<String> {
        val actionResults = mutableListOf<String>()
        val item = backpackService.getItem(player.backpack, rowNumber, cellNumber)
        item?.let {
            if (item !is Elixir) {
                backpackService.removeItem(player.backpack, item)
            }
            itemProcessing.useItemOnPlayer(player, item)?.let { res -> actionResults.add(res) }
        } ?: actionResults.add("Ячейка рюкзака пуста")

        return actionResults
    }

    fun enemiesTurn(dungeonMap: DungeonMap): List<String> {
        val player = dungeonMap.player as Player
        val playerPos = dungeonMap.player.pos
        val playground = dungeonMap.playground
        val actionResults = mutableListOf<String>()

        dungeonMap.enemies.filterNot { it is Ogre && it.isResting }.forEach { e ->
            when {
                playgroundChecks.isPlayerNearby(e.pos, playground) -> {
                    actionResults.addAll(fightService.aggressiveAction(e as Entity, player))
                }

                e is Ghost -> {
                    e.pos = movementService.getRandomPosForGhost(e.pos, playground)
                    playgroundUpdater.update(dungeonMap)
                }

                observeService.canSeePlayer(e, playerPos, playground) -> {
                    if (e is Mimic) e.isImitation = false

                    val newEnemyPos = movementService.calculateEnemyNextPos(
                        e,
                        playerPos,
                        playground
                    )
                    newEnemyPos?.let {
                        e.pos = it
                        playgroundUpdater.update(dungeonMap)
                    }
                }
            }
        }

        stateUpdater.updateEnemies(dungeonMap)

        return actionResults
    }

    private fun checkIsPlayerAlive(player: Player) = check(player.health != 0) { "Игра окончена" }
}