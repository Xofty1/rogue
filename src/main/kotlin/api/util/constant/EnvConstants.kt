package api.util.constant

import api.util.constant.MapConstants.MAP_HEIGHT
import api.util.constant.MapConstants.MAP_WIDTH
import api.util.constant.SectorConstants.ROOMS_PER_SIDE
import api.util.constant.SectorConstants.SECTOR_HEIGHT
import api.util.constant.SectorConstants.SECTOR_WIDTH

object DungeonConstants {
    const val MIN_ROOM_COUNT = 9
    const val TOTAL_SECTOR_SIZE = ROOMS_PER_SIDE + 2
    const val MAX_DUNGEON_LEVEL = 21
    const val START_DUNGEON_LEVEL = 1
    const val SAFE_SPACE_AROUND_PLAYER = 2
    const val DUNGEON_LEVEL_DIVISOR = 4
}

object MapConstants {
    const val MAP_HEIGHT = 30
    const val MAP_WIDTH = 54
}

object SectorConstants {
    const val ROOMS_PER_SIDE = 3
    const val SECTOR_HEIGHT = MAP_HEIGHT / ROOMS_PER_SIDE
    const val SECTOR_WIDTH = MAP_WIDTH / ROOMS_PER_SIDE
    const val ROOM_CHANCE = 0.5
}

object RoomConstants {
    const val MAX_ENEMIES_PER_ROOM = 8
    const val MIN_ENEMIES_PER_ROOM = 1
    const val MAX_ITEMS_PER_ROOM = 6
    const val MIN_ITEMS_PER_ROOM = 1
    const val CORNER_VERT_RANGE = (SECTOR_HEIGHT - 6) / 2
    const val CORNER_HOR_RANGE = (SECTOR_WIDTH - 6) / 2
}

object ConnectionConstants {
    const val TOP = 0
    const val RIGHT = 1
    const val BOTTOM = 2
    const val LEFT = 3
}

object SymbolConstants {
    const val WALL_CHAR = '#'
    const val CORRIDOR_CHAR = '+'
    const val OUTER_AREA_CHAR = '.'
    const val INNER_AREA_CHAR = ' '
}

object MapChar {
    const val PLAYER = '@'

    const val ZOMBIE = 'Z'
    const val GHOST = 'G'
    const val OGRE = 'O'
    const val VAMPIRE = 'V'
    const val SNAKE_MAGE = 'M'
    const val MIMIC = 'm'

    const val ELIXIR = 'e'
    const val FOOD = 'f'
    const val TREASURE = '$'
    const val WEAPON = 'w'
    const val SCROLL = 's'

    const val EXIT = 'X'

    const val ELSE = 'E'
}

const val UNINITIALIZED = -1
