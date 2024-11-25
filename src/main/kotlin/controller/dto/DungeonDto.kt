package controller.dto

import api.entity.environment.Position

data class DungeonDto(
    val player: PlayerDto,
    val dungeonLevel: Int,
    val corridorPositions: List<Position>,
    val playground: Array<CharArray>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DungeonDto

        if (player != other.player) return false
        if (dungeonLevel != other.dungeonLevel) return false

        return true
    }

    override fun hashCode(): Int {
        var result = player.hashCode()
        result = 31 * result + dungeonLevel
        return result
    }
}
