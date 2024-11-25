package api.service.generator

import api.entity.environment.Corridor
import api.entity.environment.Dungeon
import api.entity.environment.Position
import api.entity.environment.Room
import api.util.CorridorType
import api.util.constant.ConnectionConstants.BOTTOM
import api.util.constant.ConnectionConstants.LEFT
import api.util.constant.ConnectionConstants.RIGHT
import api.util.constant.ConnectionConstants.TOP
import api.util.constant.DungeonConstants.MIN_ROOM_COUNT
import api.util.constant.RoomConstants.CORNER_HOR_RANGE
import api.util.constant.RoomConstants.CORNER_VERT_RANGE
import api.util.constant.SectorConstants.ROOMS_PER_SIDE
import api.util.constant.SectorConstants.ROOM_CHANCE
import api.util.constant.SectorConstants.SECTOR_HEIGHT
import api.util.constant.SectorConstants.SECTOR_WIDTH
import api.util.constant.UNINITIALIZED

object EnvGenerator {

    fun generateDungeon(): Dungeon {
        val dungeon = Dungeon()

        generateSectors(dungeon)
        generateConnections(dungeon.rooms, dungeon.roomSequence)
        generateRoomsGeometry(dungeon.rooms)
        generateCorridorsGeometry(dungeon)

        while (!checkConnectivity(dungeon.roomSequence)) {
            println("One more try to generate dungeon...")
            generateDungeon()
        }

        return dungeon
    }

    private fun generateSectors(dungeon: Dungeon) {
        var sector = 0
        var roomCount = 0

        while (roomCount < MIN_ROOM_COUNT) {

            for (i in 1..ROOMS_PER_SIDE) {
                for (j in 1..ROOMS_PER_SIDE) {
                    if (Math.random() < ROOM_CHANCE &&
                        dungeon.rooms[i][j].sector == UNINITIALIZED
                    ) {
                        dungeon.rooms[i][j].sector = sector++
                        dungeon.rooms[i][j].gridI = i
                        dungeon.rooms[i][j].gridJ = j
                        dungeon.roomSequence.add(dungeon.rooms[i][j])
                        roomCount++
                    }
                }
            }
        }

        dungeon.roomSequence.sortWith { r1, r2 -> r1.sector - r2.sector }
    }

    private fun generateConnections(
        rooms: Array<Array<Room>>,
        roomSequence: List<Room>
    ) {
        generatePrimaryConnections(rooms)
        generateSecondaryConnections(roomSequence)
    }

    private fun generatePrimaryConnections(rooms: Array<Array<Room>>) {
        for (i in 1..ROOMS_PER_SIDE) {
            for (j in 1..ROOMS_PER_SIDE) {
                val room = rooms[i][j]
                if (room.sector != UNINITIALIZED) {
                    connectToNeighbors(room, i, j, rooms)
                }
            }
        }
    }

    private fun connectToNeighbors(room: Room, i: Int, j: Int, rooms: Array<Array<Room>>) {
        if (rooms[i - 1][j].sector != UNINITIALIZED) {
            room.connections[TOP] = rooms[i - 1][j]
        }
        if (rooms[i][j + 1].sector != UNINITIALIZED) {
            room.connections[RIGHT] = rooms[i][j + 1]
        }
        if (rooms[i + 1][j].sector != UNINITIALIZED) {
            room.connections[BOTTOM] = rooms[i + 1][j]
        }
        if (rooms[i][j - 1].sector != UNINITIALIZED) {
            room.connections[LEFT] = rooms[i][j - 1]
        }
    }

