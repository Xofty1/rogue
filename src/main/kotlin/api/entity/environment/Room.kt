package api.entity.environment

import api.entity.behavior.Placeable
import api.util.constant.UNINITIALIZED

data class Room(
    var sector: Int = UNINITIALIZED,
    var gridI: Int = UNINITIALIZED,
    var gridJ: Int = UNINITIALIZED,
    var topLeft: Position = Position(),
    var botRight: Position = Position(),
    var doors: Array<Position> = Array(4) { Position(UNINITIALIZED, UNINITIALIZED) },
    var connections: Array<Room?> = arrayOfNulls(4),
    var entities: MutableList<Placeable> = mutableListOf(),
) {
    override fun toString(): String {
        return "Room(sector=$sector, gridI=$gridI, gridJ=$gridJ, " +
                "topLeft=$topLeft, bottomRight=$botRight, " +
                "doors=${doors.joinToString()}, " +
                "connections=${connections.map { it?.sector ?: "null" }.joinToString()}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Room

        if (sector != other.sector) return false
        if (gridI != other.gridI) return false
        if (gridJ != other.gridJ) return false
        if (topLeft != other.topLeft) return false
        if (botRight != other.botRight) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sector
        result = 31 * result + gridI
        result = 31 * result + gridJ
        result = 31 * result + topLeft.hashCode()
        result = 31 * result + botRight.hashCode()
        return result
    }
}

