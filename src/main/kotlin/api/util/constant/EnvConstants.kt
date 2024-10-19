package api.util.constant

object DungeonStat {
    const val MAP_HEIGHT = 30
    const val MAP_WIDTH = 90
    const val ROOMS_PER_SIDE = 3
    const val MAX_ROOMS_NUMBER = ROOMS_PER_SIDE * ROOMS_PER_SIDE
    const val MAX_CORRIDORS_NUMBER = 12
}

object SectorStat {
    const val SECTOR_HEIGHT = DungeonStat.MAP_HEIGHT / DungeonStat.ROOMS_PER_SIDE
    const val SECTOR_WIDTH = DungeonStat.MAP_WIDTH / DungeonStat.ROOMS_PER_SIDE
}

object RoomStat {
    const val CORNER_VERT_RANGE = (SectorStat.SECTOR_HEIGHT - 6) / 2
    const val CORNER_HOR_RANGE = (SectorStat.SECTOR_WIDTH - 6) / 2
    const val ROOM_CHANCE = 0.5
    const val SPAWN_SET_CHANCE = 0.5
}

object CorridorType {
    const val LEFT_TO_RIGHT_CORRIDOR = 0
    const val LEFT_TURN_CORRIDOR = 1
    const val RIGHT_TURN_CORRIDOR = 2
    const val TOP_TO_BOTTOM_CORRIDOR = 3
}

object TileType {
    const val WALL_CHAR = '#'
    const val CORRIDOR_CHAR = '+'
    const val OUTER_AREA_CHAR = '.'
    const val INNER_AREA_CHAR = ' '
    const val EMPTY_CHAR = ' '
    const val IS_OUTER = 0
    const val IS_INNER = 1
    const val IS_WALL = 2
}

