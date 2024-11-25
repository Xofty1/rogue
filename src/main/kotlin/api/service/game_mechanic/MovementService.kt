package api.service.game_mechanic

import api.entity.behavior.Placeable
import api.entity.enemy.Ogre
import api.entity.enemy.SnakeMage
import api.entity.environment.Position
import api.util.Direction
import api.util.checks.PlaygroundChecks
import kotlin.random.Random

object MovementService {
    private val playgroundChecks = PlaygroundChecks

    private val directions = listOf(
        Position(0, 1),
        Position(0, -1),
        Position(1, 0),
        Position(-1, 0)
    )

    fun calculatePlayerNextPos(
        playerPos: Position,
        direction: Direction,
        playground: Array<CharArray>
    ): Position? {
        val newPos = when (direction) {
            Direction.UP -> Position(playerPos.x, playerPos.y - 1)
            Direction.DOWN -> Position(playerPos.x, playerPos.y + 1)
            Direction.LEFT -> Position(playerPos.x - 1, playerPos.y)
            Direction.RIGHT -> Position(playerPos.x + 1, playerPos.y)
        }

        return if (playgroundChecks.isValidPosForPlayer(newPos, playground)) {
            newPos
        } else {
            null
        }
    }

    fun calculateEnemyNextPos(
        enemy: Placeable,
        playerPos: Position,
        playground: Array<CharArray>
    ): Position? {
        val positions = findPath(enemy.pos, playerPos, playground)

        positions?.let {
            return when (enemy) {
                is Ogre -> {
                    if (positions.size > 1 && positions[1] != playerPos) positions[1] else positions[0]
                }

                is SnakeMage -> {
                    val newPos = getPosForSnakeMage(enemy.pos, playerPos, positions)
                    if (playgroundChecks.isEmptyCharPos(newPos, playground)) newPos else null
                }

                else -> positions.first()
            }
        }
        return null
    }

    fun getRandomPosForGhost(ghostPos: Position, playground: Array<CharArray>): Position {
        val stack: MutableList<Position> = mutableListOf()
        val visited: MutableSet<Position> = mutableSetOf()

        stack.add(ghostPos)
        visited.add(ghostPos)

        val emptyPositions = mutableListOf<Position>()

        while (stack.isNotEmpty()) {
            val current = stack.removeAt(stack.size - 1)

            directions.forEach { direction ->
                val newPos = Position(current.x + direction.x, current.y + direction.y)

                if (playgroundChecks.isEmptyCharPos(newPos, playground) && newPos !in visited) {
                    stack.add(newPos)
                    visited.add(newPos)

                    emptyPositions.add(newPos)
                }
            }
        }

        return if (emptyPositions.isNotEmpty()) {
            emptyPositions.random()
        } else {
            ghostPos
        }
    }

    private fun getPosForSnakeMage(
        enemyPos: Position,
        playerPos: Position,
        positions: List<Position>
    ): Position {
        val direction = getDirectionTowardsPlayer(enemyPos, playerPos)
        val (x, y) = positions.first()

        return when (direction) {
            Direction.DOWN -> Position(x + getRandomPlusMinusOne(), y)
            Direction.UP -> Position(x + getRandomPlusMinusOne(), y)
            Direction.LEFT -> Position(x, y + getRandomPlusMinusOne())
            Direction.RIGHT -> Position(x, y + getRandomPlusMinusOne())
        }
    }

    private fun findPath(
        enemyPos: Position,
        playerPos: Position,
        playground: Array<CharArray>
    ): List<Position>? {
        val queue: MutableList<Position> = mutableListOf()
        val visited: MutableSet<Position> = mutableSetOf()

        queue.add(enemyPos)
        visited.add(enemyPos)

        val pathMap = mutableMapOf<Position, Position?>()

        while (queue.isNotEmpty()) {
            val current = queue.removeAt(0)

            if (playerPos == current) {
                return reconstructPath(pathMap, current)
            }
            directions.forEach {
                val newPos = Position(current.x + it.x, current.y + it.y)

                if (playgroundChecks.isInnerAreaOrCorridorOrPlayerPos(
                        newPos,
                        playground
                    ) && newPos !in visited
                ) {
                    queue.add(newPos)
                    visited.add(newPos)
                    pathMap[newPos] = current
                }
            }
        }

        return null
    }

    private fun reconstructPath(
        pathMap: Map<Position, Position?>,
        endPos: Position
    ): List<Position> {
        val path = mutableListOf<Position>()
        var current: Position? = endPos

        while (current != null) {
            path.add(current)
            current = pathMap[current]
        }

        val finalPath = path.reversed()
        return if (finalPath.size > 1) finalPath.drop(1) else finalPath
    }

    private fun getDirectionTowardsPlayer(enemyPos: Position, playerPos: Position): Direction {
        return when {
            playerPos.y < enemyPos.y -> Direction.UP
            playerPos.y > enemyPos.y -> Direction.DOWN
            playerPos.x < enemyPos.x -> Direction.LEFT
            playerPos.x > enemyPos.x -> Direction.RIGHT
            else -> Direction.UP
        }
    }

    private fun getRandomPlusMinusOne(): Int {
        return Random.nextInt(-1, 1)
    }
}
