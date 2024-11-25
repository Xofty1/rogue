package presentation.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import controller.GameController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.app.game_screen.Game
import presentation.app.start_screen.StartMenu
import presentation.util.MyStyle.largeTextStyle
import presentation.util.Screen
import kotlin.system.exitProcess

/**
 * Собственно сама игра
 */
@Composable
fun App() {

    var currentScreen by remember { mutableStateOf(Screen.START_SCREEN) }
    var isErrorLoading by remember { mutableStateOf(false) }
    val resetError = {
        isErrorLoading = false
    }

    val onStartNewGame = {
        currentScreen = Screen.NEW_GAME
    }

    val onExit = {
        exitProcess(0)
    }

    val onLoadGame = {
        if (GameController.loadGame() != null) {
            currentScreen = Screen.LOAD_GAME
        } else {
            isErrorLoading = true
        }
    }

    when (currentScreen) {
        Screen.START_SCREEN -> {
            StartMenu(
                onStartNewGame = onStartNewGame,
                onLoadGame = onLoadGame,
                onExit = onExit,
                isErrorLoading = isErrorLoading,
                resetError = resetError
            )
        }

        Screen.NEW_GAME -> {
            Game(
                initialGameInfo = GameController.startGame(),
                onExit = onExit
            )
        }

        Screen.LOAD_GAME -> {
            GameController.loadGame()?.let {
                Game(
                    initialGameInfo = it,
                    onExit = onExit
                )
            }
        }
    }
}

/**
 * Тост, использую как заглушку для загрузки если нет сохраненных игр
 */
@Composable
fun Toast(
    message: String,
    duration: Long,
    resetError: () -> Unit
) {
    var visible by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(message) {
        coroutineScope.launch {
            delay(duration)
            visible = false
            resetError()
        }
    }

    if (visible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = message,
                color = Color.White,
                style = largeTextStyle,
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.7f))
                    .padding(8.dp)
            )
        }
    }
}
