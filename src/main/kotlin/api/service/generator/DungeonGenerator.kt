package api.service.generator

import kotlin.random.Random

const val ROOMS_PER_SIDE = 10
const val MAX_ROOMS_NUMBER = 100
const val MAX_CORRIDORS_NUMBER = 100
const val ROOM_CHANCE = 0.5
const val UNINITIALIZED = -1
const val SECTOR_HEIGHT = 10
const val SECTOR_WIDTH = 10
const val CORNER_VERT_RANGE = 3
const val CORNER_HOR_RANGE = 3

enum class Direction { TOP, RIGHT, BOTTOM, LEFT }

data class Door(var x: Int = UNINITIALIZED, var y: Int = UNINITIALIZED)
data class Position(var x: Int = 0, var y: Int = 0)
data class Room(
    var sector: Int = UNINITIALIZED,
    val connections: MutableList<Room?> = MutableList(4) { null },
    val doors: MutableList<Door> = MutableList(4) { Door() },
    var grid_i: Int = 0,
    var grid_j: Int = 0,
    var entities_cnt: Int = 0,
    var topLeft: Position = Position(),
    var botRight: Position = Position()
)

data class Corridor(
    var type: Int = UNINITIALIZED,
    val points: MutableList<Position> = mutableListOf(),
    var points_cnt: Int = 0
)

class Dungeon {
    var roomCnt = 0
    var corridorsCnt = 0
    val rooms: Array<Array<Room>> =
        Array(ROOMS_PER_SIDE + 2) { Array(ROOMS_PER_SIDE + 2) { Room() } }
    val sequence: Array<Room?> = arrayOfNulls(MAX_ROOMS_NUMBER)
    val corridors: Array<Corridor> = Array(MAX_CORRIDORS_NUMBER) { Corridor() }
}

fun generateDungeon(dungeon: Dungeon) {
    initDungeon(dungeon)
    generateSectors(dungeon)
    generateConnections(dungeon)
    generateRoomsGeometry(dungeon)
    generateCorridorsGeometry(dungeon)
}

fun initDungeon(dungeon: Dungeon) {
    dungeon.roomCnt = 0
    for (i in 0 until ROOMS_PER_SIDE + 2) {
        for (j in 0 until ROOMS_PER_SIDE + 2) {
            dungeon.rooms[i][j].sector = UNINITIALIZED
            for (k in 0 until 4) {
                dungeon.rooms[i][j].connections[k] = null
                dungeon.rooms[i][j].doors[k].x = UNINITIALIZED
                dungeon.rooms[i][j].doors[k].y = UNINITIALIZED
            }
            dungeon.rooms[i][j].entities_cnt = 0
        }
    }

    for (i in 0 until MAX_ROOMS_NUMBER) {
        dungeon.sequence[i] = null
    }
    for (i in 0 until MAX_CORRIDORS_NUMBER) {
        dungeon.corridors[i].type = UNINITIALIZED
    }

    dungeon.corridorsCnt = 0
}

fun generateSectors(dungeon: Dungeon) {
    while (dungeon.roomCnt < 3) {
        var sector = 0
        for (i in 1 until ROOMS_PER_SIDE + 1) {
            for (j in 1 until ROOMS_PER_SIDE + 1) {
                if (Random.nextDouble() < ROOM_CHANCE && dungeon.rooms[i][j].sector == UNINITIALIZED) {
                    dungeon.rooms[i][j].sector = sector++
                    dungeon.rooms[i][j].grid_i = i
                    dungeon.rooms[i][j].grid_j = j
                    dungeon.sequence[dungeon.roomCnt++] = dungeon.rooms[i][j]
                }
            }
        }
    }

    dungeon.sequence.sortBy { it?.sector }
}

fun generateConnections(dungeon: Dungeon) {
    generatePrimaryConnections(dungeon)
    generateSecondaryConnections(dungeon)
}

fun generatePrimaryConnections(dungeon: Dungeon) {
    for (i in 1 until ROOMS_PER_SIDE + 1) {
        for (j in 1 until ROOMS_PER_SIDE + 1) {
            val room = dungeon.rooms[i][j]
            if (room.sector != UNINITIALIZED) {
                if (dungeon.rooms[i - 1][j].sector != UNINITIALIZED) room.connections[Direction.TOP.ordinal] =
                    dungeon.rooms[i - 1][j]
                if (dungeon.rooms[i][j + 1].sector != UNINITIALIZED) room.connections[Direction.RIGHT.ordinal] =
                    dungeon.rooms[i][j + 1]
                if (dungeon.rooms[i + 1][j].sector != UNINITIALIZED) room.connections[Direction.BOTTOM.ordinal] =
                    dungeon.rooms[i + 1][j]
                if (dungeon.rooms[i][j - 1].sector != UNINITIALIZED) room.connections[Direction.LEFT.ordinal] =
                    dungeon.rooms[i][j - 1]
            }
        }
    }
}

fun generateSecondaryConnections(dungeon: Dungeon) {
    for (i in 0 until dungeon.roomCnt - 1) {
        val cur = dungeon.sequence[i]
        val next = dungeon.sequence[i + 1]

        if (cur != null && next != null) {
            if (cur.grid_i == next.grid_i && next.connections[Direction.LEFT.ordinal] == null) {
                cur.connections[Direction.RIGHT.ordinal] = next
                next.connections[Direction.LEFT.ordinal] = cur
            }
        }
    }
}


