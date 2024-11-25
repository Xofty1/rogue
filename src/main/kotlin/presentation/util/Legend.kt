package presentation.util

import androidx.compose.ui.graphics.painter.Painter

data class Legend(
    val entityType: GameEntity,
    val title: String,
    val icon: Painter,
    val description: String
)
