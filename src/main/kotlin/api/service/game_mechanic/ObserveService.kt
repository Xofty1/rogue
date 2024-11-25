package api.service.game_mechanic

import api.entity.behavior.Placeable
import api.entity.enemy.Enemy
import api.entity.environment.Position
import api.util.checks.PlaygroundChecks
import kotlin.math.abs

object ObserveService {
    private val playgroundChecks = PlaygroundChecks

    fun canSeePlayer(
        enemy: Placeable,
        playerPos: Position,
        playground: Array<CharArray>
    ): Boolean {
        enemy as Enemy

        if (getDistance(enemy.pos, playerPos) > enemy.hostility) return false

        return hasLineOfSight(enemy.pos, playerPos, playground)
    }

    private fun getDistance(pos1: Position, pos2: Position): Int {
        return abs(pos1.x - pos2.x) + abs(pos1.y - pos2.y)
    }

    private fun hasLineOfSight(
        enemyPos: Position,
        playerPos: Position,
        playground: Array<CharArray>
    ): Boolean {
        val dx = playerPos.x - enemyPos.x
        val dy = playerPos.y - enemyPos.y

        val steps = maxOf(abs(dx), abs(dy))
        val xStep = if (steps == 0) 0 else dx / steps
        val yStep = if (steps == 0) 0 else dy / steps

        for (i in 0..steps) {
            val newX = enemyPos.x + i * xStep
            val newY = enemyPos.y + i * yStep
            val newPos = Position(newX, newY)

            if (playgroundChecks.isWall(newPos, playground)) return false
        }

        return true
    }
}
