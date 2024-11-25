package presentation.app.game_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import api.util.Direction
import controller.GameController
import controller.dto.GameInfo

/**
 * Экран игровой сессии.
 * Состоит из двух секций: GameField и SidePanel.
 * Использование remember и mutableStateOf для отслеживания состояния и реактивного обновления UI.
 * Обрабатывает нажатия клавиатуры для управления игровым процессом.
 * @param initialGameInfo начальные данные игровой сессии
 */
@Composable
internal fun Game(
    initialGameInfo: GameInfo,
    onExit: () -> Unit
) {
    Box(
        Modifier.background(Color.DarkGray)
    ) {
        var gameInfo by remember { mutableStateOf(initialGameInfo) }

        // Пауза
        var isPauseOpen by remember { mutableStateOf(false) }
        val onPause = {
            isPauseOpen = !isPauseOpen
        }

        // Таблица рекордов
        var isRecordOpen by remember { mutableStateOf(false) }
        val onRecord = {
            isRecordOpen = !isRecordOpen
        }

        // Рюкзак
        var isBagOpen by remember { mutableStateOf(false) }
        val openBackpack = {
            isBagOpen = true
        }
        val closeBackpack = {
            isBagOpen = false
        }

        var isLegendOpen by remember { mutableStateOf(false) }
        val onLegend = {
            isLegendOpen = !isLegendOpen
        }

        // Обработка движения
        val onMove: (Direction) -> Unit = { direction ->
            gameInfo = GameController.makeMove(direction)
        }

        // Использование предмета
        val onUse: (Int, Int) -> Unit = { rowNumber, cellNumber ->
            gameInfo = GameController.useItem(rowNumber, cellNumber)
        }

        // Запускает новую игру
        val onRestart = {
            gameInfo = GameController.startGame()
        }

        // Нельзя закрыть окно после смерти
        val onDismissDeathWindow = {}

        // Отрисовка окна после смерти персонажа
        if (gameInfo.isGameOver) {
            PlayerDeathWindow(
                onDismiss = onDismissDeathWindow,
                onStartNewGame = onRestart,
                onExit = onExit,
                rating = gameInfo.dungeon.player.rating
            )
        }

        // Отрисовка окна паузы
        if (isPauseOpen) {
            PauseWindow(onPause, onExit)
        }
        // Отрисовка окна рекордов
        if (isRecordOpen) {
            RecordTableWindow(onRecord, GameController.getTableRecords().take(10))
        }
        // Отрисовка окна Легенды
        if (isLegendOpen) {
            LegendWindow(onLegend)
        }

        // Магия фокуса
        val focusRequester = remember { FocusRequester() }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .onKeyEvent { event ->
                    handleKeyEventOnGameField(
                        event,
                        onMove,
                        onPause,
                        onRestart,
                        onRecord,
                        openBackpack,
                        onLegend
                    )
                }
                .focusRequester(focusRequester)
        ) {
            Row {
                GameField(
                    dungeonMap = gameInfo.dungeon,
                    modifier = Modifier.width(1728.dp)
                )
                SidePanel(
                    player = gameInfo.dungeon.player,
                    level = gameInfo.dungeon.dungeonLevel,
                    action = gameInfo.actionResults,
                    isBagOpen = isBagOpen,
                    openBackpack = openBackpack,
                    closeBackpack = closeBackpack,
                    onUse = onUse,
                    modifier = Modifier.width(192.dp)
                )
            }
        }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}