    private fun generateSecondaryConnections(roomSequence: List<Room>) {
        for (i in 0 until roomSequence.size - 1) {
            val cur = roomSequence[i]
            val next = roomSequence[i + 1]

            when {
                cur.gridI == next.gridI && next.connections[LEFT] == null -> {
                    cur.connections[RIGHT] = next
                    next.connections[LEFT] = cur
                }

                cur.gridI - next.gridI == -1 && cur.connections[BOTTOM] == null -> {
                    when {
                        cur.gridJ < next.gridJ && next.connections[LEFT] == null -> {
                            cur.connections[BOTTOM] = next
                            next.connections[LEFT] = cur
                        }

                        cur.gridJ > next.gridJ && next.connections[RIGHT] == null -> {
                            cur.connections[BOTTOM] = next
                            next.connections[RIGHT] = cur
                        }

                        cur.gridJ > next.gridJ && i < roomSequence.size - 2 -> {
                            cur.connections[BOTTOM] = roomSequence[i + 2]
                            roomSequence[i + 2].connections[RIGHT] = cur
                        }
                    }
                }

                cur.gridI - next.gridI == -2 && next.connections[TOP] == null -> {
                    cur.connections[BOTTOM] = next
                    next.connections[TOP] = cur
                }
            }
        }
    }

    private fun generateRoomsGeometry(rooms: Array<Array<Room>>) {
        for (i in 1..ROOMS_PER_SIDE) {
            for (j in 1..ROOMS_PER_SIDE) {
                if (rooms[i][j].sector != UNINITIALIZED) {
                    generateCorners(
                        rooms[i][j],
                        (i - 1) * SECTOR_HEIGHT,
                        (j - 1) * SECTOR_WIDTH
                    )
                    generateDoors(rooms[i][j])
                }
            }
        }
    }

    private fun generateCorners(room: Room, offsetY: Int, offsetX: Int) {
        room.topLeft = Position(
            x = (Math.random() * CORNER_HOR_RANGE).toInt() + offsetX + 1,
            y = (Math.random() * CORNER_VERT_RANGE).toInt() + offsetY + 1
        )
        room.botRight = Position(
            x = SECTOR_WIDTH - 1 - (Math.random() * CORNER_HOR_RANGE).toInt() + offsetX - 1,
            y = SECTOR_HEIGHT - 1 - (Math.random() * CORNER_VERT_RANGE).toInt() + offsetY - 1
        )
    }

    private fun generateDoors(room: Room) {
        val topLeft = room.topLeft
        val botRight = room.botRight
        if (room.connections[TOP] != null) {
            room.doors[TOP] = Position(
                x = getRandomCoordinate(botRight.x, topLeft.x), y = topLeft.y
            )
        }

        if (room.connections[RIGHT] != null) {
            room.doors[RIGHT] = Position(
                x = botRight.x, y = getRandomCoordinate(botRight.y, topLeft.y)
            )
        }

        if (room.connections[BOTTOM] != null) {
            room.doors[BOTTOM] = Position(
                x = getRandomCoordinate(botRight.x, topLeft.x), y = botRight.y
            )
        }

        if (room.connections[LEFT] != null) {
            room.doors[LEFT] = Position(
                x = topLeft.x, y = getRandomCoordinate(botRight.y, topLeft.y)
            )
        }
    }

    private fun getRandomCoordinate(a: Int, b: Int): Int {
        return Math.round(Math.random() * (a - b - 2)).toInt() + b + 1
    }

    private fun generateCorridorsGeometry(dungeon: Dungeon) {
        for (i in 1..ROOMS_PER_SIDE) {
            for (j in 1..ROOMS_PER_SIDE) {
                val curRoom = dungeon.rooms[i][j]
                val corridor = Corridor()

                if (curRoom.connections[RIGHT] != null
                    && curRoom.connections[RIGHT]?.connections?.get(LEFT) == curRoom
                ) {
                    dungeon.corridors.add(corridor)
                    generateLeftToRightCorridor(
                        dungeon.rooms,
                        curRoom,
                        curRoom.connections[RIGHT]!!,
                        corridor
                    )
                }

                curRoom.connections[BOTTOM]?.let { bottomRoom ->
                    dungeon.corridors.add(corridor)
                    generateTopToBottomCorridor(
                        dungeon.rooms,
                        curRoom,
                        bottomRoom,
                        corridor
                    )
                }
            }
        }
    }

