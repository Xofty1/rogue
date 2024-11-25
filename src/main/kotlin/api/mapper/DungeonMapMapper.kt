package api.mapper

import api.entity.Player
import api.entity.environment.DungeonMap
import api.mapper.PlayerMapper.toDto
import controller.dto.DungeonDto

object DungeonMapMapper {
    fun DungeonMap.toDto(): DungeonDto {
        val player = this.player
        player as Player

        return DungeonDto(
            player = player.toDto(),
            dungeonLevel = this.dungeonLevel,
            corridorPositions = this.corridors,
            playground = Array(this.playground.size) { i -> this.playground[i].copyOf() }
        )
    }
}