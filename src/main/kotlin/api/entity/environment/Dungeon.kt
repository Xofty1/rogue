package api.entity.environment

import api.util.constant.DungeonConstants.TOTAL_SECTOR_SIZE

data class Dungeon(
    var rooms: Array<Array<Room>> = Array(TOTAL_SECTOR_SIZE) { Array(TOTAL_SECTOR_SIZE) { Room() } },
    var corridors: MutableList<Corridor> = mutableListOf(),
    var roomSequence: MutableList<Room> = mutableListOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Dungeon

        return rooms.contentDeepEquals(other.rooms)
    }

    override fun hashCode(): Int {
        return rooms.contentDeepHashCode()
    }
}
