package api.service.generator

import api.entity.behavior.Placeable
import api.entity.environment.Position
import api.entity.environment.Room
import api.util.constant.DungeonConstants.DUNGEON_LEVEL_DIVISOR
import api.util.constant.DungeonConstants.SAFE_SPACE_AROUND_PLAYER
import api.util.constant.DungeonConstants.START_DUNGEON_LEVEL
import api.util.constant.RoomConstants.MAX_ENEMIES_PER_ROOM
import api.util.constant.RoomConstants.MAX_ITEMS_PER_ROOM
import api.util.constant.RoomConstants.MIN_ENEMIES_PER_ROOM
import api.util.constant.RoomConstants.MIN_ITEMS_PER_ROOM
import api.util.factory.EnemyFactory
import api.util.factory.ItemFactory
import kotlin.random.Random

object ContentGenerator {
    private val itemFactory = ItemFactory
    private val enemyFactory = EnemyFactory

    fun generateContent(
        roomSequence: List<Room>,
        playerPos: Position,
        dungeonLevel: Int = START_DUNGEON_LEVEL
    ) {
        generateExit(roomSequence)
        generateEnemies(roomSequence, playerPos, dungeonLevel)
        generateItems(roomSequence, dungeonLevel)
    }

    fun generatePlayerPos(roomSequence: List<Room>, player: Placeable) {
        val roomIndex = Random.nextInt(roomSequence.size)
        val spawnRoom = roomSequence[roomIndex]
        val playerPos = generateEntityCoords(spawnRoom)

        player.pos = playerPos
        spawnRoom.entities.add(player)
    }

    private fun generateExit(roomSequence: List<Room>) {
        val roomIndex = Random.nextInt(roomSequence.size)
        val exitRoom = roomSequence[roomIndex]
        val exitPos = generateEntityCoords(exitRoom)

        val exit = itemFactory.createExit(exitPos)
        exitRoom.entities.add(exit)
    }

    private fun generateEnemies(roomSequence: List<Room>, playerPos: Position, dungeonLevel: Int) {
        val currentMaxEnemies = if (MIN_ENEMIES_PER_ROOM + dungeonLevel <= MAX_ENEMIES_PER_ROOM) {
            MIN_ENEMIES_PER_ROOM + dungeonLevel
        } else {
            MAX_ENEMIES_PER_ROOM
        }

        val currentMinEnemies = if (currentMaxEnemies == MAX_ENEMIES_PER_ROOM) {
            (MIN_ENEMIES_PER_ROOM + (dungeonLevel - (MAX_ENEMIES_PER_ROOM - MIN_ENEMIES_PER_ROOM)))
                .coerceAtMost(MAX_ENEMIES_PER_ROOM - 1)
        } else {
            MIN_ENEMIES_PER_ROOM
        }

        roomSequence.forEach { room ->
            repeat(Random.nextInt(currentMinEnemies, currentMaxEnemies)) {
                var enemy: Placeable
                do {
                    enemy = generateEnemy(room, dungeonLevel)
                } while (isWithinRadius(enemy.pos, playerPos, SAFE_SPACE_AROUND_PLAYER))

                room.entities.add(enemy)
            }
        }
    }

    private fun generateEnemy(room: Room, dungeonLevel: Int): Placeable {
        val pos = generateEntityCoords(room)
        val randomEnemy = enemyFactory.createRandomEnemy(pos, dungeonLevel)

        return randomEnemy
    }

    private fun generateEntityCoords(room: Room): Position {
        val topX = room.topLeft.x
        val topY = room.topLeft.y
        val botX = room.botRight.x
        val botY = room.botRight.y
        var pos: Position

        do {
            val x = Random.nextInt(topX + 1, botX)
            val y = Random.nextInt(topY + 1, botY)
            pos = Position(x, y)
        } while (checkUnoccupied(room, pos))

        return pos
    }

    private fun checkUnoccupied(room: Room, pos: Position): Boolean {
        return room.entities.any { it.pos == pos }
    }

    private fun generateItems(roomSequence: List<Room>, dungeonLevel: Int) {
        val currentMaxItems = (MAX_ITEMS_PER_ROOM - dungeonLevel / DUNGEON_LEVEL_DIVISOR)
            .coerceAtLeast(MIN_ITEMS_PER_ROOM + 1)

        roomSequence.forEach { room ->
            repeat(Random.nextInt(MIN_ITEMS_PER_ROOM, currentMaxItems)) {
                val pos = generateEntityCoords(room)
                val item = itemFactory.createRandomItem(pos)
                room.entities.add(item)
            }
        }
    }

    private fun isWithinRadius(pos1: Position, pos2: Position, radius: Int): Boolean {
        val dx = pos1.x - pos2.x
        val dy = pos1.y - pos2.y
        return dx * dx + dy * dy <= radius * radius
    }
}