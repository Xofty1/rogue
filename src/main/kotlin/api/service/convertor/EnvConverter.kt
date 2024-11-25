package api.service.convertor

import api.entity.Player
import api.entity.enemy.Enemy
import api.entity.enemy.Mimic
import api.entity.environment.*
import api.entity.item.*
import api.util.constant.MapChar.ELIXIR
import api.util.constant.MapChar.ELSE
import api.util.constant.MapChar.EXIT
import api.util.constant.MapChar.FOOD
import api.util.constant.MapChar.SCROLL
import api.util.constant.MapChar.TREASURE
import api.util.constant.MapChar.WEAPON
import api.util.constant.SymbolConstants.CORRIDOR_CHAR
import api.util.constant.SymbolConstants.INNER_AREA_CHAR
import api.util.constant.SymbolConstants.WALL_CHAR

object EnvConverter {

    fun dungeonToMap(dungeon: Dungeon): DungeonMap {
        val dungeonMap = DungeonMap()

        roomsToMap(dungeon.rooms, dungeonMap)
        corridorsToMap(dungeon.corridors, dungeonMap.playground, dungeonMap.corridors)

        return dungeonMap
    }

    private fun roomsToMap(rooms: Array<Array<Room>>, dungeonMap: DungeonMap) {
        rooms.forEach { row ->
            row.forEach { room ->
                with(room) {
                    rectangleToMap(topLeft, botRight, dungeonMap.playground)
                    fillRectangle(topLeft, botRight, dungeonMap.playground)
                    processRoomContent(this, dungeonMap)
                }
            }
        }
    }

    private fun processRoomContent(room: Room, dungeonMap: DungeonMap) {
        room.entities.forEach { curEntity ->
            dungeonMap.playground[curEntity.pos.y][curEntity.pos.x] = when (curEntity) {
                is Player -> {
                    dungeonMap.player = curEntity
                    curEntity.mapChar
                }

                is Enemy -> {
                    dungeonMap.enemies.add(curEntity)
                    if (curEntity is Mimic && !curEntity.isImitation) {
                        curEntity.trueMapChar
                    } else {
                        curEntity.mapChar
                    }
                }

                is Item -> {
                    dungeonMap.items.add(curEntity)
                    when (curEntity) {
                        is Elixir -> ELIXIR
                        is Food -> FOOD
                        is Treasure -> TREASURE
                        is Weapon -> WEAPON
                        is Scroll -> SCROLL
                        is Exit -> EXIT
                        else -> ELSE
                    }
                }

                else -> ELSE
            }
        }
    }

    private fun rectangleToMap(top: Position, bot: Position, playground: Array<CharArray>) {
        playground[top.y][top.x] = WALL_CHAR
        playground[top.y][bot.x] = WALL_CHAR
        playground[bot.y][top.x] = WALL_CHAR
        playground[bot.y][bot.x] = WALL_CHAR

        for (i in (top.x + 1) until bot.x) {
            playground[top.y][i] = WALL_CHAR
            playground[bot.y][i] = WALL_CHAR
        }

        for (i in (top.y + 1) until bot.y) {
            playground[i][top.x] = WALL_CHAR
            playground[i][bot.x] = WALL_CHAR
        }
    }

    private fun fillRectangle(top: Position, bot: Position, playground: Array<CharArray>) {
        ((top.y + 1) until bot.y).forEach { i ->
            ((top.x + 1) until bot.x).forEach { j ->
                playground[i][j] = INNER_AREA_CHAR
            }
        }
    }

    private fun corridorsToMap(
        corridors: List<Corridor>,
        playground: Array<CharArray>,
        mapCorridors: MutableList<Position>
    ) {
        for (corridor in corridors) {
            if (corridor.points.size < 1) {
                println("Corridor has insufficient points to draw: ${corridor.type}")
                continue
            }

            for (i in 0 until corridor.points.size - 1) {
                val startPoint = corridor.points[i]
                val endPoint = corridor.points[i + 1]

                if (startPoint.y == endPoint.y) {
                    drawHorizontalLine(startPoint, endPoint, playground, mapCorridors)
                } else if (startPoint.x == endPoint.x) {
                    drawVerticalLine(startPoint, endPoint, playground, mapCorridors)
                }
            }
        }
    }

    private fun drawHorizontalLine(
        firstDot: Position,
        secondDot: Position,
        playground: Array<CharArray>,
        mapCorridors: MutableList<Position>
    ) {
        (firstDot.x.coerceAtMost(secondDot.x)..firstDot.x.coerceAtLeast(secondDot.x))
            .forEach { x ->
                playground[firstDot.y][x] = CORRIDOR_CHAR
                mapCorridors.add(Position(x, firstDot.y))
            }
    }

    private fun drawVerticalLine(
        firstDot: Position,
        secondDot: Position,
        playground: Array<CharArray>,
        mapCorridors: MutableList<Position>
    ) {
        (firstDot.y.coerceAtMost(secondDot.y)..firstDot.y.coerceAtLeast(secondDot.y))
            .forEach { y ->
                playground[y][firstDot.x] = CORRIDOR_CHAR
                mapCorridors.add(Position(firstDot.x, y))
            }
    }
}