package controller.dto

data class GameInfo(
    val dungeon: DungeonDto,
    val actionResults: List<String>,
    val isGameOver: Boolean = dungeon.player.health <= 0,
)
