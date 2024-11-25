package presentation.util

import api.util.constant.SymbolConstants.WALL_CHAR
import kotlin.math.abs

object FogOfWarMapper {

    private const val PLAYER = '@'
    const val UNKNOWN_FIELD = 'U'
    private const val KNOWN_FIELD = 'K'
    const val FOGGED_FIELD = 'F'

    private const val UNKNOWN_FIELD_OFFSET = 2
    private const val FOGGED_FIELD_OFFSET = 2

    private const val MAP_HEIGHT = 30
    private const val MAP_WIDTH = 54

    private var level = 1
    private var unknownPlayground = Array(MAP_HEIGHT) { CharArray(MAP_WIDTH) { UNKNOWN_FIELD } }
    private var foggedPlayground = Array(MAP_HEIGHT) { CharArray(MAP_WIDTH) { FOGGED_FIELD } }

    fun mapPlaygroundToUnknownPlayground(
        playground: Array<CharArray>,
        dungeonLevel: Int
    ): Array<CharArray> {
        if (dungeonLevel != level) {
            if (dungeonLevel == 1) {
                clearMapper()
            } else {
                updateMapper()
            }
        }

        val (yPos, xPos) = findPlayerPosition(playground)
        val xMin = maxOf(xPos - UNKNOWN_FIELD_OFFSET, 0)
        val xMax = minOf(xPos + UNKNOWN_FIELD_OFFSET, MAP_WIDTH - 1)
        val yMin = maxOf(yPos - UNKNOWN_FIELD_OFFSET, 0)
        val yMax = minOf(yPos + UNKNOWN_FIELD_OFFSET, MAP_HEIGHT - 1)

        for (y in 0 until MAP_HEIGHT) {
            for (x in 0 until MAP_WIDTH) {
                if (x in xMin..xMax && y in yMin..yMax && hasLineOfSight(
                        playground,
                        xPos,
                        yPos,
                        x,
                        y
                    )
                ) {
                    unknownPlayground[y][x] = KNOWN_FIELD
                }
            }
        }

        return unknownPlayground
    }

    fun mapPlaygroundToFoggedPlayground(
        playground: Array<CharArray>,
        dungeonLevel: Int
    ): Array<CharArray> {
        if (dungeonLevel != level) {
            updateMapper()
        }

        val (yPos, xPos) = findPlayerPosition(playground)
        val xMin = maxOf(xPos - FOGGED_FIELD_OFFSET, 0)
        val xMax = minOf(xPos + FOGGED_FIELD_OFFSET, MAP_WIDTH - 1)
        val yMin = maxOf(yPos - FOGGED_FIELD_OFFSET, 0)
        val yMax = minOf(yPos + FOGGED_FIELD_OFFSET, MAP_HEIGHT - 1)

        for (y in 0 until MAP_HEIGHT) {
            for (x in 0 until MAP_WIDTH) {
                if (x in xMin..xMax && y in yMin..yMax && hasLineOfSight(
                        playground,
                        xPos,
                        yPos,
                        x,
                        y
                    )
                ) {
                    foggedPlayground[y][x] = playground[y][x]
                    unknownPlayground[y][x] = playground[y][x]
                } else if (unknownPlayground[y][x] != UNKNOWN_FIELD) {
                    foggedPlayground[y][x] = FOGGED_FIELD
                } else {
                    foggedPlayground[y][x] = UNKNOWN_FIELD
                }
            }
        }
        return foggedPlayground
    }

    private fun hasLineOfSight(
        playground: Array<CharArray>,
        x1: Int,
        y1: Int,
        x2: Int,
        y2: Int
    ): Boolean {
        val dx = abs(x2 - x1)
        val dy = abs(y2 - y1)
        val sx = if (x1 < x2) 1 else -1
        val sy = if (y1 < y2) 1 else -1
        var err = dx - dy

        var x = x1
        var y = y1
        var passedWall = false

        while (true) {
            if (playground[y][x] == WALL_CHAR) {
                passedWall = true
            }

            if (passedWall && (x != x2 || y != y2)) {
                return false
            }

            if (x == x2 && y == y2) return true

            val e2 = 2 * err
            if (e2 > -dy) {
                err -= dy
                x += sx
            }
            if (e2 < dx) {
                err += dx
                y += sy
            }
        }
    }

    private fun updateMapper() {
        level++
        unknownPlayground = Array(MAP_HEIGHT) { CharArray(MAP_WIDTH) { UNKNOWN_FIELD } }
        foggedPlayground = Array(MAP_HEIGHT) { CharArray(MAP_WIDTH) { FOGGED_FIELD } }
    }

    fun clearMapper() {
        level = 1
        unknownPlayground = Array(MAP_HEIGHT) { CharArray(MAP_WIDTH) { UNKNOWN_FIELD } }
        foggedPlayground = Array(MAP_HEIGHT) { CharArray(MAP_WIDTH) { FOGGED_FIELD } }
    }

    private fun findPlayerPosition(playground: Array<CharArray>): Pair<Int, Int> {
        for (y in playground.indices) {
            for (x in playground[y].indices) {
                if (playground[y][x] == PLAYER) {
                    return Pair(y, x)
                }
            }
        }
        throw RuntimeException("Player not found")
    }
}