package api.entity.environment

import api.entity.behavior.Placeable
import api.util.constant.DungeonConstants.START_DUNGEON_LEVEL
import api.util.constant.MapConstants.MAP_HEIGHT
import api.util.constant.MapConstants.MAP_WIDTH
import api.util.constant.SymbolConstants.OUTER_AREA_CHAR

data class DungeonMap(
    val playground: Array<CharArray> = Array(MAP_HEIGHT) { CharArray(MAP_WIDTH) { OUTER_AREA_CHAR } },
    val enemies: MutableList<Placeable> = mutableListOf(),
    val items: MutableList<Placeable> = mutableListOf(),
    val corridors: MutableList<Position> = mutableListOf(),
    var isExitFound: Boolean = false,
    var dungeonLevel: Int = START_DUNGEON_LEVEL,
) {
    lateinit var player: Placeable
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DungeonMap

        return player == other.player
    }

    override fun hashCode(): Int {
        return player.hashCode()
    }
}