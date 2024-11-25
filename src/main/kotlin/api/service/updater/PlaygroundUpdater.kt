package api.service.updater

import api.entity.behavior.Placeable
import api.entity.enemy.Enemy
import api.entity.enemy.Ghost
import api.entity.enemy.Mimic
import api.entity.environment.DungeonMap
import api.entity.environment.Position
import api.entity.item.*
import api.util.constant.MapChar.ELIXIR
import api.util.constant.MapChar.ELSE
import api.util.constant.MapChar.EXIT
import api.util.constant.MapChar.FOOD
import api.util.constant.MapChar.PLAYER
import api.util.constant.MapChar.SCROLL
import api.util.constant.MapChar.TREASURE
import api.util.constant.MapChar.WEAPON
import api.util.constant.SymbolConstants.CORRIDOR_CHAR
import api.util.constant.SymbolConstants.INNER_AREA_CHAR
import api.util.constant.SymbolConstants.OUTER_AREA_CHAR
import api.util.constant.SymbolConstants.WALL_CHAR

object PlaygroundUpdater {

    fun update(dungeon: DungeonMap) {
        clearPlayground(dungeon.playground)
        updateCorridorPositions(dungeon.playground, dungeon.corridors)
        updateItemPositions(dungeon.playground, dungeon.items)
        updatePlayerPos(dungeon.playground, dungeon.player)
        updateEnemyPositions(dungeon.playground, dungeon.enemies)
    }

    private fun clearPlayground(playground: Array<CharArray>) {
        for (y in playground.indices) {
            for (x in playground[y].indices) {
                if (isNonPlaceableElement(playground[y][x])) {
                    continue
                }
                playground[y][x] = INNER_AREA_CHAR
            }
        }
    }

    private fun updatePlayerPos(playground: Array<CharArray>, player: Placeable) {
        val (x, y) = player.pos
        playground[y][x] = PLAYER
    }

    private fun updateCorridorPositions(
        playground: Array<CharArray>,
        corridorPositions: List<Position>
    ) {
        corridorPositions.forEach { corridor ->
            val (x, y) = corridor
            playground[y][x] = CORRIDOR_CHAR
        }
    }

    private fun updateEnemyPositions(playground: Array<CharArray>, enemies: List<Placeable>) {
        enemies.filter { !(it is Ghost && !it.isVisible) }.forEach {
            val (x, y) = it.pos
            it as Enemy
            playground[y][x] = if (it is Mimic && !it.isImitation) {
                it.trueMapChar
            } else {
                it.mapChar
            }
        }
    }

    private fun updateItemPositions(playground: Array<CharArray>, items: List<Placeable>) {
        items.forEach {
            val (x, y) = it.pos
            playground[y][x] = when (it) {
                is Elixir -> ELIXIR
                is Food -> FOOD
                is Treasure -> TREASURE
                is Weapon -> WEAPON
                is Scroll -> SCROLL
                is Exit -> EXIT
                else -> ELSE
            }
        }
    }

    private fun isNonPlaceableElement(char: Char): Boolean {
        return when (char) {
            WALL_CHAR, CORRIDOR_CHAR, OUTER_AREA_CHAR, INNER_AREA_CHAR, EXIT -> true
            else -> false
        }
    }
}