    private fun generateLeftToRightCorridor(
        rooms: Array<Array<Room>>,
        leftRoom: Room,
        rightRoom: Room,
        corridor: Corridor
    ) {
        corridor.type = CorridorType.LEFT_TO_RIGHT_CORRIDOR
        corridor.pointsCount = 4
        corridor.points.add(leftRoom.doors[RIGHT])

        var xMin = leftRoom.doors[RIGHT].x
        var xMax = rightRoom.doors[LEFT].x

        for (i in 1 until ROOMS_PER_SIDE + 1) {
            val room = rooms[i][leftRoom.gridJ]
            if (room.sector != UNINITIALIZED && i != leftRoom.gridI) {
                xMin = maxOf(room.botRight.x, xMin)
            }
        }

        for (i in 1 until ROOMS_PER_SIDE + 1) {
            val room = rooms[i][rightRoom.gridJ]
            if (room.sector != UNINITIALIZED && i != rightRoom.gridI) {
                xMax = minOf(room.topLeft.x, xMax)
            }
        }

        if (xMin > xMax) {
            println("Error: xMin ($xMin) greater or equal xMax ($xMax)")
            return
        }

        val randomCenterX = ((Math.random() * (xMax - xMin - 1)).toInt() + 1) + xMin

        val secondPoint = Position(randomCenterX, leftRoom.doors[RIGHT].y)
        val thirdPoint = Position(randomCenterX, rightRoom.doors[LEFT].y)

        corridor.points.add(secondPoint)
        corridor.points.add(thirdPoint)
        corridor.points.add(rightRoom.doors[LEFT])
    }

    private fun generateTopToBottomCorridor(
        rooms: Array<Array<Room>>,
        topRoom: Room,
        bottomRoom: Room,
        corridor: Corridor
    ) {
        corridor.type = CorridorType.TOP_TO_BOTTOM_CORRIDOR
        corridor.pointsCount = 4
        corridor.points.add(topRoom.doors[BOTTOM])

        var yMin = topRoom.doors[BOTTOM].y
        var yMax = bottomRoom.doors[TOP].y

        for (j in 1 until ROOMS_PER_SIDE + 1) {
            if (rooms[topRoom.gridI][j].sector != UNINITIALIZED) {
                yMin = maxOf(rooms[topRoom.gridI][j].botRight.y, yMin)
            }
        }

        for (j in 1 until ROOMS_PER_SIDE + 1) {
            if (rooms[bottomRoom.gridI][j].sector != UNINITIALIZED) {
                yMax = minOf(rooms[bottomRoom.gridI][j].topLeft.y, yMax)
            }
        }

        if (yMin > yMax) {
            println("Error: yMin ($yMin) greater or equal xMax ($yMax)")
            return
        }

        val randomCenterY = (Math.random() * (yMax - yMin - 1)).toInt() + 1 + yMin
        val secondPoint = Position(topRoom.doors[BOTTOM].x, randomCenterY)
        val thirdPoint = Position(bottomRoom.doors[TOP].x, randomCenterY)

        corridor.points.add(secondPoint)
        corridor.points.add(thirdPoint)
        corridor.points.add(bottomRoom.doors[TOP])

        topRoom.connections[BOTTOM] = bottomRoom
        bottomRoom.connections[TOP] = topRoom
    }

    private fun checkConnectivity(roomSequence: List<Room>): Boolean {
        val visited = BooleanArray(MIN_ROOM_COUNT) { false }
        val visitedCount = depthFirstSearch(roomSequence.first(), visited)

        return visitedCount == roomSequence.size
    }

    private fun depthFirstSearch(cur: Room, visited: BooleanArray): Int {
        var visitedCount = 1
        visited[cur.sector] = true

        for (connection in cur.connections) {
            if (connection != null && !visited[connection.sector]) {
                visitedCount += depthFirstSearch(connection, visited)
            }
        }

        return visitedCount
    }
}