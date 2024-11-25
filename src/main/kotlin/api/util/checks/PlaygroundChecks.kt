package api.util.checks

import api.entity.Entity
import api.entity.environment.DungeonMap
import api.entity.environment.Position
import api.util.Direction
import api.util.constant.MapChar.ELIXIR
import api.util.constant.MapChar.EXIT
import api.util.constant.MapChar.FOOD
import api.util.constant.MapChar.GHOST
import api.util.constant.MapChar.MIMIC
import api.util.constant.MapChar.OGRE
import api.util.constant.MapChar.PLAYER
import api.util.constant.MapChar.SCROLL
import api.util.constant.MapChar.SNAKE_MAGE
import api.util.constant.MapChar.TREASURE
import api.util.constant.MapChar.VAMPIRE
import api.util.constant.MapChar.WEAPON
import api.util.constant.MapChar.ZOMBIE
import api.util.constant.SymbolConstants
import api.util.constant.SymbolConstants.CORRIDOR_CHAR
import api.util.constant.SymbolConstants.INNER_AREA_CHAR

object PlaygroundChecks {
    fun isInnerAreaOrCorridorOrPlayerPos(pos: Position, playground: Array<CharArray>): Boolean {
        val cell = playground[pos.y][pos.x]
        return cell == INNER_AREA_CHAR || cell == PLAYER || cell == CORRIDOR_CHAR
    }

    fun isEmptyCharPos(pos: Position, playground: Array<CharArray>): Boolean {
        return playground[pos.y][pos.x] == INNER_AREA_CHAR
    }

    fun isValidPosForPlayer(pos: Position, playground: Array<CharArray>): Boolean {
        val cell = playground[pos.y][pos.x]
        val validSymbols = setOf(
            INNER_AREA_CHAR,
            CORRIDOR_CHAR,
            FOOD,
            ELIXIR,
            WEAPON,
            SCROLL,
            TREASURE,
            EXIT,
        )
        return cell in validSymbols
    }

    fun isItemPosition(pos: Position, playground: Array<CharArray>): Boolean {
        val cell = playground[pos.y][pos.x]
        val itemChars = setOf(
            FOOD,
            ELIXIR,
            WEAPON,
            SCROLL,
            TREASURE,
            EXIT,
        )
        return cell in itemChars
    }

    fun isWall(pos: Position, playground: Array<CharArray>): Boolean {
        val cell = playground[pos.y][pos.x]
        return cell == SymbolConstants.WALL_CHAR
    }

    fun findEnemyForward(
        playerPos: Position,
        dungeonMap: DungeonMap,
        direction: Direction,
    ): Entity? {
        var (x, y) = playerPos

        when (direction) {
            Direction.UP -> y -= 1
            Direction.DOWN -> y += 1
            Direction.LEFT -> x -= 1
            Direction.RIGHT -> x += 1
        }

        val cell = dungeonMap.playground[y][x]
        val enemyChars = setOf(OGRE, ZOMBIE, GHOST, SNAKE_MAGE, VAMPIRE, MIMIC)
        return if (cell in enemyChars) {
            dungeonMap.enemies.find { it.pos == Position(x, y) } as Entity?
        } else {
            null
        }
    }


    fun isPlayerNearby(enemyPos: Position, playground: Array<CharArray>): Boolean {
        val (x, y) = enemyPos

        return (y > 0 && playground[y - 1][x] == PLAYER) ||
                (y < playground.size - 1 && playground[y + 1][x] == PLAYER) ||
                (x > 0 && playground[y][x - 1] == PLAYER) ||
                (x < playground[0].size - 1 && playground[y][x + 1] == PLAYER)
    }
}