package api.entity.environment

import api.util.CorridorType

data class Corridor(
    var type: CorridorType = CorridorType.UNDEFINED,
    var points: MutableList<Position> = mutableListOf(),
    var pointsCount: Int = 0,
)
