package controller

import api.entity.Player
import api.entity.environment.DungeonMap
import api.mapper.DungeonMapMapper.toDto
import api.service.GameCycle
import api.service.GameService
import api.util.Direction
import api.util.constant.DungeonConstants.MAX_DUNGEON_LEVEL
import controller.dto.GameInfo

object GameController {
    private val gameService = GameService
    private val gameCycle = GameCycle
    private lateinit var dungeonMap: DungeonMap

    fun startGame(): GameInfo {
        dungeonMap = gameService.startGame()

        return GameInfo(dungeonMap.toDto(), emptyList())
    }

    fun loadGame(): GameInfo? {
        return gameService.loadGame()?.let {
            dungeonMap = it
            GameInfo(dungeonMap.toDto(), emptyList())
        }
    }

    fun makeMove(direction: Direction): GameInfo {
        val actionResults = gameCycle.makePlayerMove(dungeonMap, direction)
        val enemyActionResults = gameCycle.enemiesTurn(dungeonMap)

        if (dungeonMap.isExitFound) {
            dungeonMap =
                gameService.nextDungeon(dungeonMap.player as Player, dungeonMap.dungeonLevel)
            gameService.saveGame(dungeonMap)
        }

        updateTableRecords(dungeonMap)

        return GameInfo(dungeonMap.toDto(), actionResults + enemyActionResults)
    }

    fun useItem(rowNumber: Int, cellNumber: Int): GameInfo {
        val actionResults =
            gameCycle.useItemFromBackPack((dungeonMap.player as Player), rowNumber, cellNumber)
        val enemyActionResults = gameCycle.enemiesTurn(dungeonMap)

        updateTableRecords(dungeonMap)

        return GameInfo(dungeonMap.toDto(), actionResults + enemyActionResults)
    }

    fun getTableRecords(): List<String> {
        return gameService.loadRecords()
    }

    private fun updateTableRecords(dungeonMap: DungeonMap) {
        val player = dungeonMap.player as Player
        if (player.health == 0 || dungeonMap.dungeonLevel == MAX_DUNGEON_LEVEL) {
            gameService.saveRecord(dungeonMap)
        }
    }
}