fun generateRoomsGeometry(dungeon: Dungeon) {
    for (i in 1 until ROOMS_PER_SIDE + 1) {
        for (j in 1 until ROOMS_PER_SIDE + 1) {
            val room = dungeon.rooms[i][j]
            if (room.sector != UNINITIALIZED) {
                generateCorners(room, (i - 1) * SECTOR_HEIGHT, (j - 1) * SECTOR_WIDTH)
                generateDoors(room)
            }
        }
    }
}

fun generateCorners(room: Room, offsetY: Int, offsetX: Int) {
    room.topLeft.y = Random.nextInt(CORNER_VERT_RANGE) + offsetY + 1
    room.topLeft.x = Random.nextInt(CORNER_HOR_RANGE) + offsetX + 1
    room.botRight.y = SECTOR_HEIGHT - 1 - Random.nextInt(CORNER_VERT_RANGE) + offsetY - 1
    room.botRight.x = SECTOR_WIDTH - 1 - Random.nextInt(CORNER_HOR_RANGE) + offsetX - 1
}

fun generateDoors(room: Room) {
    if (room.connections[Direction.TOP.ordinal] != null) {
        room.doors[Direction.TOP.ordinal].y = room.topLeft.y
        room.doors[Direction.TOP.ordinal].x =
            Random.nextInt(room.botRight.x - room.topLeft.x - 1) + room.topLeft.x + 1
    }

    if (room.connections[Direction.RIGHT.ordinal] != null) {
        room.doors[Direction.RIGHT.ordinal].y =
            Random.nextInt(room.botRight.y - room.topLeft.y - 1) + room.topLeft.y + 1
        room.doors[Direction.RIGHT.ordinal].x = room.botRight.x
    }

    if (room.connections[Direction.BOTTOM.ordinal] != null) {
        room.doors[Direction.BOTTOM.ordinal].y = room.botRight.y
        room.doors[Direction.BOTTOM.ordinal].x =
            Random.nextInt(room.botRight.x - room.topLeft.x - 1) + room.topLeft.x + 1
    }

    if (room.connections[Direction.LEFT.ordinal] != null) {
        room.doors[Direction.LEFT.ordinal].y =
            Random.nextInt(room.botRight.y - room.topLeft.y - 1) + room.topLeft.y + 1
        room.doors[Direction.LEFT.ordinal].x = room.topLeft.x
    }
}

fun generateCorridorsGeometry(dungeon: Dungeon) {
    for (i in 1 until ROOMS_PER_SIDE + 1) {
        for (j in 1 until ROOMS_PER_SIDE + 1) {
            val curRoom = dungeon.rooms[i][j]
            if (curRoom.connections[Direction.RIGHT.ordinal] != null && curRoom.connections[Direction.RIGHT.ordinal]?.connections?.get(
                    Direction.LEFT.ordinal
                ) == curRoom
            ) {
                generateLeftToRightCorridor(
                    dungeon,
                    curRoom,
                    curRoom.connections[Direction.RIGHT.ordinal]!!
                )
            }
        }
    }
}

fun generateLeftToRightCorridor(dungeon: Dungeon, leftRoom: Room, rightRoom: Room) {
    val corridor = Corridor()
    corridor.type = 1

    corridor.points.add(
        Position(
            leftRoom.doors[Direction.RIGHT.ordinal].x,
            leftRoom.doors[Direction.RIGHT.ordinal].y
        )
    )

    val xMin = leftRoom.doors[Direction.RIGHT.ordinal].x
    val xMax = rightRoom.doors[Direction.LEFT.ordinal].x

    val randomCenterX = Random.nextInt(xMin, xMax)
    corridor.points.add(Position(randomCenterX, leftRoom.doors[Direction.RIGHT.ordinal].y))
    corridor.points.add(Position(randomCenterX, rightRoom.doors[Direction.LEFT.ordinal].y))

    corridor.points.add(
        Position(
            rightRoom.doors[Direction.LEFT.ordinal].x,
            rightRoom.doors[Direction.LEFT.ordinal].y
        )
    )

    dungeon.corridors[dungeon.corridorsCnt++] = corridor
}

fun printDungeon(dungeon: Dungeon) {
    println("Dungeon Overview:")
    println("Total Rooms: ${dungeon.roomCnt}")
    println("Total Corridors: ${dungeon.corridorsCnt}")

    println("\nRooms:")
    for (i in 1 until ROOMS_PER_SIDE + 1) {
        for (j in 1 until ROOMS_PER_SIDE + 1) {
            val room = dungeon.rooms[i][j]
            if (room.sector != UNINITIALIZED) {
                println("Room at [$i, $j]: Sector = ${room.sector}, Entities = ${room.entities_cnt}")
                println("  Connections: ${room.connections.map { it?.sector ?: "null" }}")
                println("  Doors: ${room.doors.map { "(${it.x}, ${it.y})" }}")
            }
        }
    }

    println("\nCorridors:")
    for (i in 0 until dungeon.corridorsCnt) {
        val corridor = dungeon.corridors[i]
        println("Corridor ${i + 1}: Type = ${corridor.type}, Points = ${corridor.points.joinToString { "(${it.x}, ${it.y})" }}")
    }
}

fun main() {
    val dungeon = Dungeon()
    generateDungeon(dungeon)
    println("Dungeon generated with ${dungeon.roomCnt} rooms and ${dungeon.corridorsCnt} corridors.")
    printDungeon(dungeon)
}
