package presentation.app.game_screen

import androidx.compose.ui.input.key.*
import api.util.Direction
import presentation.util.FogOfWarMapper

/**
 * Функция для обработки событий клавиатуры для игрового поля.
 * W, A, S, D - перемещение персонажа.
 * Escape - пауза, R - рестарт, TAB - таблица рекордов.
 * @param event событие клавиатуры
 * @param onMove callback для АПИ - передает направление и обновляет состояние экрана
 * @param onPause действие для кнопки паузы
 * @param onRestart действие для рестарта
 * @param openBackpack открывает инвентарь
 */
internal fun handleKeyEventOnGameField(
    event: KeyEvent,
    onMove: (Direction) -> Unit,
    onPause: () -> Unit,
    onRestart: () -> Unit,
    onTableRecord: () -> Unit,
    openBackpack: () -> Unit,
    onLegend: () -> Unit
): Boolean {
    return if (event.type == KeyEventType.KeyDown) {
        when (event.key) {
            Key.W -> {
                onMove(Direction.UP)
                true
            }

            Key.A -> {
                onMove(Direction.LEFT)
                true
            }

            Key.S -> {
                onMove(Direction.DOWN)
                true
            }

            Key.D -> {
                onMove(Direction.RIGHT)
                true
            }

            Key.Escape -> {
                onPause()
                true
            }

            Key.R -> {
                FogOfWarMapper.clearMapper()
                onRestart()
                true
            }

            Key.T -> {
                onTableRecord()
                true
            }

            Key.B -> {
                openBackpack()
                true
            }

            Key.Tab -> {
                onLegend()
                true
            }

            else -> false
        }
    } else {
        false
    }
}